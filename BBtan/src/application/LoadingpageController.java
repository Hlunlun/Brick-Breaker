package application;

import java.io.IOException;
import java.net.URL;
import javafx.util.Duration;
import java.util.ResourceBundle;
import java.util.jar.Attributes.Name;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoadingpageController implements Initializable{

	@FXML
	private AnchorPane scene;
	
	@FXML
	private ProgressBar loadingBar = new ProgressBar(0);
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		KeyValue startKeyValue = new KeyValue(loadingBar.progressProperty(), 0);
		KeyValue endKeyValue = new KeyValue(loadingBar.progressProperty(), 1);
		KeyFrame frame = new KeyFrame(Duration.seconds(2), startKeyValue, endKeyValue);
		Timeline taskTimeline = new Timeline(frame);
		taskTimeline.playFromStart();
		/*
		SceneController sceneController = new SceneController();
		try {
			sceneController.switchScene("Menu.fxml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
    }
	

	
	public void intogame(ActionEvent event) throws IOException {
		SceneController sceneController = new SceneController();
		sceneController.switchScene(event, Mode.Menu.getPath());
	}
}
