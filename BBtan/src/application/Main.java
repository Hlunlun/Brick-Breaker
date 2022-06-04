package application;
	

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	
		
	@Override
	public void start(Stage stage) {
		try {

			Parent root=FXMLLoader.load(getClass().getResource(Mode.Homepage.getPath()));			
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("BRICK BREAKER");
			Image iconImage = new Image("file:src/Image/icon-PhotoRoom.png");
			stage.getIcons().add(iconImage);
			stage.show();			
			
		} catch(Exception e) {
			e.printStackTrace();
		}		
		
	}
	
	public static void main(String[] args){
		
		launch(args);		
		
	}
}

