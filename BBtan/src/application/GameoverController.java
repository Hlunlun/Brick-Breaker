package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GameoverController {
	
	@FXML
	private Label score;
	
	@FXML
	private Button menuBtn;
	
	@FXML
	private Button againBtn;
	
	private SceneController sceneController=new SceneController();
	

	private AudioManager audioManager = new AudioManager();
	
	
   @FXML
    private void again(ActionEvent event) throws IOException {
	   audioManager.playMusic(Music.click);
	   sceneController.switchScene(event,Mode.mode.getPath());
    }

    @FXML
    private void goMenu(ActionEvent event) throws IOException {    	
    	audioManager.playMusic(Music.click);
    	sceneController.switchScene(event,Mode.Menu.getPath());
    }
    
    public void setScore(String text) {
    	score.setText(text);
    }

}
