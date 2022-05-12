package application;



import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class SigntLineController implements Initializable{
	
	@FXML
	private Button startBtn;
	
	@FXML
	private Button menuBtn;
	
	@FXML
	private AnchorPane scene;
	
	@FXML
	private Circle circle;
	
	@FXML
	private Rectangle topZone;
	
	@FXML
	private Rectangle buttomZone;
	
	private Polyline polyline = new Polyline();
	
    private ArrayList<Rectangle> bricks = new ArrayList<>();
    
    private TranslateTransition translateTransition=new TranslateTransition();
    
    private SceneController sceneController=new SceneController();
	
    private double deltaX = 0;
    private double deltaY = -1;
    
    private double x;
    private double y;

    EventHandler<MouseEvent>eventHandler=new EventHandler<MouseEvent>(){
    	@Override
    	public void handle(MouseEvent event) {
    	 	   Bounds bounds = scene.getBoundsInLocal();
    			
    	 		double circleX=circle.getLayoutX();
    	 	   	double circleY=circle.getLayoutY();
    	 	   	
    	 	   	double mouseX=event.getSceneX();
    	 	   	double mouseY=event.getSceneY();
    	 	
    	 	   	boolean vertical=mouseX==circleX;
    	 	   	
    	 	   	///line equation
    	 	   	double slope1=vertical?-1:(circleY-mouseY)/(circleX-mouseX);
    	 	   	double var1=circleY-slope1*circleX;
    	 	   	    	
    	 	   	///inverse line
    	 	   	double slope2=slope1*-1;
    	 	   	double var2=var1*-1;
    	 	   	
    	 	   	double scale=1.2;
    	 	   	
    	 	   	deltaX=vertical?0:1*scale;
    	 		deltaY=slope1*scale;
    	 	   	
    	 	   	
    	 	   	if(mouseX<circleX&&mouseY<circleY) {
    	 	   		deltaX*=-1;
    	 	   		deltaY*=-1;
    	 	   	}
    	 	   	
    	 	   	if(mouseX>circleX&&mouseY>circleY) {
    		   		deltaX*=-1;
    		   		deltaY*=-1;
    		   	}		
    	    	
    	 	   x=circle.getLayoutX();
    	 	   y=circle.getLayoutY()-circle.getRadius();
    	 	   
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
    	    		timeline.play();  		
    	    		drawLine.stop();
    	    		polyline.getPoints().clear();
    	    		scene.getChildren().removeAll(polyline);
    	    		
    	    	}
    	    	
    			
    		}
    };
		
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
            
            checkCollisionScene(scene);
            
            scene.getChildren().add(polyline);
            
            
        }
    }));
    
	
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(7), new EventHandler<ActionEvent>() {
            	
    	
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
            //checkCollisionTopZone();
            checkCollisionButtomZone();
            
        }
    }));
    
    /////circle's Layout!!!!!!!!!!
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	
    	    	        
        timeline.setCycleCount(Animation.INDEFINITE);
        drawLine.setCycleCount(Animation.INDEFINITE);   
                
        translateTransition.setNode(circle);
        translateTransition.setDuration(Duration.millis(300));
        translateTransition.setFromY(scene.getLayoutBounds().getMaxY());
        translateTransition.setToY(scene.getLayoutBounds().getMaxY()-50);;
        translateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
        
    }

	@FXML
	private void goMenu(ActionEvent event) throws IOException {
		
		sceneController.switchScene(event, "Menu.fxml");
	}
    
    
    @FXML
    private void startGameButtonAction(ActionEvent event) {
        startBtn.setVisible(false);
        menuBtn.setVisible(false);
        startGame();
        
        //circle.setLayoutX(scene.getLayoutBounds().getCenterX());
    	//circle.setLayoutY(scene.getLayoutBounds().getMaxY()-circle.getRadius());
    	
    	
        
    }

    private void startGame(){
    	
        createBricks();  
        drawLine.stop();
        translateTransition.stop();
               
        
        scene.addEventFilter(MouseEvent.MOUSE_DRAGGED,eventHandler);
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED,eventHandler);
        scene.addEventFilter(MouseEvent.MOUSE_RELEASED,eventHandler);
        
        
        System.out.println(scene.getBoundsInLocal().getHeight());
        System.out.println(circle.getLayoutX()+" "+circle.getLayoutY());

    }
    
    private void createBricks(){
        double width = 560;
        double height = 200;

        int spaceCheck = 1;
        
        Random random=new Random();

        for (double i = height; i > 0 ; i = i - 50) {
            for (double j = width; j > 0 ; j = j - 25) {
                if(spaceCheck % 2 == 0&&random.nextInt(2)==1){
                    Rectangle rectangle = new Rectangle(j,i,40,40);//x,y,width,height
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
    
    private void fallingBricks() {
    	
    	
    	for(int i=0;i<bricks.size();i++) {
    		
    		Rectangle rectangle=bricks.get(i);
    		
    		rectangle.setLayoutY(rectangle.getLayoutY()+50);
    	}  	
    	
    	
    	double width = 560;
        double height = 40;

        int spaceCheck = 1;
        
        Random random=new Random();

        
        for (double j = width; j > 0 ; j = j - 25) {
            if(spaceCheck % 2 == 0&&random.nextInt(2)==1){
                Rectangle rectangle = new Rectangle(j,height,40,40);//x,y,width,height
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.hsb(random.nextInt(360), 0.6, 1));//hue,saturation,brightness
                rectangle.setStrokeWidth(5);
                scene.getChildren().add(rectangle);
                bricks.add(rectangle);
            }
            spaceCheck++;
        }
        
    	
    	
    	
    }
    

   

    private boolean checkCollisionBrick(Rectangle brick){

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

            scene.getChildren().remove(brick);            
            
            makeExplosion(brick.getX()+brick.getWidth()/2,brick.getY()+brick.getHeight()/2);
           
            
            
            return true;
        }
        return false;
    }
    
    private void makeExplosion(double x,double y) {
    	
    	Explosion explosion=new Explosion();
        scene.getChildren().add(explosion.getExplosionGroup());
        explosion.startExplode(x,y);
    }
    
    private void checkCollisionScene(Node node){
        Bounds bounds = node.getLayoutBounds();
       
        boolean rightBorder = circle.getLayoutX() >= (bounds.getMaxX() - circle.getRadius());
        boolean leftBorder = circle.getLayoutX() <= (bounds.getMinX() + circle.getRadius());
        boolean bottomBorder = circle.getLayoutY() >= (bounds.getMaxY() - circle.getRadius());
        boolean topBorder = circle.getLayoutY() <= (bounds.getMinY() + circle.getRadius());
        

        if (rightBorder || leftBorder) {
            deltaX *= -1;
            
        }
        if(topBorder) {
        	deltaY*=-1;
        }
        
        
    }
    
    private void checkCollisionTopZone(){
        if(circle.getBoundsInParent().intersects(topZone.getBoundsInParent())){
            timeline.stop();    
            translateTransition.play();

 
            //brick is element in bricks
            //->add the code that you want to execute during iterate the array bricks 
            bricks.forEach(brick -> scene.getChildren().remove(brick));
            
            bricks.clear();
            scene.getChildren().clear();
            
            startBtn.setVisible(true);    
            menuBtn.setVisible(true);

            deltaX = 0;
            deltaY = -1;

            circle.setLayoutX(scene.getBoundsInLocal().getMaxX()/2);
            circle.setLayoutY(scene.getLayoutBounds().getMaxY()-circle.getRadius());           
            

            System.out.println("Game over!");
        }
    }

    private void checkCollisionButtomZone(){
        if(circle.getBoundsInParent().intersects(buttomZone.getBoundsInParent())){
            timeline.stop();           
            

            deltaX = 0;
            deltaY = -1;
            
            circle.setLayoutY(circle.getLayoutY()-2);
            fallingBricks();

       }
    }
	   
    
    
    	
	
	
    
    public void handle(KeyEvent event) {		
		if (KeyEvent.KEY_PRESSED.equals(event.getEventType())) {				
			if(startBtn.isVisible()) {
				startGame();
			}
        } 
	}


    
}
