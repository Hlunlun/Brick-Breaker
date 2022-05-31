package application;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class CountDownController extends BBtan {
	
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
	private Rectangle timerBar;
	
	@FXML
	private Label Showtime;

	private double paddleStartSize = 160;

	Robot robot = new Robot();

	Timer timer = new Timer();
	long min, sec, totalSec;


	// 1 Frame evey 10 millis, which means 100 FPS
	private Timeline paddleTimeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent actionEvent) {
			movePaddle();

			checkCollisionPaddle(paddle);
			
			checkTimer();
			Showtime.setText(format(min)+":"+format(sec));
			
			if(bricks.isEmpty()) {
            	paddleTimeline.stop();
            }
            else {
            	paddleTimeline.play();
            }
		}
	}));

	@Override
	public void initialize() {
		
		pauseBtn.setVisible(false);
		
		Mode.mode=Mode.CountDown;
		
		deltaX = -1;
		deltaY = -3;
		
		paddleTimeline.setCycleCount(Animation.INDEFINITE);
		
		
	}

	@Override
	public void startGame() {
		
		pauseBtn.setVisible(false);
		
		Showtime.setText("01:30");
		
		timeline.play();
		
		paddleTimeline.play();
		paddle.setWidth(paddleStartSize);
		
		Showtime.setText("01:30");
		
		//game rule disappear
		text1.setVisible(false);
     	text2.setVisible(false);
     	text3.setVisible(false);
     	text4.setVisible(false);
     	text5.setVisible(false);
     	
		
		totalSec = 90;
		timer.purge();
		timer = new Timer();
		TimerTask timertask = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(()->{
						//Showtime.setText("min: "+format(min)+" sec: "+format(sec));
						//Showtime.setText(format(min)+":"+format(sec));
						//System.out.println("After 1 sec");
						convertTime();
						if(totalSec < 0)
						{
							Showtime.setText("00:00");
							timer.cancel();
						}
				});
			}
		};
		timer.schedule(timertask, 0, 1000);
	}

	
	

	public void movePaddle() {
		Bounds bounds = scene.localToScreen(scene.getBoundsInLocal());
		double sceneXPos = bounds.getMinX();

		double xPos = robot.getMouseX();
		double paddleWidth = paddle.getWidth();

		if (xPos >= sceneXPos + (paddleWidth / 2) && xPos <= (sceneXPos + scene.getWidth()) - (paddleWidth / 2)) {
			paddle.setLayoutX(xPos - sceneXPos - (paddleWidth / 2));
		} else if (xPos < sceneXPos + (paddleWidth / 2)) {
			paddle.setLayoutX(0);
		} else if (xPos > (sceneXPos + scene.getWidth()) - (paddleWidth / 2)) {
			paddle.setLayoutX(scene.getWidth() - paddleWidth);
		}
	}

	public void checkCollisionPaddle(Rectangle paddle) {

		if (circle.getBoundsInParent().intersects(paddle.getBoundsInParent())) {

			boolean rightBorder = circle.getLayoutX() >= ((paddle.getLayoutX() + paddle.getWidth()) - circle.getRadius());
			boolean leftBorder = circle.getLayoutX() <= (paddle.getLayoutX() + circle.getRadius());
			boolean bottomBorder = circle.getLayoutY() >= ((paddle.getLayoutY() + paddle.getHeight()) - circle.getRadius());
			boolean topBorder = circle.getLayoutY() <= (paddle.getLayoutY() + circle.getRadius());

			if (rightBorder || leftBorder) {
				deltaX *= -1;
			}
			if (bottomBorder || topBorder) {
				deltaY *= -1;
			}
			if(paddle.getWidth() > 40)
			{
				paddle.setWidth(paddle.getWidth() - (0.1 * paddle.getWidth()));	
			}
			if(bottomBorder&&!(rightBorder||leftBorder)) {         
	             circle.setLayoutY(paddle.getLayoutY()-circle.getRadius()-10);
	         }
		}
	}

	@Override
    public void checkCollisionBottomZone(){
        if(circle.getBoundsInParent().intersects(bottomZone.getBoundsInParent())){
        	timeline.stop();
    		paddleTimeline.stop();
            timer.cancel();
            
            bricks.forEach(brick -> scene.getChildren().remove(brick));
            
            Reset();
        }
    }


	public void checkTimer() {
		if(totalSec < 0)
		{
			timeline.stop();
			bricks.forEach(brick -> scene.getChildren().remove(brick));
			bricks.clear();
			startBtn.setVisible(true);

			paddle.setWidth(paddleStartSize);

			deltaX = -1;
			deltaY = -3;

			
			System.out.println("Game over!");				
		}
	}
	public void convertTime()
	{
		min = TimeUnit.SECONDS.toMinutes(totalSec);
		sec = totalSec - (min*60);
		totalSec--;
		//System.out.println("min: "+format(min)+" sec: "+format(sec));
	}
	private String format(long value)
	{
		if(value < 10)
		{
			return 0+""+value;
		}
		return value+"";
	}

	@Override
	public void Reset() {
        bricks.clear();
		
		//game rule appear
		text1.setVisible(true);
        text2.setVisible(true);
        text3.setVisible(true);
        text4.setVisible(true);
        text5.setVisible(true);
        
        startBtn.setVisible(true);
        menuBtn.setVisible(true);
        pauseBtn.setVisible(false);

		paddle.setWidth(paddleStartSize);

		deltaX = -1;
		deltaY = -3;
		
		circle.setLayoutX(scene.getLayoutBounds().getCenterX());
        circle.setLayoutY(paddle.getLayoutY()-circle.getRadius()-10);
		
	}
}