package application;



import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class SimpleController extends BBtan{
	
    private int paddleStartSize = 600;

    Robot robot = new Robot();

    //1 Frame evey 10 millis, which means 100 FPS
    Timeline paddleTimeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            
        	movePaddle();

            checkCollisionPaddle(paddle);
                        
            checkCollisionBottomZone();
        }
    }));


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	deltaX = 1;
        deltaY = -1;
        
        Mode.mode=Mode.Simple;
    	
    	paddle.setWidth(paddleStartSize);
        timeline.setCycleCount(Animation.INDEFINITE);
        paddleTimeline.setCycleCount(Animation.INDEFINITE);
    }

    @Override
    @FXML
    public void startGameButtonAction(ActionEvent event) {
        
        startGame();
    }

    public void startGame(){
    	startBtn.setVisible(false);
    	
        createBricks();
        timeline.play();
        paddleTimeline.play();
    }



    private void movePaddle(){
        Bounds bounds = scene.localToScreen(scene.getBoundsInLocal());
        double sceneXPos = bounds.getMinX();

        double xPos = robot.getMouseX();
        double paddleWidth = paddle.getWidth();

        if(xPos >= sceneXPos + (paddleWidth/2) && xPos <= (sceneXPos + scene.getWidth()) - (paddleWidth/2)){
            paddle.setLayoutX(xPos - sceneXPos - (paddleWidth/2));
        } else if (xPos < sceneXPos + (paddleWidth/2)){
            paddle.setLayoutX(0);
        } else if (xPos > (sceneXPos + scene.getWidth()) - (paddleWidth/2)){
            paddle.setLayoutX(scene.getWidth() - paddleWidth);
        }
    }

    private void checkCollisionPaddle(Rectangle paddle){

        if(circle.getBoundsInParent().intersects(paddle.getBoundsInParent())){

        	boolean rightBorder = circle.getLayoutX() - circle.getRadius() <= ((paddle.getX() + paddle.getWidth()));
            boolean leftBorder = (circle.getLayoutX()+ circle.getRadius()) >= paddle.getX();
            boolean bottomBorder = (circle.getLayoutY()- circle.getRadius()) <= (paddle.getY() + paddle.getHeight());
            boolean topBorder = (circle.getLayoutY()+ circle.getRadius()) >= paddle.getY() ;

            if (rightBorder || leftBorder) {
                deltaX *= -1;
            }
            if (bottomBorder || topBorder) {
                deltaY *= -1;
            }
        }
    }
    
    
    
    public void checkCollisionBottomZone(){
        if(circle.getBoundsInParent().intersects(bottomZone.getBoundsInParent())&&circle.getLayoutY()>paddle.getLayoutY()+paddle.getHeight()){
            timeline.stop();
            
            //brick is element in bricks
            //->add the code that you want to execute during iterate the array bricks 
            bricks.forEach(brick -> scene.getChildren().remove(brick));
            
            Reset();
        }
    }

	@Override
	public void Reset() {
		
		bricks.clear();
        startBtn.setVisible(true);
        paddle.setWidth(paddleStartSize);

        deltaX = -1;
        deltaY = -3;

        circle.setLayoutX(300);
        circle.setLayoutY(300);

        System.out.println("Game over!");
	}
    
    
}


//timeline
//https://vimsky.com/zh-tw/examples/detail/java-class-javafx.animation.Timeline.html







