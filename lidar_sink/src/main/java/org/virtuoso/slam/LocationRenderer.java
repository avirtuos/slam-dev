package org.virtuoso.slam;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

public class LocationRenderer
        extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JPanel canvas;
    private final PointMap map;
    private final Localizer localizer;

    private final AtomicLong simNum = new AtomicLong(0);
    private final AtomicReference<List<LocationScenario>> ref = new AtomicReference<>();

    public LocationRenderer(PointMap map, Localizer localizer)
    {
        this.map = map;
        this.localizer = localizer;

        JFrame f = new JFrame();

        this.canvas = new JPanel()
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);

                LocationScenario sim = null;
                if (ref.get() != null && simNum.get() < ref.get().size()) {
                    sim = ref.get().get((int) simNum.get());
                }

                if (sim != null) {
                    BufferedImage img = onScan(sim);
                    int newImageWidth = 1000;
                    int newImageHeight = 1000;

                    g.drawString("Num: " + simNum.get() + " Num Points: " + sim.getScenario().getPoints().size() +
                                    " Matches: " + sim.getScore() +
                                    " x: " + sim.getOrigin().getX() +
                                    " y: " + sim.getOrigin().getY() +
                                    " angle: " + sim.getOrigin().getAngle() +
                                    " size: " + ref.get().size()
                            , 30, 30);

                    g.drawImage(img, 50, 50, newImageWidth, newImageHeight, this);
                }
                else {
                    g.drawString("No Image Available.", 30, 30);
                }
            }
        };

        String title = "Location Status";
        Border border = BorderFactory.createTitledBorder(title);
        canvas.setBorder(border);

        JButton nextSimButton = new JButton("Next Location Scenario");
        nextSimButton.addActionListener((ActionEvent e) -> {
            List<LocationScenario> sims = ref.get();
            if (sims == null || simNum.get() >= sims.size()) {
                ref.set(localizer.getPossibleLocations());
                simNum.set(0);
            }
            simNum.incrementAndGet();
            f.repaint();
        });

        JButton prevSimButton = new JButton("PRev Location Scenario");
        prevSimButton.addActionListener((ActionEvent e) -> {
            List<LocationScenario> sims = ref.get();
            if (sims == null || simNum.get() >= sims.size()) {
                ref.set(localizer.getPossibleLocations());
                simNum.set(0);
            }
            simNum.decrementAndGet();
            f.repaint();
        });

        canvas.add(nextSimButton);
        canvas.add(prevSimButton);
        canvas.setPreferredSize(new Dimension(1000, 1000));
        JScrollPane sp = new JScrollPane(canvas);
        setLayout(new BorderLayout());
        add(sp, BorderLayout.CENTER);

        f.setContentPane(this);
        f.setSize(1000, 700);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    private BufferedImage onScan(LocationScenario sim)
    {
        BufferedImage img = new BufferedImage(2000, 2000, BufferedImage.TYPE_INT_RGB);

        Scan mapPoints = map.getMap();
        mapPoints.getPoints().stream().forEach(point -> {
            try {
                img.setRGB(point.getX() + 1000, point.getY() + 1000, 255);
            }
            catch (RuntimeException ex) {
                System.out.println(point.getX() + " " + point.getY());
                ex.printStackTrace();
            }
        });

        sim.getScenario().getPoints().stream().forEach(point -> {
            try {
                int color = 16711680;
                if (mapPoints.hasPoint(point)) {
                    color = 16744448;
                }
                img.setRGB(point.getX() + 1000, point.getY() + 1000, color);
            }
            catch (RuntimeException ex) {
                System.out.println(point.getX() + " " + point.getY());
                ex.printStackTrace();
            }
        });

        for (int x = -2; x < 3; x++) {
            for (int y = -2; y < 3; y++) {
                img.setRGB(1000 + sim.getOrigin().getX() + x, 1000 + sim.getOrigin().getY() + y, 65280);
            }
        }

        return img;
    }
}