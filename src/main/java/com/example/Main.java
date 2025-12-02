package com.example;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.example.view.MainFrame;

public class Main {
    public static void main(String[] args) {
        Font myFont = FontUtil.loadFont().deriveFont(16f);
        setGlobalFont(myFont);

        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            }
        );
    }

    public static void setGlobalFont(Font baseFont) {
    java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();

    while (keys.hasMoreElements()) {
        Object key = keys.nextElement();
        Object value = UIManager.get(key);

        if (value instanceof Font) {
            System.out.println("registered to"+ key.toString());
            UIManager.put(key, baseFont.deriveFont(((Font) value).getStyle(), ((Font) value).getSize2D()));
        }
    }
}
public class FontUtil {
    public static Font loadFont() {
        try {
            InputStream is = FontUtil.class.getResourceAsStream("/pixel.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            System.out.println("did you register");
            return font;
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.PLAIN, 12);
        }
    }
}

}