package application;

import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;

public class EndlessController extends BBtan{	
	
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
 	   	
	 	   	double scale=1.2;
	 	   	
	 	   	deltaX=vertical?0:1*scale;
	 		deltaY=slope*scale;
	 	   	
	 	   	
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
    		polyline.setStroke(Color.KHAKI);
    		polyline.setStrokeWidth(3);
    	    polyline.getStrokeDashArray().add(10d);
    		polyline.getPoints().addAll(x,y);    		
            
    		x+=300*deltaX;
            y+=300*deltaY;   
                       
            scene.getChildren().add(polyline);
            
            
        }
    }));
    
	   
    //initialize the timeline, checkGameOver, mode: SightLine
    @Override
    public void initialize() {
    	
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
    	
                
        drawLine.stop();
        translateTransition.stop();   
        
                
        scene.addEventFilter(MouseEvent.MOUSE_DRAGGED,eventHandler);
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED,eventHandler);
        scene.addEventFilter(MouseEvent.MOUSE_RELEASED,eventHandler);        
        
    }
  
    //after game over, reset the game
    @Override
    public void Reset(){
    	
    	timeline.stop();    
    	checkGameOver.stop();
        
        deltaX = 0;
        deltaY = -1;
         
        scene.getChildren().removeAll(bricks);
        
        bricks.clear();
        
        scene.removeEventFilter(MouseEvent.MOUSE_DRAGGED,eventHandler);
        scene.removeEventFilter(MouseEvent.MOUSE_PRESSED,eventHandler);
        scene.removeEventFilter(MouseEvent.MOUSE_RELEASED,eventHandler);
                
        startBtn.setVisible(true);        
        menuBtn.setVisible(true);
    	
    }
    
    
}
