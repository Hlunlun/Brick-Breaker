package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SceneController{

	private Stage stage=new Stage();
	private Scene scene;
	private Parent root;	
	private Image iconImage = new Image("file:src/Image/icon-PhotoRoom.png");
	
	
	public void initialize() {
		
		stage.getIcons().add(iconImage);
		stage.setResizable(false);
		stage.setTitle("BRICK BREAKER");
	}
	
	public void switchScene(ActionEvent event,String sceneName)throws IOException{		
		
		root =FXMLLoader.load(getClass().getResource(sceneName));
		Node node=(Node)event.getSource();
		stage=(Stage)node.getScene().getWindow();
		scene=new Scene(root);
		initialize();
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchScene(KeyEvent event,String sceneName)throws IOException{		
		root =FXMLLoader.load(getClass().getResource(sceneName));
		Node node=(Node)event.getSource();
		stage=(Stage)node.getScene().getWindow();
		scene=new Scene(root);
		initialize();
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchScene(AnchorPane scene,String sceneName)throws IOException{		
		
		root = FXMLLoader.load(getClass().getResource(sceneName));		
		Stage stage=new Stage();
		stage = (Stage)scene.getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.getIcons().add(iconImage);
		stage.setResizable(false);
		stage.setTitle("BRICK BREAKER");
		stage.show();
	}

	
		
	

}
