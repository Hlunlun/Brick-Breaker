package application;


import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class EndlessController extends BBtan{
	
	private AudioManager audioManager = new AudioManager();
	
	//game rule
	@FXML
	private Text text1;
	
	@FXML
	private Text text2;
	
	@FXML
	private Text text3;
	
	@FXML
	private Text text4;
	
	@FXML
	private Text text5;
	
	@FXML
	private Text text6;
	
	@FXML
	private Text text7;
	
	//the line to point to target
	private Polyline polyline = new Polyline();
	
    private double x;
    private double y;
	
	//circle will go up and down while waiting for the user to start the game
    private TranslateTransition translateTransition=new TranslateTransition();
    
    	
    //MouseEvent : Pressed, Dragged, Released
    EventHandler<MouseEvent>eventHandler=new EventHandler<MouseEvent>(){
    	@Override
    	public void handle(MouseEvent event) {
    		
    		//reference
    		//https://www.tabnine.com/code/java/methods/javafx.animation.Timeline/getStatus
    		if(timeline.getStatus() == Status.RUNNING)return ;
    		
	 		double circleX=circle.getLayoutX();
	 	   	double circleY=circle.getLayoutY();
	 	   	
	 	   	double mouseX=event.getSceneX();
	 	   	double mouseY=event.getSceneY();
	 	
	 	   	boolean vertical=mouseX==circleX;
	 	   	
	 	   	///line equation
	 	   	double slope=vertical?-1:(circleY-mouseY)/(circleX-mouseX);
 	   	
	 	   	
	 	   	
	 	   	double scale=1;
	 	   	
	 	   	deltaX=vertical?0:1*scale;
	 		deltaY=slope*scale;

	 		///check if the ball is out of the scene
	 		double k=Math.log10(Math.abs(deltaY));
	 		k=-1*Math.floor(k);
 			double shrink=Math.pow(10, k);
 			deltaX*=shrink;
 			deltaY*=shrink;
	 		
	 		
	 	   	
	 	   	if(mouseX<circleX&&mouseY<circleY) {
	 	   		deltaX*=-1;
	 	   		deltaY*=-1;
	 	   	}
	 	   	
	 	   	if(mouseX>circleX&&mouseY>circleY) {
		   		deltaX*=-1;
		   		deltaY*=-1;
		   	}		
	    	
	 	   x=circle.getLayoutX();
	 	   y=circle.getLayoutY();
	 	   
	 	  if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
	    		drawLine.play(); 
	    		timeline.stop();    
	    		   		
	    	}
	    	
	    	if(event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {

	    		drawLine.stop();
	    		polyline.getPoints().clear();
	    		scene.getChildren().removeAll(polyline);
	    		
	    		drawLine.play();  	
	    		
	    		timeline.stop();   
	    		
	    		
	    	}
	    	
	    	if(event.getEventType()==MouseEvent.MOUSE_RELEASED){    		
	    		
	    		new AudioManager().playMusic(Music.ballup);
	    		
	    		timeline.play();  		
	    		drawLine.stop();
	    		polyline.getPoints().clear();
	    		scene.getChildren().removeAll(polyline);
	    		
	    	}
	    	
			
		}
    };
		
    //draw the sight line
    Timeline drawLine = new Timeline(new KeyFrame(Duration.ONE, new EventHandler<ActionEvent>() {
           	
    	@Override
        public void handle(ActionEvent actionEvent) {    
    		
    		//refer to 
    		//https://stackoverflow.com/questions/36589770/customize-the-stroke-of-a-javafx-polyline
    		polyline.setStroke(Color.ROYALBLUE);
    		polyline.setStrokeWidth(3);
    	    polyline.getStrokeDashArray().add(10d);
    		polyline.getPoints().addAll(x,y);    		
            
    		x+=1000*deltaX;
            y+=1000*deltaY;   
                       
            scene.getChildren().add(polyline);
            
            
        }
    }));
    
	   
    //initialize the timeline, checkGameOver, mode: SightLine
    @Override
    public void initialize() {
    	pauseBtn.setVisible(false);
    	
    	Mode.mode=Mode.Endless;
        
        drawLine.setCycleCount(Animation.INDEFINITE);   
        checkGameOver.setCycleCount(Animation.INDEFINITE); 
        
        translateTransition.setNode(circle);
        translateTransition.setDuration(Duration.millis(300));
        translateTransition.setFromY(scene.getLayoutBounds().getMaxY());
        translateTransition.setToY(scene.getLayoutBounds().getMaxY()-50);;
        translateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        translateTransition.setAutoReverse(true);
        //translateTransition.play();
        
    }

    
    //set the scene of the game and called in startGameButtonAction
    public void startGame(){
    	audioManager.playMusic(Music.startgame);
    	//game rule disappear
    	text1.setVisible(false);
     	text2.setVisible(false);
     	text3.setVisible(false);
     	text4.setVisible(false);
     	text5.setVisible(false);
     	text6.setVisible(false);
     	text7.setVisible(false);
    	
        drawLine.stop();
        translateTransition.stop();   
        
                
        scene.addEventFilter(MouseEvent.MOUSE_DRAGGED,eventHandler);
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED,eventHandler);
        scene.addEventFilter(MouseEvent.MOUSE_RELEASED,eventHandler);        
        
    }
  
    //after game over, reset the game
    @Override
    public void Reset(){
    	
    	startBtn.setVisible(true);
        menuBtn.setVisible(true);
        pauseBtn.setVisible(false);
        
        //game rule appear
        text1.setVisible(true);
        text2.setVisible(true);
        text3.setVisible(true);
        text4.setVisible(true);
        text5.setVisible(true);
        text6.setVisible(true);
        text7.setVisible(true);
    	
    	timeline.stop();    
    	checkGameOver.stop();
        
        deltaX = 0;
        deltaY = -1;
       
        
        scene.getChildren().removeAll(bricks);
        scene.getChildren().removeAll(bombs);
        
        bricks.clear();
        bombs.clear();
        
        scene.removeEventFilter(MouseEvent.MOUSE_DRAGGED,eventHandler);
        scene.removeEventFilter(MouseEvent.MOUSE_PRESSED,eventHandler);
        scene.removeEventFilter(MouseEvent.MOUSE_RELEASED,eventHandler);
                
        startBtn.setVisible(true);        
        menuBtn.setVisible(true);
    	
    }
    
    
}
