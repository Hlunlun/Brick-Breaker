package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
		
	//Application是JavaFX程式的入口，任何javafx應用程式程式都要繼承該類並重寫start()方法
	
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
		launch(args);//也可以寫作launch()
		
		System.out.println("這是main方法");
		
		
	}
	
	@Override
    public void init(){
        System.out.println("這是初始化方法");
     }
	@Override    
    public void stop() throws Exception {
		super.stop();
		System.out.println("這個是stop()方法");
	}
}

