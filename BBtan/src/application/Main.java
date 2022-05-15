package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
		
	//Application�JavaFX蝔���嚗遙雿avafx��蝔���閬匱�閰脤�蒂��神start()�瘜�
	
	@Override
	public void start(Stage stage) {
		try {

			//Parent root=FXMLLoader.load(getClass().getResource("Homepage.fxml"));
      //雿隞亙Mode摰象omepage,����璅��撖恍����見撖急��末
			Parent root=FXMLLoader.load(getClass().getResource(Mode.Homepage.getPath()));

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
		launch(args);//銋隞亙神雿aunch()
		
		System.out.println("�main�瘜�");
		
		
	}
	
	@Override
    public void init(){
        System.out.println("�����瘜�");
     }
	@Override    
    public void stop() throws Exception {
		super.stop();
		System.out.println("��stop()�瘜�");
	}
}

