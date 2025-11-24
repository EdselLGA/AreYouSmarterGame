package Utils;

import java.io.File;
import javax.sound.sampled.*;

public class Sound {

    private static Clip bgmClip;

    public static boolean isMenuMusicPlaying = false;
    public static boolean comingFromGame = false;

    public static float MenusVolume = -25.0f;  // default menu BGM volume (dB)
    public static float SFXVolume   = -5.0f;   // default SFX volume (dB)

    // Save menu music position
    public static long menuMusicPosition = 0;

    // Getter so SettingPanel can access the clip safely
    public static Clip getBGMClip() {
        return bgmClip;
    }


    /** ---------------------------
     *  PLAY SOUND EFFECT
     *  --------------------------- */
    public static void playSFX(String path) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(path));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);

            try {
                FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gain.setValue(SFXVolume);
            } catch (Exception ignore) {}

            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /** ---------------------------
     *  PLAY BGM (MENU OR GAME)
     *  --------------------------- */
    public static void playBGM(String path) {

        if (isMenuMusicPlaying) {
            return;
        }

        try {
            stopOnly();

            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(path));
            bgmClip = AudioSystem.getClip();
            bgmClip.open(audio);

            try {
                FloatControl gain = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);
                gain.setValue(MenusVolume);
            } catch (Exception ignore) {}

            // Resume menu music
            if (path.contains("BgMusic") && menuMusicPosition > 0) {
                bgmClip.setMicrosecondPosition(menuMusicPosition);
            } else {
                menuMusicPosition = 0;
            }

            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start();

            isMenuMusicPlaying = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /** Stop BGM but keep saved menu position */
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


    /** Completely stop BGM and reset */
    public static void stopBGM() {
        if (bgmClip != null && bgmClip.isRunning())
            bgmClip.stop();

        menuMusicPosition = 0;
        isMenuMusicPlaying = false;
    }


    /** Adjust BGM volume by percent (unused but safe to keep) */
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


