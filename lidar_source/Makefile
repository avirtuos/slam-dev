
all: main.cpp
	/usr/local/cuda-10.0/bin/nvcc -g -arch=sm_60 -o lidarBroadcast -I . -I ./devel/ -I/usr/include/google/protobuf/ -I /usr/include/google/protobuf/util/ main.cpp ./protobuf/lidar.pb.cc ./lib/librplidar_sdk.a -rdc=true -lrt -lstdc++ -lprotobuf -lpthread -lbluetooth

protobuf: protobuf/lidar.proto
	protoc --cpp_out .  protobuf/lidar.proto