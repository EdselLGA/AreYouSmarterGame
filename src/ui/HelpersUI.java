package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 * Helpers: fade-in overlay + simple throb animation.
 */
public class HelpersUI {

    public static void fadeInComponent(JComponent comp, int delayMs, float step, Runnable onDone) {

        if (comp == null || comp.getWidth() <= 0 || comp.getHeight() <= 0) {
            if (onDone != null) onDone.run();
            return;
        }

        comp.revalidate();
        comp.repaint();

        // Snapshot
        BufferedImage snapshot = new BufferedImage(comp.getWidth(), comp.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = snapshot.createGraphics();
        comp.paint(g2);
        g2.dispose();

        Window owner = SwingUtilities.getWindowAncestor(comp);
        if (owner == null) {
            if (onDone != null) onDone.run();
            return;
        }

        Rectangle bounds =
                SwingUtilities.convertRectangle(comp.getParent(), comp.getBounds(), owner);

        // Overlay
        JWindow overlay = new JWindow(owner);
        overlay.setBackground(new Color(0, 0, 0, 0));
        overlay.setBounds(bounds);

        final float[] alpha = {0f};

        JComponent painter = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(AlphaComposite.SrcOver.derive(alpha[0]));
                g2d.drawImage(snapshot, 0, 0, getWidth(), getHeight(), null);
                g2d.dispose();
            }
        };

        overlay.add(painter);
        overlay.setVisible(true);

        Timer timer = new Timer(delayMs, null);
        timer.addActionListener((ActionEvent e) -> {
            alpha[0] += step;
            if (alpha[0] > 1f) alpha[0] = 1f;

            painter.repaint();

            if (alpha[0] >= 1f) {
                timer.stop();
                overlay.setVisible(false);
                overlay.dispose();
                if (onDone != null) onDone.run();
            }
        });

        timer.start();
    }

    public static Timer createSimpleTextThrob(JLabel label, int delayMs, float minScale, float maxScale) {
        if (label == null) return null;

        final float[] scale = {1f};
        final boolean[] shrinking = {true};

        Icon baseIcon = label.getIcon();

        Timer timer = new Timer(delayMs, ev -> {
            if (shrinking[0]) {
                scale[0] -= 0.01f;
                if (scale[0] <= minScale) shrinking[0] = false;
            } else {
                scale[0] += 0.01f;
                if (scale[0] >= maxScale) shrinking[0] = true;
            }

            if (baseIcon instanceof ImageIcon) {
                Image baseImg = ((ImageIcon) baseIcon).getImage();
                int w = Math.max(1, (int) (baseImg.getWidth(null) * scale[0]));
                int h = Math.max(1, (int) (baseImg.getHeight(null) * scale[0]));
                Image scaled = baseImg.getScaledInstance(w, h, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(scaled));
            } else {
                Font f = label.getFont();
                label.setFont(f.deriveFont(f.getSize2D() * scale[0]));
            }

            label.revalidate();
            label.repaint();
        });

        timer.start();
        return timer;
    }
}

