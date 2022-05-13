package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

//must Override : initialize, startGameButtonAction, Reset
//must turn on for the circle : timeline, checkGameOver, 

public abstract class BBtan implements Initializable{

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
	
    public ArrayList<Rectangle> bricks = new ArrayList<>();
    
    private TranslateTransition translateTransition=new TranslateTransition();
    
	
    public double deltaX = 0;
    public double deltaY = -1;
    
    public double x;
    public double y;
    
    private double width = 560;
    private double height = 200;
    private double intervalOfBricksWidth=25;
    private double intervalOfBricksHeight=50;
    private double widthOfBrick=40;
    private double heightOfBrick=40;
    
    
		
    public Timeline timeline = new Timeline(new KeyFrame(Duration.millis(7), new EventHandler<ActionEvent>() {
       
    	@Override
        public void handle(ActionEvent actionEvent) {
    		circle.setLayoutX(circle.getLayoutX() + deltaX);
            circle.setLayoutY(circle.getLayoutY() + deltaY);
            
            if(!bricks.isEmpty()){            	
                bricks.removeIf(brick -> checkCollisionBrick(brick));  
                
            } else {            	
                timeline.stop();                
            }
            checkCollisionScene(scene);
			checkCollisionBottomZone();
			
    	}    		
    }));
    
    public Timeline checkGameOver=new Timeline(new KeyFrame(Duration.ONE, new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent arg0) {
			        	
            bricks.forEach(brick->checkGameOver(brick)); 
		}    	
    	
    }));
    
    @Override
    public abstract void initialize(URL url, ResourceBundle resourceBundle);
    
    
    @FXML
    public abstract void startGameButtonAction(ActionEvent event);
    
    
    
    public void createBricks(){
        int spaceCheck = 1;
        
        Random random=new Random();

        for (double i = height; i > 0 ; i = i - intervalOfBricksHeight) {
            for (double j = width; j > 0 ; j = j - intervalOfBricksWidth) {
                if(spaceCheck % 2 == 0&&random.nextInt(2)==1){
                    Rectangle rectangle = new Rectangle(j,i,widthOfBrick,heightOfBrick);//x,y,width,height
                    rectangle.setFill(Color.TRANSPARENT);
                    rectangle.setStroke(Color.hsb(random.nextInt(360), 0.6, 1));//hue,saturation,brightness
                    rectangle.setStrokeWidth(5);
                    scene.getChildren().add(rectangle);
                    bricks.add(rectangle);
                }
                spaceCheck++;
            }
        }
        
        System.out.println(width+" "+height);
    } 
      
   

    public boolean checkCollisionBrick(Rectangle brick){

    	if(circle.getBoundsInParent().intersects(brick.getBoundsInParent())){
    		boolean rightBorder = circle.getLayoutX() >= ((brick.getX() + brick.getWidth()+brick.getStrokeWidth()) - circle.getRadius());
            boolean leftBorder = circle.getLayoutX() <= (brick.getX()-brick.getStrokeWidth() + circle.getRadius());
            boolean bottomBorder = circle.getLayoutY() >= ((brick.getY() + brick.getHeight())+brick.getStrokeWidth() - circle.getRadius());
            boolean topBorder = circle.getLayoutY() <= (brick.getY()-brick.getStrokeWidth() + circle.getRadius());

			if (leftBorder || rightBorder) {
                
                deltaX *= -1;
            }
            
			else if (bottomBorder || topBorder) {
                
                deltaY *= -1;
            }
					
			if(Mode.mode.equals(Mode.Simple))
				paddle.setWidth(paddle.getWidth() - (0.10 * paddle.getWidth()));
			
            scene.getChildren().remove(brick);            
            
            makeExplosion(brick.getX()+brick.getWidth()/2,brick.getY()+brick.getHeight()/2);
           
            
            
            return true;
        }    	
    	
        return false;
    }
    
    private void checkGameOver(Rectangle brick) {
    	
    	if(brick.getBoundsInParent().intersects(bottomZone.getBoundsInParent())){
            
    		Reset();            
		}
    }
    
    
    public abstract void Reset() ;
    
    
    private void makeExplosion(double x,double y) {
    	
    	Explosion explosion=new Explosion();
        scene.getChildren().add(explosion.getExplosionGroup());
        explosion.startExplode(x,y);
    }
    
    private void checkCollisionScene(Node node){
        Bounds bounds = node.getLayoutBounds();
       
        boolean rightBorder = circle.getLayoutX() >= (bounds.getMaxX() - circle.getRadius());
        boolean leftBorder = circle.getLayoutX() <= (bounds.getMinX() + circle.getRadius());
        //boolean bottomBorder = circle.getLayoutY() >= (bounds.getMaxY() - circle.getRadius());
        boolean topBorder = circle.getLayoutY() <= (bounds.getMinY() + circle.getRadius());
        

        if (rightBorder || leftBorder) {
            deltaX *= -1;
            
        }
        if(topBorder) {
        	deltaY*=-1;
        }
        
        
    }
    
    
    public void checkCollisionBottomZone(){
        if(circle.getBoundsInParent().intersects(bottomZone.getBoundsInParent())){
            
        	timeline.stop();           
            
            deltaX = 0;
            deltaY = -1;
            
            circle.setLayoutY(circle.getLayoutY()-2);     
            
            fallingBricks();
       }
        
		
		
    }
	   
    private void fallingBricks() {
    	
    	
    	for(int i=0;i<bricks.size();i++) {
    		
    		Rectangle rectangle=bricks.get(i);
    		
    		//rectangle.setLayoutY(rectangle.getLayoutY()+heightOfBrick);
    		rectangle.setY(rectangle.getY()+heightOfBrick);
    	}  	 
        int spaceCheck = 1;
        
        Random random=new Random();

        
        for (double j = width; j > 0 ; j = j -intervalOfBricksWidth) {
            if(spaceCheck % 2 == 0&&random.nextInt(2)==1){
                Rectangle rectangle = new Rectangle(j,intervalOfBricksHeight-10,widthOfBrick,heightOfBrick);//x,y,width,height
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.hsb(random.nextInt(360), 0.6, 1));//hue,saturation,brightness
                rectangle.setStrokeWidth(5);
                scene.getChildren().add(rectangle);
                bricks.add(rectangle);
            }
            spaceCheck++;
        }
    	
    }
	    
}


