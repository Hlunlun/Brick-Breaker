package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
		
	//Application�OJavaFX�{�����J�f�A����javafx���ε{���{�����n�~�Ӹ����í��gstart()��k
	
	@Override
	public void start(Stage stage) {
		try {
			Parent root=FXMLLoader.load(getClass().getResource("Homepage.fxml"));
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("menu.css").toExternalForm());
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
		launch(args);//�]�i�H�g�@launch()
		
		System.out.println("�o�Omain��k");
		
		
	}
	
	@Override
    public void init(){
        System.out.println("�o�O��l�Ƥ�k");
     }
	@Override    
    public void stop() throws Exception {
		super.stop();
		System.out.println("�o�ӬOstop()��k");
	}
}

