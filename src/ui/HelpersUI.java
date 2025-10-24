package ui;

// Avatars & helpers class



import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class HelpersUI {

    /** Creates a throbbing fade in/out effect for image JLabels */
    public static Timer createFadeThrob(JLabel label, int delayMs, float minAlpha, float maxAlpha) {
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

            ImageIcon icon = (ImageIcon) label.getIcon();
            if (icon != null) {
                Image faded = applyAlpha(icon.getImage(), alpha[0]);
                label.setIcon(new ImageIcon(faded));
            }
        });

        timer.start();
        return timer;
    }

    /** Creates a throbbing scale (zoom in/out) effect for image JLabels */
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
            Image scaled = baseImage.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaled));
        });

        timer.start();
        return timer;
    }

    /** Applies alpha transparency to an image. */
    private static Image applyAlpha(Image image, float alpha) {
        int w = image.getWidth(null);
        int h = image.getHeight(null);
        BufferedImage transparent = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
        Graphics2D g2 = transparent.createGraphics();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return transparent;
    }
}


