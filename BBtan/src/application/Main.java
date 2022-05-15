package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
		
	@Override
	public void start(Stage stage) {
		try {

			Parent root=FXMLLoader.load(getClass().getResource(Mode.Homepage.getPath()));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("BBTAN");
			stage.show();			
			
		} catch(Exception e) {
			e.printStackTrace();
		}		
		
	}
	
	public static void main(String[] args) {
		
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

