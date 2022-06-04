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
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

public class LoadingpageController implements Initializable{

	@FXML
	private AnchorPane scene;
	
	@FXML
	private ProgressBar loadingBar = new ProgressBar(0);
	
	//reference
	//https://stackoverflow.com/questions/13246211/how-to-get-stage-from-controller-during-initialization
	Timeline goMenuLine=new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent arg0) {				
			
			try {
				
				new SceneController().switchScene(scene, Mode.Menu.getPath());
				
			} catch (IOException e) {
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
		
		goMenuLine.play();
    }
	
}
