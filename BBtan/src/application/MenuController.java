package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class MenuController implements Initializable {
	
	@FXML
	private Button simpleBtn;
	
	@FXML
	private Button lineBtn;
	
	@FXML
	private Button fallingBtn;
	
	@FXML
	private Label topic;
	
	@FXML
	private AnchorPane scene;
	
	private TranslateTransition translate=new TranslateTransition();
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
	        
		translate.setNode(topic);
		translate.setDuration(Duration.seconds(5));
		translate.setFromX(scene.getBoundsInLocal().getMinX()-300);
		translate.setToX(scene.getBoundsInLocal().getMaxX()+5);;
		translate.setCycleCount(TranslateTransition.INDEFINITE);
		translate.setAutoReverse(true);
		translate.play();
	        
	}
	
	private SceneController sceneController=new SceneController();
	
	public void simpleMode(ActionEvent event) throws IOException {
		
		sceneController.switchScene(event, "Simple.fxml");
	}
	
	public void sightLineMode(ActionEvent event) throws IOException {
		
		sceneController.switchScene(event, "SightLine.fxml");
	}
	public void Mode4(ActionEvent event) throws IOException {
		
		sceneController.switchScene(event, "Mode4.fxml");
	}

}
