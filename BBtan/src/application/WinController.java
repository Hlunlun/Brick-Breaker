package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class WinController implements Initializable{
	
	private AudioManager audioManager = new AudioManager();
	
	@FXML
	private AnchorPane scene;
	
	@FXML
	private Button menuBtn;
	
	@FXML
	private Button againBtn;
	
	private SceneController sceneController=new SceneController();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Mode.mode=Mode.Win;
		
	}
	
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
