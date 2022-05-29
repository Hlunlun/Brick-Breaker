package application;
	
import java.net.MalformedURLException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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
		
		//https://www.796t.com/content/1542293253.html

		launch(args);//
		
		System.out.println("");
		
		
	}
	
	@Override
    public void init(){
        System.out.println("");

     }
	@Override    
    public void stop() throws Exception {
		super.stop();

		System.out.println("");

	}
}

