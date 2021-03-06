package org.virtuoso.slam;

import org.virtuoso.slam.localizers.Localizer;
import org.virtuoso.slam.localizers.PointMatchingLocalizer;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class LocationRenderer
        extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JPanel canvas;
    private final PointMap map;
    private final Localizer localizer;
    private final PointLocation location;

    private final AtomicLong simNum = new AtomicLong(0);
    private final AtomicLong version = new AtomicLong(0);
    private final AtomicReference<List<LocationScenario>> ref = new AtomicReference<>();

    private final JTextArea editTextArea = new JTextArea("0,0,0");
    ;

    public LocationRenderer(PointMap map, PointLocation location, Localizer localizer, String name)
    {
        this.map = map;
        this.localizer = localizer;
        this.location = location;

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
                    int newImageWidth = 800;
                    int newImageHeight = 800;

                    g.drawString("Version: " + version.get() +
                                    " Num: " + simNum.get() +
                                    " Num Points: " + sim.getScenario().getPoints().size() +
                                    " Matches: " + sim.getScore() +
                                    " x: " + sim.getOrigin().getX() +
                                    " y: " + sim.getOrigin().getY() +
                                    " angle: " + sim.getOrigin().getAngle() +
                                    " size: " + ref.get().size()
                            , 70, 70);

                    g.drawImage(img, 100, 100, newImageWidth, newImageHeight, this);
                }
                else {
                    g.drawString("No Image Available.", 30, 30);
                }
            }
        };

        String title = name;
        Border border = BorderFactory.createTitledBorder(title);
        canvas.setBorder(border);

        JButton updateMap = new JButton("Update Map");
        updateMap.addActionListener((ActionEvent e) -> {
            LocationScenario sim = null;
            if (ref.get() != null && simNum.get() < ref.get().size()) {
                sim = ref.get().get((int) simNum.get());
            }

            if (sim != null) {
                map.apply(sim.getScenario());
            }
        });

        JButton updateLocation = new JButton("Update Location");
        updateMap.addActionListener((ActionEvent e) -> {
            LocationScenario sim = null;
            if (ref.get() != null && simNum.get() < ref.get().size()) {
                sim = ref.get().get((int) simNum.get());
            }

            if (sim != null) {
                location.update(sim.getOrigin());
            }
        });

        JButton enableToggle = new JButton("Toggle Localization");
        enableToggle.addActionListener((ActionEvent e) -> {
            if (localizer.isEnabled()) {
                localizer.setEnabled(false);
            }
            else {
                localizer.setEnabled(true);
            }
        });

        JButton nextSimButton = new JButton("Next Location Scenario");
        nextSimButton.addActionListener((ActionEvent e) -> {
            List<LocationScenario> sims = ref.get();
            simNum.incrementAndGet();
            if (sims == null || simNum.get() >= sims.size()) {
                ref.set(localizer.getPossibleLocations());
                version.set(localizer.getVersion());
                simNum.set(0);
            }
            f.repaint();
        });

        JButton prevSimButton = new JButton("Prev Location Scenario");
        prevSimButton.addActionListener((ActionEvent e) -> {
            List<LocationScenario> sims = ref.get();
            simNum.decrementAndGet();
            if (sims == null || simNum.get() < 0) {
                ref.set(localizer.getPossibleLocations());
                version.set(localizer.getVersion());
                simNum.set(0);
            }
            f.repaint();
        });

        canvas.add(editTextArea);
        canvas.add(nextSimButton);
        canvas.add(enableToggle);
        canvas.add(updateMap);
        canvas.add(updateLocation);
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
                img.setRGB(Math.round(point.getX() / 10) + 1000,
                        Math.round(point.getY() / 10) + 1000, 255);
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
                img.setRGB(Math.round(point.getX() / 10) + 1000,
                        Math.round(point.getY() / 10) + 1000, color);
            }
            catch (RuntimeException ex) {
                System.out.println(point.getX() + " " + point.getY());
                ex.printStackTrace();
            }
        });

        String[] vals = editTextArea.getText().split(",");
        LocationScenario scenario = new LocationScenario(sim.getScenario(),
                sim.getOrigin().getX() + Integer.valueOf(vals[0]),
                sim.getOrigin().getY() + Integer.valueOf(vals[1]),
                sim.getOrigin().getAngle() + Float.valueOf(vals[2]));
        if (Integer.valueOf(vals[0]) != 0 ||
                Integer.valueOf(vals[1]) != 0 ||
                Float.valueOf(vals[2]) != 0
        ) {
            scenario.getScenario().getPoints().stream().forEach(point -> {
                try {
                    int color = 65280;
                    if (mapPoints.hasPoint(point)) {
                        color = 16777215;
                    }
                    img.setRGB(Math.round(point.getX() / 10) + 1000, Math.round(point.getY() / 10) + 1000, color);
                }
                catch (RuntimeException ex) {
                    System.out.println(point.getX() + " " + point.getY());
                    ex.printStackTrace();
                }
            });
        }

        List<Slam.Point> history = location.getHistory();
        int b = 0;
        int r = 0;
        for (int i = history.size() - 1; i >= 0; i--) {
            Slam.Point next = history.get(i);
            for (int x = -2; x < 3; x++) {
                for (int y = -2; y < 3; y++) {
                    img.setRGB(1000 + next.getX()/10 + x,
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

        return img;
    }
}