import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicTest {

    public static final String MUSIC_FILE = "https://github.com/UQdeco2800/2022-ext-studio-1/blob/main/source/core/src/main/com/deco2800/game/music/LaboratoryBGM.wav";

    public static void main(String[] args) throws LineUnavailableException,
            UnsupportedAudioFileException, IOException {

         /*
          * Gets the audio input stream
          /
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(MUSIC_FILE));
         /*
          * Gets the audio encoding object
          */
        AudioFormat audioFormat = audioInputStream.getFormat();

         /*
          * Set up data entry
          */
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,
                audioFormat, AudioSystem.NOT_SPECIFIED);
        SourceDataLine sourceDataLine =
                (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        sourceDataLine.open(audioFormat);
        sourceDataLine.start();

        /*
         * Read data from the input stream and send it to the mixer
         */
        int count;
        byte tempBuffer[] = new byte[1024];
        while ((count = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
            if (count > 0) {
                sourceDataLine.write(tempBuffer, 0, count);
            }
        }

         /*
         * Empty the data buffer and turn off the input
         */
        sourceDataLine.drain();
        sourceDataLine.close();
    }

}
