package com.example.view;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

    public static float MenusVolume = -10.0f;  // dB for BGM
    public static float SFXVolume   = -5.0f;   // dB for SFX

    public static boolean isMenuMusicPlaying = false;
    public static boolean comingFromGame = false;

    private static Clip bgmClip;

    private static Clip loadClip(String path) {
        try {
            // Load from resources folder using classpath
            URL url = Sound.class.getResource(path);

            if (url == null) {
                System.err.println("[Sound] FILE NOT FOUND: " + path);
                return null;
            }

            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            return clip;

        } catch (Exception e) {
            System.err.println("[Sound] ERROR loading: " + path);
            e.printStackTrace();
            return null;
        }
    }

    private static void applyVolume(Clip clip, float decibels) {
        try {
            FloatControl gain =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gain.setValue(decibels);
        } catch (Exception ignored) {}
    }

    public static void playBGM(String path) {
        stopBGM(); // stop previous

        bgmClip = loadClip(path);

        if (bgmClip != null) {
            applyVolume(bgmClip, MenusVolume);
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start();
        }
    }

    public static void stopBGM() {
        if (bgmClip != null) {
            try {
                bgmClip.stop();
                bgmClip.flush();
                bgmClip.setFramePosition(0);
            } catch (Exception ignored) {}
        }
        bgmClip = null;
    }

    public static Clip getBGMClip() {
        return bgmClip;
    }

    public static void playSFX(String path) {
        new Thread(() -> {
            Clip sfx = loadClip(path);
            if (sfx != null) {
                applyVolume(sfx, SFXVolume);
                sfx.start();
            }
        }).start();
    }

    public static void setBGMVolume(float volume) {
        if (bgmClip != null) {
            try {
                FloatControl gain = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);
                gain.setValue(volume);
            } catch (Exception ignored) {}
        }
    }

}