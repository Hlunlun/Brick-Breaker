package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class SceneController {

	private Stage stage;
	private Scene scene;
	private Parent root;	
	
	public void switchScene(ActionEvent event,String sceneName,String styleName)throws IOException{		
		root =FXMLLoader.load(getClass().getResource(sceneName));
		Node node=(Node)event.getSource();
		stage=(Stage)node.getScene().getWindow();
		scene=new Scene(root);
		scene.getStylesheets().add(getClass().getResource(styleName).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchScene(ActionEvent event,String sceneName)throws IOException{		
		
		root =FXMLLoader.load(getClass().getResource(sceneName));
		Node node=(Node)event.getSource();
		stage=(Stage)node.getScene().getWindow();
		scene=new Scene(root);
		
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchScene(KeyEvent event,String sceneName,String styleName)throws IOException{		
		root =FXMLLoader.load(getClass().getResource(sceneName));
		Node node=(Node)event.getSource();
		stage=(Stage)node.getScene().getWindow();
		scene=new Scene(root);
		scene.getStylesheets().add(getClass().getResource(styleName).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchScene(KeyEvent event,String sceneName)throws IOException{		
		root =FXMLLoader.load(getClass().getResource(sceneName));
		Node node=(Node)event.getSource();
		stage=(Stage)node.getScene().getWindow();
		scene=new Scene(root);
		
		stage.setScene(scene);
		stage.show();
	}
		
	

}
