package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;


public class HomepageController{
	
	private AudioManager audioManager = new AudioManager();
	
	@FXML
	private AnchorPane scene;
	
	@FXML
	private Button startButton;
	
	private SceneController sceneController = new SceneController();
	
	public void intomenu(ActionEvent event) throws IOException {
		
		audioManager.playMusic(Music.click);
		sceneController.switchScene(event, "Loadingpage.fxml");
	}
	
	
	
}