package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

//must Override : initialize, startGame, Reset
//must turn on for the circle : timeline, checkGameOver, 

public abstract class BBtan implements Initializable {

	// widgets on scene
	@FXML
	public Button pauseBtn;

	@FXML
	public Button startBtn;

	@FXML
	public Button menuBtn;

	@FXML
	public AnchorPane scene;

	@FXML
	public Circle circle;

	@FXML
	public Rectangle bottomZone;

	@FXML
	public Rectangle paddle;
//<<<<<<< master



	// control the audio
	private AudioManager audioManager = new AudioManager();

	// control the direction of the circle
	public double deltaX = 0;
	public double deltaY = -1;
	
	public double speed=2;

	// bricks
	public ArrayList<Brick> bricks = new ArrayList<>();

	// bombs
	public ArrayList<Bomb> bombs = new ArrayList<>();

	
	private Score score=new Score();
	
	// go back to menu
	private SceneController sceneController = new SceneController();

	// control the interval of the brick
	private double width = 560;
	private double height = 200;
	private double intervalOfBricksWidth = 50;
	private double intervalOfBricksHeight = 50;

  	 
    
    
    private int rateOfBomb=100;
    private int rateOfBrick=2;

	/// public class

	// control the direction of the circle after shooting out
	public Timeline timeline = new Timeline(new KeyFrame(Duration.millis(7), new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {

			circle.setLayoutX(circle.getLayoutX() + deltaX);
			circle.setLayoutY(circle.getLayoutY() + deltaY);

			if (!bricks.isEmpty()) {
				bricks.removeIf(brick -> checkCollisionBrick(brick));

			}else{            	
				
				if(Mode.mode.equals(Mode.Simple)||Mode.mode.equals(Mode.CountDown)) {
					
					try {
						sceneController.switchScene(scene,Mode.Win.getPath());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}				
				
            }
			if (!bombs.isEmpty()) {
				bombs.removeIf(bomb -> checkCollisionBomb(bomb));
			}

			// how about deltaX!=0 and deltaY=0
			// also deltaX=0 and deltaY!=0

			checkCollisionScene(scene);
			checkCollisionBottomZone();

		}
	}));

	// call checkGameOver to know if the game is over
	public Timeline checkGameOver = new Timeline(new KeyFrame(Duration.ONE, new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent arg0) {

			bricks.forEach(brick -> checkGameOver(brick));

		}

	}));

	public void muteButtonAction(ActionEvent event) {

	}
	
	public void pauseButtonAction(ActionEvent event) throws IOException {
		
		timeline.pause();
		scene.setDisable(true);
		
		
		FXMLLoader loader=new FXMLLoader(getClass().getResource(Mode.Pause.getPath()));
		Parent root=loader.load();				
		Scene pause=new Scene(root);
		Stage stage=new Stage();
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setScene(pause);
		stage.setResizable(false);
		stage.setTitle("PAUSED");
		Image iconImage = new Image("file:src/Image/icon-PhotoRoom.png");
		stage.getIcons().add(iconImage);
		stage.show();
		
		PauseController pauseController=loader.getController();		
		pauseController.menuBtn.pressedProperty().addListener((observable, wasPressed, pressed) -> {
	        
	        if (pressed) {
	        	try {
	        		audioManager.playMusic(Music.click);
	        		scene.setDisable(false);
					sceneController.switchScene(scene,Mode.Menu.getPath());
					stage.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        } 
	    });
		pauseController.restartBtn.pressedProperty().addListener((observable, wasPressed, pressed) -> {
	        
	        if (pressed) {
	        	try {
	        		audioManager.playMusic(Music.click);
	        		scene.setDisable(false);
	        		sceneController.switchScene(scene,Mode.mode.getPath());
	    			stage.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        } 
	    });
		pauseController.continueBtn.pressedProperty().addListener((observable, wasPressed, pressed) -> {
	        
	        if (pressed) {
	        	audioManager.playMusic(Music.click);
	        	scene.setDisable(false);
	        	if(Mode.mode.equals(Mode.Simple))timeline.play();
	        	if(Mode.mode.equals(Mode.Endless)||Mode.mode.equals(Mode.FallingBricks)) {
	        		if(circle.getLayoutY()!=bottomZone.getLayoutY()-circle.getRadius()-2)timeline.play();
	        	}
				stage.close();
	        } 
	    });
		
	
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// initialize the timeline

		deltaX = 1;
		deltaY = -1;

		timeline.setCycleCount(Animation.INDEFINITE);

		initialize();

	}

	// initialize other things in derived class
	public abstract void initialize();

	// start the game after user pressing the startBtn
	@FXML
	public void startGameButtonAction(ActionEvent event) {

		score.Reset();
		if (Mode.mode.equals(Mode.FallingBricks)) {
			
			scene.getChildren().add(score);
			ArrangeFallingBricks();
			checkGameOver.play();
			
		} else if(Mode.mode.equals(Mode.Endless)) {
			
			scene.getChildren().add(score);			
			ArrangeEndlessBricks();
			checkGameOver.play();
			
		}else {
			ArrangeSimpleBricks();
		}
		

		startBtn.setVisible(false);
		menuBtn.setVisible(false);
		
		if(!Mode.mode.equals(Mode.CountDown))pauseBtn.setVisible(true);

		startGame();
	}

	public abstract void startGame();

	private void createFallingBricks(double x,double y) {
		
		Random random=new Random();
		Integer BricksHitCount = random.nextInt(1, 10);
		Brick brick=new Brick(BricksHitCount.toString());
		brick.setLayoutX(x);
		brick.setLayoutY(y);
		scene.getChildren().add(brick);
		bricks.add(brick);
	}
	public void ArrangeFallingBricks() {

		Random random = new Random();

		for (double i = height; i > 0; i = i - intervalOfBricksHeight) {
			for (double j = width; j > 0; j = j - intervalOfBricksWidth) {
				if (random.nextInt(2) == 1) {
					createFallingBricks(j, i);
				} else if (random.nextInt(100) == 1) {
					if (Mode.mode != Mode.Simple && Mode.mode != Mode.CountDown)
						createBombs(j, i);
				}

				
			}
		}
		
	}

	private void createBricks(double x, double y) {

		Brick brick = new Brick();
		brick.setLayoutX(x);
		brick.setLayoutY(y);

		scene.getChildren().add(brick);

		bricks.add(brick);
	}

	private void createBombs(double x, double y) {
		Bomb bomb = new Bomb();
		bomb.setLayoutX(x + bomb.getRadius());
		bomb.setLayoutY(y + bomb.getRadius());

		scene.getChildren().add(bomb);

		bombs.add(bomb);
	}

	public void ArrangeEndlessBricks() {
		Random random = new Random();

		for (double i = height; i > 0; i = i - intervalOfBricksHeight) {
			for (double j = width; j > 0; j = j - intervalOfBricksWidth) {
				if (random.nextInt(2) == 1) {
					createBricks(j, i);
				} else if (random.nextInt(100) == 1) {
					if (Mode.mode != Mode.Simple && Mode.mode != Mode.CountDown)
						createBombs(j, i);
				}
			}
		}

	}
	
	public void ArrangeSimpleBricks() {
		Random random = new Random();

		for (double i = height; i > 0; i = i - intervalOfBricksHeight) {
			for (double j = width; j > 0; j = j - intervalOfBricksWidth) {
				if (random.nextInt(2) == 1) {
					createBricks(j, i);
				} 
			}
		}

	}

	// check if the circle collide with the brick
	public boolean checkCollisionBrick(Brick brick) {

		if (circle.getBoundsInParent().intersects(brick.getBoundsInParent())) {
			boolean rightBorder = circle.getLayoutX() >= ((brick.getLayoutX() + brick.getWidth() + brick.getBorderWidth()) - circle.getRadius());
			boolean leftBorder = circle.getLayoutX() <= (brick.getLayoutX() - brick.getBorderWidth() + circle.getRadius());
			boolean bottomBorder = circle.getLayoutY() >= ((brick.getLayoutY() + brick.getHeight()) + brick.getBorderWidth() - circle.getRadius());
			boolean topBorder = circle.getLayoutY() <= (brick.getLayoutY() - brick.getBorderWidth() + circle.getRadius());

			if (leftBorder || rightBorder) {

				deltaX *= -1;
			}

			if (bottomBorder || topBorder) {

				deltaY *= -1;
			}

			audioManager.playMusic(Music.brickDestroy);
			
			if (Mode.mode.equals(Mode.Simple)) {
				paddle.setWidth(paddle.getWidth() - (0.10 * paddle.getWidth()));
			}

			if (Mode.mode.equals(Mode.FallingBricks)) {
				
				Integer count=Integer.parseInt(brick.getText());
							
				count--;
				if(count<=0) {
					scene.getChildren().remove(brick);

					makeExplosion(brick.getLayoutX() + brick.getWidth() / 2, brick.getLayoutY() + brick.getHeight() / 2);
							
					score.plus();					
					return true;
				}
				else {
					brick.setText(count.toString());
				}
				
			}else {
				scene.getChildren().remove(brick);

				makeExplosion(brick.getLayoutX() + brick.getWidth() / 2, brick.getLayoutY() + brick.getHeight() / 2);

				score.plus();
				return true;
			}
				
			
		}

		return false;
	}

	public boolean checkCollisionBomb(Bomb bomb) {

		if (circle.getBoundsInParent().intersects(bomb.getBoundsInParent())) {

			for (int i = 0; i < bricks.size(); i++) {
				if (bricks.get(i).getLayoutY() == bomb.getLayoutY() - bomb.getRadius()) {
					scene.getChildren().remove(bricks.get(i));
					score.plus();
				}

			}

			bricks.removeIf(brick -> brick.getLayoutY() == bomb.getLayoutY() - bomb.getRadius());
			makeRowFlash(bomb.getLayoutY() - bomb.getRadius(), (Color) bomb.getStroke());
			scene.getChildren().remove(bomb);

			return true;
		}

		return false;
	}

	private void makeRowFlash(double y, Color color) {

		Flash flash = new Flash();
		scene.getChildren().add(flash);
		flash.setSize(scene.getWidth(), 20, color);
		flash.makeFlash(y);

	}

	// reset the game after game over in derived class
	public abstract void Reset();

	/// private class

	// go back to menu
	@FXML
	private void goMenu(ActionEvent event) throws IOException {

		audioManager.playMusic(Music.click);
		sceneController.switchScene(event, Mode.Menu.getPath());
	}

	// check if the bricks collide with the bottomZone
	private void checkGameOver(Brick brick){	

		if(brick.getLayoutY()>=bottomZone.getLayoutY()-brick.getHeight()-10) {
    		
    		FXMLLoader loader=new FXMLLoader(getClass().getResource(Mode.Gameover.getPath()));
			try {
				Parent root = loader.load();
				Scene pause=new Scene(root);
				Stage stage=new Stage();
				Image iconImage = new Image("file:src/Image/icon-PhotoRoom.png");
				stage.getIcons().add(iconImage);
				stage.setResizable(false);
				stage.setTitle("BRICK BREAKER");
				stage.setScene(pause);
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}				
			
			GameoverController gameoverController=loader.getController();
			gameoverController.setScore(score.getScore());			
			Stage stage = (Stage)scene.getScene().getWindow();
			stage.close();
    		Reset();
    	}
    }
    
    //Once the circle collide with the brick, distribute fragments
    private void makeExplosion(double x,double y){
    	
    	Explosion explosion=new Explosion();
        scene.getChildren().add(explosion.getExplosionGroup());
        explosion.startExplode(x,y);
    }
    
    
    //check if the circle collide with the bounds of the scene
    private void checkCollisionScene(Node node){
        Bounds bounds = node.getLayoutBounds();
                   
    	boolean rightBorder = circle.getLayoutX() >= (bounds.getMaxX() - circle.getRadius());
        boolean leftBorder = circle.getLayoutX() <= (bounds.getMinX() + circle.getRadius());
        boolean topBorder = circle.getLayoutY() <= (bounds.getMinY() + circle.getRadius());
        

        if (rightBorder || leftBorder) {
            deltaX *= -1;
            
        }
        if(topBorder) {
        	deltaY*=-1;
        } 

        
    }
    
    //check if the circle collide with the bottomZone
    public void checkCollisionBottomZone(){
        
    	
    	if(circle.getBoundsInParent().intersects(bottomZone.getBoundsInParent())){
            
        	timeline.stop();           
            
            deltaX = 0;
            deltaY = -1;
            
            circle.setLayoutY(bottomZone.getLayoutY()-circle.getRadius()-2);  
            
            if(Mode.mode.equals(Mode.FallingBricks)) {
            	FallingBricksDown();
            	
            }else {
            	bricksBombsDown();
            }
            
       }
        
        
        
		
    }
	   
    //after circle go back to bottomZone, bricks fall down
    private void bricksBombsDown() {
    	
    	
    	for(int i=0;i<bricks.size();i++) {
    		
    		Brick brick=bricks.get(i);
    		brick.setLayoutY(brick.getLayoutY()+intervalOfBricksHeight);
    	}  
    	for(int i=0;i<bombs.size();i++) {
    		
    		Bomb bomb=bombs.get(i);
    		bomb.setLayoutY(bomb.getLayoutY()+intervalOfBricksHeight);
    	}
        Random random=new Random();

        
        double i=50;
        for (double j = width; j > 0 ; j = j - intervalOfBricksWidth) {
            if(random.nextInt(rateOfBrick)==1){
                createBricks(j,i);
            }
            else if(random.nextInt(rateOfBomb)==1){
            	createBombs(j,i);
            }
            
        }
       
    	
    }



	public void FallingBricksDown() {
		for (int i = 0; i < bricks.size(); i++) {

			Brick brick = bricks.get(i);
			brick.setLayoutY(brick.getLayoutY()+intervalOfBricksHeight);
		}
		for(int i=0;i<bombs.size();i++) {
    		
    		Bomb bomb=bombs.get(i);
    		bomb.setLayoutY(bomb.getLayoutY()+intervalOfBricksHeight);
    	}

		Random random = new Random();
		double i=50;
        for (double j = width; j > 0 ; j = j - intervalOfBricksWidth) {
            if(random.nextInt(rateOfBrick)==1){
                createFallingBricks(j,i);
            }
            else if(random.nextInt(rateOfBomb)==1){
            	createBombs(j,i);
            }
            
        }
	}
}
