package group2.keybarricade.game;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class MusicPlayer {

    private final String[] musicFiles = new String[]{"/sounds/wethands.wav", "/sounds/SubwooferLulaby.wav"};

    public void startPlaying() {
        new Thread() {

            int currentSong = 0;

            @Override
            public void run() {
                try {
                    AudioStream audioStream = new AudioStream(group2.keybarricade.game.KeyBarricade.class.getResourceAsStream(musicFiles[currentSong]));
                    AudioPlayer.player.start(audioStream);

                    while (true) {
                        if (audioStream.available() <= 0) {
                            ++currentSong;
                            if (currentSong >= musicFiles.length) {
                                currentSong = 0;
                            }
                            audioStream = new AudioStream(group2.keybarricade.game.KeyBarricade.class.getResourceAsStream(musicFiles[currentSong]));
                            AudioPlayer.player.start(audioStream);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }.start();
    }

}
