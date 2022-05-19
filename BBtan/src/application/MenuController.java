package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class MenuController implements Initializable {
	
	private AudioManager audioManager = new AudioManager();
	
	@FXML
	private Button simpleBtn;
	
	@FXML
	private Button lineBtn;
	
	@FXML
	private Button countDownBtn;
	
	@FXML
	private Button fallingBtn;
	
	@FXML
	private Label topic;
	
	@FXML
	private AnchorPane scene;
	
	
	
	//	private TranslateTransition translate=new TranslateTransition();
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
	    
	        
	}
	
	private SceneController sceneController=new SceneController();
	
	public void simpleMode(ActionEvent event) throws IOException {
		
		audioManager.playMusic(Music.click);
		sceneController.switchScene(event, Mode.Simple.getPath());
	}
	
	public void sightLineMode(ActionEvent event) throws IOException {
		
		audioManager.playMusic(Music.click);
		sceneController.switchScene(event, Mode.Endless.getPath());
	}

	public void countDownMode(ActionEvent event) throws IOException {
		
		audioManager.playMusic(Music.click);
		sceneController.switchScene(event, Mode.CountDown.getPath());
	}
	public void fallingBricksMode(ActionEvent event) throws IOException {
		
		audioManager.playMusic(Music.click);
		sceneController.switchScene(event, Mode.FallingBricks.getPath());
	}

	public void backToHome(ActionEvent event) throws IOException {
		
		audioManager.playMusic(Music.click);
		sceneController.switchScene(event, Mode.Homepage.getPath());
	}
}
