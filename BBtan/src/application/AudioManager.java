package application;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioManager {
	
	public void playMusic(Music music) {
		
		Media sound =new Media(new File(music.getPath()).toURI().toString());	
		
		new MediaPlayer(sound).play();
	}

}
