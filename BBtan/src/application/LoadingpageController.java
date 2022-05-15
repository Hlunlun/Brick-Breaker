package application;

import java.io.IOException;
import java.net.URL;
import javafx.util.Duration;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoadingpageController implements Initializable{

	@FXML
	private AnchorPane scene;
	
	@FXML
	private ProgressBar loadingBar = new ProgressBar(0);
	
	Timeline goMenuLine=new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent arg0) {				
			
			try {
				
				Parent root = FXMLLoader.load(getClass().getResource(Mode.Menu.getPath()));
				
				Stage stage = (Stage)scene.getScene().getWindow();
				stage.setScene(new Scene(root));
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}));
		
	
	
       	
	
	
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
		goMenuLine.play();
    }
	
}
