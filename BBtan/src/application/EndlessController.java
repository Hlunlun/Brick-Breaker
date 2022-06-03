package application;


import java.util.Iterator;

import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
	
	
    //MouseEvent : Pressed, Dragged, Released
    EventHandler<MouseEvent>eventHandler=new EventHandler<MouseEvent>(){
    	@Override
    	public void handle(MouseEvent event) {
    		
    		//reference
    		//https://www.tabnine.com/code/java/methods/javafx.animation.Timeline/getStatus
    		if(timeline.getStatus() == Status.RUNNING)return;
    		if(pauseBtn.isPressed()||pauseBtn.isHover())return; 
    		
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
	 		double k=Math.max(Math.log10(Math.abs(deltaY)),Math.log10(Math.abs(deltaX)));
	 		k=-1*Math.floor(k);
 			double shrink=Math.pow(10, k);
 			deltaX*=shrink;
 			deltaY*=shrink;
	 		
 			checkSpeed();
 			double degree=Math.atan(deltaY/deltaX);
	   		if (mouseX < circleX && mouseY <circleY) {
				deltaX *= -1;
				deltaY *= -1;
				
				if(Math.abs(degree)<Math.PI/18) {
		   			deltaX=-speed*Math.cos(Math.PI/18);
		   			deltaY=-speed*Math.sin(Math.PI/18);
				}
			}

			if (mouseX > circleX &&(Math.abs(degree)<Math.PI/18)||mouseY>=circleY) {
		   			deltaX=speed*Math.cos(Math.PI/18);
		   			deltaY=-speed*Math.sin(Math.PI/18);
			}
			
			
	   		
	   		
	 	   x=circle.getLayoutX();
	 	   y=circle.getLayoutY();
	 	   
	 	  if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
	    		drawLine.play();            
	            scene.getChildren().add(polyline);
	    		timeline.stop();    
	    		   		
	    	}
	    	
	    	if(event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {

	    		drawLine.stop();
	    		polyline.getPoints().clear();
	    		scene.getChildren().removeAll(polyline);
	    		
	    		drawLine.play();  	
	            
	            scene.getChildren().add(polyline);
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
    
    private void checkSpeed() {
    	
    	double length=Math.pow(deltaX,2)+Math.pow(deltaY,2);
    	    	
		if(length!=Math.pow(speed, 2)) {
    		double scale=Math.sqrt(Math.pow(speed, 2)/length);   		
    		    		
    		deltaX*=scale;
    		deltaY*=scale;
    		
    	}
    }
    
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
            
            
            //reference
            //https://stackoverflow.com/questions/36406408/exception-in-thread-javafx-application-thread-duplicate-children-added
            
        }
    }));
    
    
	   
    //initialize the timeline, checkGameOver, mode: SightLine
    @Override
    public void initialize() {
    	pauseBtn.setVisible(false);
    	
    	Mode.mode=Mode.Endless;
        
        drawLine.setCycleCount(Animation.INDEFINITE);   
        checkGameOver.setCycleCount(Animation.INDEFINITE); 
                
        
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
    	drawLine.stop();
        
        deltaX = 0;
        deltaY = -1;
       
        
        scene.getChildren().removeAll(bricks);
        scene.getChildren().removeAll(bombs);
        
        scene.getChildren().removeIf(node->node.getClass().getName().equals("javafx.scene.Group"));
        
        //reference
        //https://stackoverflow.com/questions/37104215/error-exception-in-thread-javafx-application-thread
        //use iterator to clear arraylist instead of arrylist.clear()
        Iterator<Brick> br = bricks.iterator();        
        while (br.hasNext()) {  
        	br.next();
        }         
        Iterator<Bomb>bo = bombs.iterator(); 
        while (bo.hasNext()) {  
        	bo.next(); 
        }

        
        scene.removeEventFilter(MouseEvent.MOUSE_DRAGGED,eventHandler);
        scene.removeEventFilter(MouseEvent.MOUSE_PRESSED,eventHandler);
        scene.removeEventFilter(MouseEvent.MOUSE_RELEASED,eventHandler);
                
        startBtn.setVisible(true);        
        menuBtn.setVisible(true);
    	
    }
    
    
}
