package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class WinController {
	
	private AudioManager audioManager = new AudioManager();
	
	@FXML
	private Button menuBtn;
	
	@FXML
	private Button againBtn;
	
	private SceneController sceneController=new SceneController();
	
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
	

}
