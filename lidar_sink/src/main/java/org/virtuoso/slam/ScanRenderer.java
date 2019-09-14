package org.virtuoso.slam;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class ScanRenderer
        extends JPanel
        implements ScanAssembler.ScanListener
{
    private static final long serialVersionUID = 1L;
    private JPanel canvas;
    private final AtomicReference<Scan> lastScan = new AtomicReference(new Scan());
    private final AtomicLong matches = new AtomicLong(0);
    private final PointMap map;
    private final PointLocation location;
    private final AtomicReference<BufferedImage> bufferedImage = new AtomicReference<>();
    private final JTextArea editTextArea = new JTextArea("0,0,0");
    ;

    public ScanRenderer(PointMap map, PointLocation location)
    {
        this.map = map;
        this.location = location;
        this.canvas = new JPanel()
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);

                BufferedImage img = bufferedImage.get();

                if (img != null) {
                    int newImageWidth = 800;
                    int newImageHeight = 800;

                    g.drawString("Num Points: " + lastScan.get().getPoints().size() +
                            " Matches: " + matches.get(), 30, 30);

                    g.drawImage(img, 50, 50, newImageWidth, newImageHeight, this);
                }
                else {
                    g.drawString("No Image Available.", 30, 30);
                }
            }
        };

        String title = "SLAM Status";
        Border border = BorderFactory.createTitledBorder(title);
        canvas.setBorder(border);

        JButton updateMapButton = new JButton("Update PointMap");
        updateMapButton.addActionListener((ActionEvent e) -> {
            map.apply(lastScan.get());
        });

        canvas.add(editTextArea);
        canvas.add(updateMapButton);
        canvas.setPreferredSize(new Dimension(1000, 1000));
        JScrollPane sp = new JScrollPane(canvas);
        setLayout(new BorderLayout());
        add(sp, BorderLayout.CENTER);

        JFrame f = new JFrame();
        f.setContentPane(this);
        f.setSize(1000, 700);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(() -> {
            while (true) {
                f.repaint();
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public synchronized void onScan(Scan scan)
    {
        final AtomicLong scanMatches = new AtomicLong(0);
        bufferedImage.set(new BufferedImage(2000, 2000, BufferedImage.TYPE_INT_RGB));

        Scan mapPoints = map.getMap();
        mapPoints.getPoints().stream().forEach(point -> {
            try {
                bufferedImage.get().setRGB(Math.round(point.getX() / 10) + 1000,
                        Math.round(point.getY() / 10) + 1000, 255);
            }
            catch (RuntimeException ex) {
                System.out.println(point.getX() + " " + point.getY());
                ex.printStackTrace();
            }
        });

        scan.getPoints().stream().forEach(point -> {
            try {
                Slam.Point locPoint = location.get();

                double effectiveAngle = ((locPoint.getAngle() * 3.14159265359) / 180) + point.getAngle();
                if (effectiveAngle > 6.28319) {
                    effectiveAngle = effectiveAngle - 6.28319;
                }
                else if (effectiveAngle < 0) {
                    effectiveAngle = effectiveAngle * -1;
                }
                int x1 = locPoint.getX() + (int) Math.round(sin(effectiveAngle) * point.getDistance());
                int y1 = locPoint.getY() + (int) Math.round(cos(effectiveAngle) * point.getDistance());

                Slam.Point effectivePoint = Slam.Point.newBuilder()
                        .setAngle((float) effectiveAngle)
                        .setDistance(point.getDistance())
                        .setX(x1)
                        .setY(y1)
                        .setEnd(false)
                        .build();

                int color = 16711680;
                if (mapPoints.hasPoint(effectivePoint)) {
                    color = 16744448;
                    scanMatches.incrementAndGet();
                }
                bufferedImage.get().setRGB(Math.round(effectivePoint.getX() / 10) + 1000,
                        Math.round(effectivePoint.getY()/10) + 1000, color);
            }
            catch (RuntimeException ex) {
                System.out.println(point.getX() + " " + point.getY());
                ex.printStackTrace();
            }
        });

        String[] vals = editTextArea.getText().split(",");
        LocationScenario scenario = new LocationScenario(scan, Integer.valueOf(vals[0]), Integer.valueOf(vals[1]),
                Float.valueOf(vals[2]));
        if (scenario.getOrigin().getX() != 0 ||
                scenario.getOrigin().getY() != 0 ||
                scenario.getOrigin().getAngle() != 0
        ) {
            scenario.getScenario().getPoints().stream().forEach(point -> {
                try {
                    int color = 65280;
                    if (mapPoints.hasPoint(point)) {
                        color = 16777215;
                    }
                    bufferedImage.get().setRGB(Math.round(point.getX() / 10) + 1000, Math.round(point.getY() / 10) + 1000, color);
                }
                catch (RuntimeException ex) {
                    System.out.println(point.getX() + " " + point.getY());
                    ex.printStackTrace();
                }
            });
        }

        matches.set(scanMatches.get());
        lastScan.set(scan);

        List<Slam.Point> history = location.getHistory();
        int b = 0;
        int r = 0;
        for (int i = history.size() - 1; i >= 0; i--) {
            Slam.Point next = history.get(i);
            for (int x = -2; x < 3; x++) {
                for (int y = -2; y < 3; y++) {
                    bufferedImage.get().setRGB(1000 + next.getX()/10 + x,
                            1000 + next.getY()/10 + y,
                            new Color(r, 255, b).getRGB());
                }
            }
            b += 20;
            r += 20;

            if (b > 150) {
                b = 150;
                r = 150;
            }
        }
    }
}