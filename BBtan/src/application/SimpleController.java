package application;



import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class SimpleController extends BBtan{
			
	private AudioManager audioManager = new AudioManager();
	
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
	

	//the length of the paddle
    private int paddleStartSize = 600;

    //to listen to the position of the mouse
    Robot robot = new Robot();

    //1 Frame every 10 millisecond, which means 100 FPS
    Timeline paddleTimeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            
        	movePaddle();

            checkCollisionPaddle(paddle);
                        
            checkCollisionBottomZone();
        }
    }));


    
    //initialize the timeline, paddleTimeline, mode:Simple
    @Override
	public void initialize() {
		Mode.mode=Mode.Simple;
    	
    	paddle.setWidth(paddleStartSize);
        
        paddleTimeline.setCycleCount(Animation.INDEFINITE);
        
		pauseBtn.setVisible(false);
	}

    //set the scene of the game and called in startGameButtonAction
     @Override
     public void startGame(){
    	
		audioManager.playMusic(Music.startgame);
     	text1.setVisible(false);
     	text2.setVisible(false);
     	text3.setVisible(false);
     	text4.setVisible(false);
     	text5.setVisible(false);        

     	timeline.play();
        paddleTimeline.play();
     }

    //keep track of the mouse and move the paddle
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

    //check if the circle collide with the paddle
    private void checkCollisionPaddle(Rectangle paddle){

        if(circle.getBoundsInParent().intersects(paddle.getBoundsInParent())){

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
        }
    }
    
    //check if the circle collide with the bottomZone
    @Override
    public void checkCollisionBottomZone(){
        if(circle.getBoundsInParent().intersects(bottomZone.getBoundsInParent())&&circle.getLayoutY()>paddle.getLayoutY()){
            timeline.stop();
            
            //brick is element in bricks
            //->add the code that you want to execute during iterate the array bricks 
            bricks.forEach(brick -> scene.getChildren().remove(brick));
            
            Reset();
        }
    }
  
    //after game over, reset the game
	@Override
	public void Reset() {
		
		bricks.clear();
		
        startBtn.setVisible(true);
        menuBtn.setVisible(true);
        pauseBtn.setVisible(false);
        text1.setVisible(true);
        text2.setVisible(true);
        text3.setVisible(true);
        text4.setVisible(true);
        text5.setVisible(true);
        
        paddle.setWidth(paddleStartSize);

        deltaX = -1;
        deltaY = -3;

        circle.setLayoutX(scene.getBoundsInLocal().getCenterX());
        circle.setLayoutY(paddle.getLayoutY()-circle.getRadius());

        System.out.println("Game over!");
	}

   

}


//timeline
//https://vimsky.com/zh-tw/examples/detail/java-class-javafx.animation.Timeline.html







