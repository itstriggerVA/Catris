package main;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {

    Clip musicClip;
    URL url[] = new URL[15];

    public Sound() {

        url[0] = getClass().getResource("/main-theme.wav");
        url[1] = getClass().getResource("/clear1line.wav");
        url[2] = getClass().getResource("/clear2line.wav");
        url[3] = getClass().getResource("/clear3line.wav");
        url[4] = getClass().getResource("/clear4line.wav");
        url[5] = getClass().getResource("/move-sound.wav");
        url[6] = getClass().getResource("/rotate-sound.wav");
        url[7] = getClass().getResource("/cantRotate.wav");
        url[8] = getClass().getResource("/placed-sound.wav");
        url[9] = getClass().getResource("/gameover.wav");
        url[10] = getClass().getResource("/win.wav");

//        for (int i = 0; i < url.length; i++) {
//            System.out.println("URL " + i + ": " + url[i]);
//        } //debug code for checking if all files are available
    }

    public void play(int i, boolean music) { //This boolean is for checking if music or sound effect

        try {
            //Used to take the audio file from the stream and put it into the clip object
            AudioInputStream ais = AudioSystem.getAudioInputStream(url[i]);
            Clip clip = AudioSystem.getClip();

            if (music) {
                musicClip = clip;
            }

            clip.open(ais);
            clip.addLineListener(new LineListener() { //adding the LineListener to the clip
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) { //Setting for when audio has stopped
                        clip.close(); //Stops the clip, freeing memory
                    }
                }
            });
            ais.close();
            clip.start();

        } catch (UnsupportedAudioFileException e) {
            System.out.println("UnsupportedAudioFileException: " + e.getMessage());
            e.printStackTrace();

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
            e.printStackTrace();

        } catch (LineUnavailableException e) {
            System.out.println("LineUnavailableException: " + e.getMessage());
            e.printStackTrace();

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void loop() { //for looping bgm
        if (musicClip != null) {
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            System.out.println("Music clip is null, cannot loop.");
        }
    }

    public void stop() { //for stopping bgm
        if (musicClip != null) {
            musicClip.stop();
            musicClip.close();
        } else {
            System.out.println("Music clip is null, cannot stop.");
        }
    }
}

