package com.example.view;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.Timer;


public class HelpersUI {


    //FADE IN COMPONENT

    public static void fadeInComponent(JComponent comp, int delayMs, float step, Runnable onDone) {

        if (comp == null || comp.getWidth() <= 0 || comp.getHeight() <= 0) {
            if (onDone != null) onDone.run();
            return;
        }

        comp.revalidate();
        comp.repaint();

        BufferedImage snapshot = new BufferedImage(
                comp.getWidth(), comp.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );

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

    //THROB EFFECT

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
 
    //FADE IN / OUT ICON LABEL
   
    public static Timer createFadeInOut(JLabel label, int delayMs, float step) {
        if (label == null) return null;

        final float[] alpha = {0f};
        final boolean[] fadingIn = {true};

        JLabel fadingLabel = new JLabel(label.getIcon()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha[0]));
                super.paintComponent(g2);
                g2.dispose();
            }
        };

        fadingLabel.setBounds(label.getBounds());
        fadingLabel.setAlignmentX(label.getAlignmentX());
        fadingLabel.setOpaque(false);

        Container parent = label.getParent();
        if (parent != null) {

            int index = -1;
            Component[] comps = parent.getComponents();
            for (int i = 0; i < comps.length; i++) {
                if (comps[i] == label) {
                    index = i;
                    break;
                }
            }

            if (index != -1) {
                parent.remove(label);
                parent.add(fadingLabel, index);
                parent.revalidate();
                parent.repaint();
            }
        }

        Timer timer = new Timer(delayMs, e -> {

            if (fadingIn[0]) {
                alpha[0] += step;
                if (alpha[0] >= 1f) {
                    alpha[0] = 1f;
                    fadingIn[0] = false;
                }
            } else {
                alpha[0] -= step;
                if (alpha[0] <= 0f) {
                    alpha[0] = 0f;
                    fadingIn[0] = true;
                }
            }

            fadingLabel.repaint();
        });

        timer.start();
        return timer;
    }
    
    public static ImageIcon lightenIcon(ImageIcon icon, float brightness) {

        int w = icon.getIconWidth();
        int h = icon.getIconHeight();

        BufferedImage buff = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = buff.createGraphics();
        g.drawImage(icon.getImage(), 0, 0, null);
        g.dispose();

        float[] brightArr = {brightness, brightness, brightness, 1f};
        float[] offsetArr = {0, 0, 0, 0};

        RescaleOp op = new RescaleOp(brightArr, offsetArr, null);
        BufferedImage bright = op.filter(buff, null);

        return new ImageIcon(bright);
    }

    public static void addLightenOnHover(JButton btn, float brightness) {

        ImageIcon normal = (ImageIcon) btn.getIcon();
        ImageIcon bright = lightenIcon(normal, brightness);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setIcon(bright);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setIcon(normal);
            }
        });
    }

    //HOVER SOUND

    public static void addHoverSFX(JButton button, String soundPath) {

        button.addMouseListener(new java.awt.event.MouseAdapter() {

            private Timer hoverTimer;

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {

                hoverTimer = new Timer(300, ev -> {
                    Sound.playSFX(soundPath);   // path already fixed by caller
                    hoverTimer.stop();
                });

                hoverTimer.setRepeats(false);
                hoverTimer.start();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {

                if (hoverTimer != null && hoverTimer.isRunning()) {
                    hoverTimer.stop();
                }
            }
        });
    }

    public static void showErrorAutoClose(Component parent) {

        Sound.playSFX("/assets/Error.wav");

        JDialog dialog = new JDialog((Frame) null, "Error", true);
        dialog.setUndecorated(true);

        // Load error popup image correctly from classpath
        ImageIcon icon = new ImageIcon(
                HelpersUI.class.getResource("/assets/Error.png")
        );

        JLabel label = new JLabel(icon, JLabel.CENTER);
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        dialog.add(label);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);

        dialog.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }
}