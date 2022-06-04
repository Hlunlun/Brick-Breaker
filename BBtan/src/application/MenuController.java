package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class MenuController{
	
	private AudioManager audioManager = new AudioManager();
	
	@FXML
	private Button simpleBtn;
	
	@FXML
	private Button lineBtn;
	
	@FXML
	private Button countDownBtn;
	
	@FXML
	private Button timesBtn;
	
	@FXML
	private Label topic;
	
	@FXML
	private AnchorPane scene;
	
	
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
	public void timesMode(ActionEvent event) throws IOException {
		
		audioManager.playMusic(Music.click);
		sceneController.switchScene(event, Mode.Times.getPath());
	}

	public void backToHome(ActionEvent event) throws IOException {
		
		audioManager.playMusic(Music.click);
		sceneController.switchScene(event, Mode.Homepage.getPath());
	}

	
}
