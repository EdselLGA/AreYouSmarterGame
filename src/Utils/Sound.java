package Utils;

import java.io.File;
import javax.sound.sampled.*;

public class Sound {

    private static Clip bgmClip;

    public static boolean isMenuMusicPlaying = false;
    public static boolean comingFromGame = false;

    // Save point for menu music
    public static long menuMusicPosition = 0;

    public static void playSFX(String path) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(path));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);

        // soften volume
        try {
            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gain.setValue(-7.0f);   // change this value to control softness
        } catch (Exception ignore) {}

        clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void playBGM(String path) {

        if (isMenuMusicPlaying) {
            return;
        }

        try {
            stopOnly();  

            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(path));
            bgmClip = AudioSystem.getClip();
            bgmClip.open(audio);

            // Default volume
            try {
                FloatControl gain = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);
                gain.setValue(-25.0f);
            } catch (Exception ignore) {}

            // Resume ONLY if this is menu music
            if (path.contains("BgMusic") && menuMusicPosition > 0) {
                bgmClip.setMicrosecondPosition(menuMusicPosition);
            } else {
                menuMusicPosition = 0; // fresh start for new songs
            }

            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start();

            isMenuMusicPlaying = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Stop BGM but DO NOT reset position.
     * This is used when switching temporarily (ex: going to GameScreen, gamescreen -> mainmenu).
     */
    public static void stopOnly() {
        if (bgmClip != null) {
            try {
                menuMusicPosition = bgmClip.getMicrosecondPosition();
            } catch (Exception ignore) {}

            if (bgmClip.isRunning())
                bgmClip.stop();
        }
        isMenuMusicPlaying = false;
    }

    public static void stopBGM() {
        if (bgmClip != null) {
            if (bgmClip.isRunning())
                bgmClip.stop();
        }
        menuMusicPosition = 0;

        isMenuMusicPlaying = false;
    }

    public static void setBGMVolume(int volumePercent) {
        if (bgmClip == null) return;

        try {
            FloatControl gain = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);

            float min = gain.getMinimum();
            float max = gain.getMaximum();

            float value = min + (max - min) * (volumePercent / 100f);

            gain.setValue(value);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

