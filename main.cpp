

#include <iostream>
#include <fstream>
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <unistd.h>
#include <iostream>
#include <signal.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <sys/socket.h>
#include <cuda_runtime_api.h>
#include <cuda.h>
#include<cuda_profiler_api.h>
#include "LaserScan.h"
#include "TelemetryPoint.h"
#include "protobuf/lidar.pb.h"
#include <google/protobuf/message.h>
#include <google/protobuf/descriptor.h>
#include <google/protobuf/io/zero_copy_stream_impl.h>
#include <google/protobuf/io/coded_stream.h>
#include <google/protobuf/io/zero_copy_stream_impl_lite.h>

#include "CudaUtils.h"
#include <thread>         // std::this_thread::sleep_for
#include <chrono>         // std::chrono::seconds


#define DEBUG

using namespace std;
using namespace std::chrono;
using namespace google::protobuf::io;



bool ctrl_c_pressed;
void ctrlc(int)
{
    ctrl_c_pressed = true;
}

int main(int argc, const char *argv[])
{
    signal(SIGPIPE, SIG_IGN);
    //remove once dev is completed
    cudaDeviceReset();
    int deviceCount;
    cudaGetDeviceCount(&deviceCount);
    int device;
    for (device = 0; device < deviceCount; ++device)
    {
        cudaDeviceProp deviceProp;
        cudaGetDeviceProperties(&deviceProp, device);
        printf("Device %d has compute capability %d.%d.\n", device, deviceProp.major, deviceProp.minor);
        printf("  Device name: %s\n", deviceProp.name);
        printf("  Memory Clock Rate (KHz): %d\n", deviceProp.memoryClockRate);
        printf("  Memory Bus Width (bits): %d\n", deviceProp.memoryBusWidth);
        printf("  Peak Memory Bandwidth (GB/s): %f\n\n", 2.0 * deviceProp.memoryClockRate * (deviceProp.memoryBusWidth / 8) / 1.0e6);
        printf("  Max Shared Mem Bytes Per-Block: %lu\n\n", deviceProp.sharedMemPerBlock);
        printf("  Max Threads Per-Block: %d\n\n", deviceProp.maxThreadsPerBlock);
        printf("  Warp Size: %d\n\n", deviceProp.warpSize);
    }

    const char *default_com_path = "/dev/ttyUSB0";
    char *com_path = (char *)default_com_path;
    if(argc > 1)
    {
        com_path = (char *)argv[1];
    }
    printf("LiDAR COM Port %s \n", com_path);

    _u32 com_baudrate = 256000;
    if(argc > 1)
    {
        com_baudrate = strtoul(argv[2], NULL, 10);
    }
    printf("LiDAR BAUD Rate %u \n", com_baudrate);

    LaserScan laser(com_path, com_baudrate);
    laser.start();

    signal(SIGINT, ctrlc);
    high_resolution_clock::time_point t1, t2;

    //TODO: rplidar examples have this set to ~9000 but I've never seen more than ~2000 samples in a single scan, maybe we can reduce this
    const int scan_buffer_size = 4000;
    TelemetryPoint *h_scan_p = NULL;

    //We used Pinned Host Memory because it tends to be 2x faster than pageable host memory when transfering to/from
    //source: https://devblogs.nvidia.com/how-optimize-data-transfers-cuda-cc/
    checkCuda(cudaMallocHost((void **)&h_scan_p, scan_buffer_size * sizeof(TelemetryPoint)));

    int host_port = 8080;

    struct sockaddr_in my_addr;

    int hsock;
    int *p_int ;
    int err;

    socklen_t addr_size = 0;
    int *csock;
    sockaddr_in sadr;
    pthread_t thread_id = 0;

    hsock = socket(AF_INET, SOCK_STREAM, 0);
    if(hsock == -1)
    {
        printf("Error initializing socket %d\n", errno);
        goto FINISH;
    }

    p_int = (int *)malloc(sizeof(int));
    *p_int = 1;

    if( (setsockopt(hsock, SOL_SOCKET, SO_REUSEADDR, (char *)p_int, sizeof(int)) == -1 ) ||
            (setsockopt(hsock, SOL_SOCKET, SO_KEEPALIVE, (char *)p_int, sizeof(int)) == -1 ) )
    {
        printf("Error setting options %d\n", errno);
        free(p_int);
        goto FINISH;
    }
    free(p_int);

    my_addr.sin_family = AF_INET ;
    my_addr.sin_port = htons(host_port);

    memset(&(my_addr.sin_zero), 0, 8);
    my_addr.sin_addr.s_addr = INADDR_ANY ;

    if( bind( hsock, (sockaddr *)&my_addr, sizeof(my_addr)) == -1 )
    {
        fprintf(stderr, "Error binding to socket, make sure nothing else is listening on this port %d\n", errno);
        goto FINISH;
    }
    if(listen( hsock, 10) == -1 )
    {
        fprintf(stderr, "Error listening %d\n", errno);
        goto FINISH;
    }

    //Now lets do the server stuff

    addr_size = sizeof(sockaddr_in);

    while(true)
    {
        printf("waiting for a connection\n");
        csock = (int *)malloc(sizeof(int));
        if((*csock = accept( hsock, (sockaddr *)&sadr, &addr_size)) != -1)
        {
            printf("---------------------\nReceived connection from %s\n", inet_ntoa(sadr.sin_addr));
            int count = 0;
            while(count++ < 20)
            {
                t1 = high_resolution_clock::now();
                int num_scan_samples = laser.scan(h_scan_p, scan_buffer_size);
                t2 = high_resolution_clock::now();
                auto scan_dur = duration_cast<milliseconds>( t2 - t1 ).count();

                t1 = high_resolution_clock::now();


                for(int i = 0; i < num_scan_samples; i++)
                {

                    Point point;
                    TelemetryPoint t_point = h_scan_p[i];

                    point.set_x(t_point.x);
                    point.set_y(t_point.y);
                    point.set_distance(t_point.distance);
                    point.set_angle(t_point.angle);

                    int siz = point.ByteSize() + 2;
                    char *pkt = new char [siz];
                    google::protobuf::io::ArrayOutputStream aos(pkt, siz);
                    CodedOutputStream *coded_output = new CodedOutputStream(&aos);
                    coded_output->WriteVarint32(point.ByteSize());
                    point.SerializeToCodedStream(coded_output);

                    int bytecount;
                    if( (bytecount = send(*csock, (void *) pkt, siz, 0)) == -1 )
                    {
                        fprintf(stderr, "Error sending data %d\n", errno);
                        break;
                    }
                }

                t2 = high_resolution_clock::now();
                auto cp_dur = duration_cast<milliseconds>( t2 - t1 ).count();

                cout << "Scan Dur: " << scan_dur << " ms Num Samples: " << num_scan_samples << " Processing Dur: " << cp_dur << "ms " << endl;


                if (ctrl_c_pressed)
                {
                    break;
                }
            }

            close(*csock);

        }
        else
        {
            fprintf(stderr, "Error accepting %d\n", errno);
        }
    }

FINISH:
    free(csock);
    laser.stop();
    google::protobuf::ShutdownProtobufLibrary();
    return 0;
}



