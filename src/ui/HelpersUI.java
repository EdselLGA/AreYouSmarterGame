package ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class HelpersUI {

    public static Timer createTextFadeThrob(JLabel label, int delayMs, float minAlpha, float maxAlpha) {
        final float[] alpha = {maxAlpha};
        final boolean[] fadingOut = {true};

        Timer timer = new Timer(delayMs, e -> {
            if (fadingOut[0]) {
                alpha[0] -= 0.05f;
                if (alpha[0] <= minAlpha) fadingOut[0] = false;
            } else {
                alpha[0] += 0.05f;
                if (alpha[0] >= maxAlpha) fadingOut[0] = true;
            }

            Color base = label.getForeground();
            float r = base.getRed() / 255f;
            float g = base.getGreen() / 255f;
            float b = base.getBlue() / 255f;

            alpha[0] = Math.max(0, Math.min(1, alpha[0]));
            label.setForeground(new Color(r, g, b, alpha[0]));
        });

        timer.start();
        return timer;
    }

    public static Timer createScaleThrob(JLabel label, int delayMs, float minScale, float maxScale) {
        final float[] scale = {1.0f};
        final boolean[] shrinking = {true};
        final Image baseImage = ((ImageIcon) label.getIcon()).getImage();

        Timer timer = new Timer(delayMs, e -> {
            if (shrinking[0]) {
                scale[0] -= 0.02f;
                if (scale[0] <= minScale) shrinking[0] = false;
            } else {
                scale[0] += 0.02f;
                if (scale[0] >= maxScale) shrinking[0] = true;
            }

            int newW = Math.max(1, (int) (baseImage.getWidth(null) * scale[0]));
            int newH = Math.max(1, (int) (baseImage.getHeight(null) * scale[0]));

            BufferedImage scaled = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = scaled.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(baseImage, 0, 0, newW, newH, null);
            g2.dispose();

            label.setIcon(new ImageIcon(scaled));
            label.revalidate();
            label.repaint();
        });

        timer.start();
        return timer;
    }

    public static Image applyAlpha(Image image, float alpha) {
        int w = image.getWidth(null);
        int h = image.getHeight(null);
        BufferedImage transparent = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
        Graphics2D g2 = transparent.createGraphics();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return transparent;
    }

    public static void fadeWindow(Window window, boolean fadeIn, int delayMs, float step, Runnable onDone) {

        Timer timer = new Timer(delayMs, null);
        final float[] opacity = {fadeIn ? 0f : 1f};

        timer.addActionListener(e -> {

            try {
                window.setOpacity(opacity[0]);
            } catch (Exception ex) {
                timer.stop();
                if (onDone != null) onDone.run();
                return;
            }

            if (fadeIn) {
                opacity[0] += step;
                if (opacity[0] >= 1f) {
                    window.setOpacity(1f);
                    timer.stop();
                    if (onDone != null) onDone.run();
                }
            } else {
                opacity[0] -= step;
                if (opacity[0] <= 0f) {
                    window.setOpacity(0f);
                    timer.stop();
                    if (onDone != null) onDone.run();
                }
            }
        });

        timer.start();
    }

    public static void fadePanel(JComponent panel, boolean fadeIn, int delayMs, float step, Runnable onDone) {

        if (panel == null || panel.getWidth() <= 0 || panel.getHeight() <= 0) {
            if (onDone != null) onDone.run();
            return;
        }

        panel.revalidate();
        panel.repaint();

        try {
            SwingUtilities.invokeAndWait(() -> {});
        } catch (Exception ignored) {}

        BufferedImage snapshot = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = snapshot.createGraphics();
        panel.paint(g2);
        g2.dispose();

        Window owner = SwingUtilities.getWindowAncestor(panel);
        if (owner == null) {
            if (onDone != null) onDone.run();
            return;
        }

        Rectangle bounds = SwingUtilities.convertRectangle(panel.getParent(), panel.getBounds(), owner);

        JWindow overlay = new JWindow(owner);
        overlay.setBackground(new Color(0, 0, 0, 0));
        overlay.setBounds(bounds);
        overlay.setAlwaysOnTop(true);

        final float[] alpha = {1f};

        overlay.add(new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha[0]));
                g2d.drawImage(snapshot, 0, 0, getWidth(), getHeight(), null);
                g2d.dispose();
            }
        });

        overlay.setVisible(true);

        Timer timer = new Timer(delayMs, null);
        timer.addActionListener(e -> {

            alpha[0] -= step;
            if (alpha[0] < 0f) alpha[0] = 0f;

            overlay.repaint();

            if (alpha[0] <= 0f) {
                timer.stop();
                overlay.setVisible(false);
                overlay.dispose();

                if (!fadeIn) panel.setVisible(false);

                if (onDone != null) onDone.run();
            }
        });

        timer.start();
    }
}

