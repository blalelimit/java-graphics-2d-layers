package layers;

import javax.swing.*;
import javax.swing.plaf.LayerUI;

import random.InitRandom;

import java.awt.*;
import java.awt.geom.Point2D;

public class HybridLayer extends LayerUI<JComponent> {
    private int count = 0;
    private float radius = 200f;
    private double x;
    private double y;
    private final InitRandom xLoc = new InitRandom(0, 1000);
    private final InitRandom yLoc = new InitRandom(0, 1000);
    private final InitRandom rad = new InitRandom(150, 200);
    private final InitRandom vel = new InitRandom(-1, 1);

    private void randomCenter(int count) {
        if (count % 100 == 0) {
            x = xLoc.randomPosition();
            y = yLoc.randomPosition();
        }
        if (count % 500 == 0) {
            radius = (float)rad.randomPosition();
        }
        x += vel.randomVelocity();
        y += vel.randomVelocity();
    }

    @Override
    public void paint (Graphics g, JComponent c) {
        int w = c.getWidth();
        int h = c.getHeight();

        Graphics2D g2d = (Graphics2D)g.create();
        super.paint(g2d, c);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2d.setPaint(new GradientPaint(0, 0, new Color(100, 0, 0), 0, h, new Color(100, 255, 255)));
        g2d.fillRect(0, 0, w, h);

        float[] dist = {0.0f, 1.0f};
        Color[] colors = {new Color(0, 0, 0, 255), new Color(0, 0, 0, 0)};
        RadialGradientPaint p = new RadialGradientPaint(new Point2D.Double(x, y), radius, dist, colors);
        g2d.setPaint(p);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        g2d.fillRect(0, 0, w, h);

        g2d.dispose();
        randomCenter(count);
        count++;
    }
}