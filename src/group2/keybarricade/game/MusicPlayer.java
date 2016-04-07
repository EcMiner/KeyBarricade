package group2.keybarricade.game;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class MusicPlayer {

    /**
     * Define what music files we want to go through and play
     */
    private final String[] musicFiles = new String[]{"/sounds/wethands.wav", "/sounds/SubwooferLulaby.wav"};

    /**
     * Starts playing all the music files and looping through
     */
    public void startPlaying() {
        // Create a new thread so we can do an infinite while loop without pausing the game
        new Thread() {

            // The current index number of the array
            int currentSong = 0;

            @Override
            public void run() {
                try {
                    // Create an audiostream for the first song
                    AudioStream audioStream = new AudioStream(group2.keybarricade.game.KeyBarricade.class.getResourceAsStream(musicFiles[currentSong]));
                    // Start playing the audio file
                    AudioPlayer.player.start(audioStream);

                    // Do an infinite loop which will check if a song is finished, then start the next
                    while (true) {
                        // Check if the song has finished, AudioStream#available() will return the remaining bytes that need to be played
                        if (audioStream.available() <= 0) {
                            // Increment the index
                            ++currentSong;

                            // Check if the next song is out of bounds
                            if (currentSong >= musicFiles.length) {
                                // Reset the index to the first song
                                currentSong = 0;
                            }

                            // load and start playing the next song
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
