package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class HomepageController implements Initializable{
	
	private AudioManager audioManager = new AudioManager();
	
	@FXML
	private AnchorPane scene;
	
	@FXML
	private Button startButton;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
	        
	}

	
	private SceneController sceneController = new SceneController();
	
	public void intomenu(ActionEvent event) throws IOException {
		
		audioManager.playMusic(Music.click);
		sceneController.switchScene(event, "Loadingpage.fxml");
		//AudioClip buzzer = new AudioClip(getClass().getResource("../sound/click.mp3").toExternalForm());
		//buzzer.play();
	}
	
	
	
}