package viewmodel;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Sound {
    private static final String AUDIO_PATH = "assets/audios/";

    public static Clip playSound(String filename, boolean looping) {
        Clip audioClip = null;
        try {
            // Load the audio file
            File audioFile = new File(AUDIO_PATH + filename).getAbsoluteFile();
            // Create an audio input stream
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            // Get a clip resource from the audio input stream
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream); // Open the clip
            // Start playing the clip
            audioClip.start();
            // Loop the clip if the looping flag is true
            if (looping) {
                audioClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return audioClip;
    }

    // Stop the sound
    public static void stopSound(Clip audioClip) {
        if (audioClip != null) {
            audioClip.stop();
        }
    }
}