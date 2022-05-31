package application;


import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class BallCount extends Label{
	
	
	private ParallelTransition parTransition=new ParallelTransition();
	
	public BallCount(Integer number) {
		
		Font font = Font.font("Impact", FontWeight.NORMAL, FontPosture.REGULAR, 20);
		setFont(font);		
		setAlignment(Pos.CENTER);
		setTextFill(Color.BLACK);	
		setText("-"+number.toString());		
		
	}
		
	public void parallelAnimation() {
		
		parTransition.setNode(this);
		//Add the Children to the ParallelTransition
		parTransition.getChildren().addAll(faded(),up());
		//Let the animation run forever
		parTransition.setCycleCount(1);
		//Play the Animation
		parTransition.play();	
				
	}
	
	private FadeTransition faded() {
		
		//Set up a Fade Transition
		FadeTransition fadeTransition=new FadeTransition(Duration.seconds(3));
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		//Let the animation run two times
		fadeTransition.setCycleCount(1);
		//Reverse direction on alternating cycles
		fadeTransition.setAutoReverse(false);
		
		return fadeTransition;
	}
	
	
	private TranslateTransition up() {
		
	    TranslateTransition translateTransition=new TranslateTransition(Duration.seconds(3),this);
	    translateTransition.setToY(getLayoutY()-300);
        translateTransition.setAutoReverse(false);
                
        return translateTransition;
	}
	

}
