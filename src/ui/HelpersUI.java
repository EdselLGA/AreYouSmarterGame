package ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class HelpersUI {

    /** Creates a throbbing fade in/out effect for text labels (smooth glowing). */
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
            label.setForeground(new Color(base.getRed()/255f, base.getGreen()/255f, base.getBlue()/255f, alpha[0]));
        });

        timer.start();
        return timer;
    }

    /** Creates a throbbing scale (zoom in/out) effect for image JLabels, centered scaling. */
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

            int newW = (int) (baseImage.getWidth(null) * scale[0]);
            int newH = (int) (baseImage.getHeight(null) * scale[0]);

            // Center scaling by applying an offset
            Image scaled = new BufferedImage(newW, newH, BufferedImage.TRANSLUCENT);
            Graphics2D g2 = ((BufferedImage) scaled).createGraphics();
            g2.drawImage(baseImage, 0, 0, newW, newH, null);
            g2.dispose();

            label.setIcon(new ImageIcon(scaled));
            label.revalidate();
            label.repaint();
        });

        timer.start();
        return timer;
    }

    /** Applies alpha transparency to an image. */
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

    /** Smooth fade-in or fade-out for a window. */
    public static void fadeWindow(Window window, boolean fadeIn, int delayMs, float step, Runnable onDone) {
        Timer timer = new Timer(delayMs, null);
        final float[] opacity = {fadeIn ? 0f : 1f};

        timer.addActionListener(e -> {
            try {
                window.setOpacity(opacity[0]);
            } catch (Exception ex) {
                // Older Java versions may not support opacity
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

    
}
