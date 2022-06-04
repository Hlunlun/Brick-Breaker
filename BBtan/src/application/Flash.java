package application;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Flash extends Rectangle{
	
	private Timeline flashLine = new Timeline(new KeyFrame(Duration.seconds(0.05), evt -> setVisible(false)),
            new KeyFrame(Duration.seconds( 0.1), evt -> setVisible(true)));
	
	
	public void setSize(double width,double height, Color color) {
		
        setWidth(width);
        setHeight(height);
        setOpacity(0.6);
        setFill(color);     
        setStroke(Color.TRANSPARENT);
        
	}
	
	public void makeFlash(double y) {
		
		setY(y);
		flashLine.setCycleCount(Animation.INDEFINITE);		
		flashLine.play();
		
		new Timeline(new KeyFrame(Duration.millis(300), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {				
				flashLine.stop();
				setFill(Color.TRANSPARENT);			
				
	            
			}    	
	    	
	    })).play();
		
	}

}
