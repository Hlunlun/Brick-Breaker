package application;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.LoadListener;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Mode4Controller implements Initializable {

	@FXML
	private AnchorPane scene;

	@FXML
	private Circle circle;

	@FXML
	private Rectangle paddle;

	@FXML
	private Rectangle bottomZone;

	@FXML
	private Button startButton;

	@FXML
	private Rectangle timerBar;
	
	@FXML
	private Label Showtime;

	private int paddleStartSize = 160;

	Robot robot = new Robot();

	Timer timer = new Timer();
	static long min, sec, totalSec;

	private ArrayList<Rectangle> bricks = new ArrayList<>();

	double deltaX = -1;
	double deltaY = -3;

	// 1 Frame evey 10 millis, which means 100 FPS
	Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent actionEvent) {
			movePaddle();

			checkCollisionPaddle(paddle);
			circle.setLayoutX(circle.getLayoutX() + deltaX);
			circle.setLayoutY(circle.getLayoutY() + deltaY);

			if (!bricks.isEmpty()) {
				bricks.removeIf(brick -> checkCollisionBrick(brick));
			} else {
				timeline.stop();
			}

			checkCollisionScene(scene);
			checkCollisionBottomZone();
			checkTimer();
			Showtime.setText(format(min)+":"+format(sec));
		}
	}));

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		paddle.setWidth(paddleStartSize);
		timeline.setCycleCount(Animation.INDEFINITE);
		Showtime.setText("03:00");
	}

	@FXML
	void startGameButtonAction(ActionEvent event) {
		startButton.setVisible(false);
		startGame();
		Showtime.setText("03:00");
	}

	public void startGame() {
		createBricks();
		timeline.play();
		totalSec = 180;
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

	public void checkCollisionScene(Node node) {
		Bounds bounds = node.getBoundsInLocal();
		boolean rightBorder = circle.getLayoutX() >= (bounds.getMaxX() - circle.getRadius());
		boolean leftBorder = circle.getLayoutX() <= (bounds.getMinX() + circle.getRadius());
		boolean bottomBorder = circle.getLayoutY() >= (bounds.getMaxY() - circle.getRadius());
		boolean topBorder = circle.getLayoutY() <= (bounds.getMinY()+35 + circle.getRadius());

		if (rightBorder || leftBorder) {
			deltaX *= -1;
		}
		if (bottomBorder || topBorder) {
			deltaY *= -1;
		}
	}

	public boolean checkCollisionBrick(Rectangle brick) {

		if (circle.getBoundsInParent().intersects(brick.getBoundsInParent())) {
			boolean rightBorder = circle.getLayoutX() >= ((brick.getX() + brick.getWidth()) - circle.getRadius());
			boolean leftBorder = circle.getLayoutX() <= (brick.getX() + circle.getRadius());
			boolean bottomBorder = circle.getLayoutY() >= ((brick.getY() + brick.getHeight()) - circle.getRadius());
			boolean topBorder = circle.getLayoutY() <= (brick.getY() + circle.getRadius());

			if (rightBorder || leftBorder) {
				deltaX *= -1;
			}
			if (bottomBorder || topBorder) {
				deltaY *= -1;
			}

			/*if(paddle.getWidth() > 40)
			{
				paddle.setWidth(paddle.getWidth() - (0.1 * paddle.getWidth()));	
			}*/
			scene.getChildren().remove(brick);

			return true;
		}
		return false;
	}

	public void createBricks() {
		double width = 560;
		double height = 160;

		int spaceCheck = 1;

		Random random = new Random();

		for (double i = height; i > 50; i = i - 50) {
			for (double j = width; j > 0; j = j - 25) {
				if (spaceCheck % 2 == 0) {
					Rectangle rectangle = new Rectangle(j, i, 40, 40);// x,y,width,height
					rectangle.setFill(Color.TRANSPARENT);
					rectangle.setStroke(Color.hsb(random.nextInt(360), 0.6, 1));// hue,saturation,brightness
					rectangle.setStrokeWidth(5);
					scene.getChildren().add(rectangle);
					bricks.add(rectangle);
				}
				spaceCheck++;
			}
		}
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

			boolean rightBorder = circle
					.getLayoutX() >= ((paddle.getLayoutX() + paddle.getWidth()) - circle.getRadius());
			boolean leftBorder = circle.getLayoutX() <= (paddle.getLayoutX() + circle.getRadius());
			boolean bottomBorder = circle
					.getLayoutY() >= ((paddle.getLayoutY() + paddle.getHeight()) - circle.getRadius());
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
		}
	}

	public void checkCollisionBottomZone() {
		if (circle.getBoundsInParent().intersects(bottomZone.getBoundsInParent())) {
			timeline.stop();
			timer.cancel();
			bricks.forEach(brick -> scene.getChildren().remove(brick));
			bricks.clear();
			startButton.setVisible(true);

			paddle.setWidth(paddleStartSize);

			deltaX = -1;
			deltaY = -3;

			circle.setLayoutX(300);
			circle.setLayoutY(300);

			System.out.println("Game over!");
		}
	}

	/*TimerTask timertask = new TimerTask() {
		@Override
		public void run() {
			//System.out.println("After 1 sec");
			convertTime();
			if(totalSec < 0)
			{
				timer.cancel();
			}
		}
	};*/
	public void checkTimer() {
		if(totalSec < 0)
		{
			timeline.stop();
			bricks.forEach(brick -> scene.getChildren().remove(brick));
			bricks.clear();
			startButton.setVisible(true);

			paddle.setWidth(paddleStartSize);

			deltaX = -1;
			deltaY = -3;

			circle.setLayoutX(300);
			circle.setLayoutY(300);
			System.out.println("Game over!");				
		}
	}
	public static void convertTime()
	{
		min = TimeUnit.SECONDS.toMinutes(totalSec);
		sec = totalSec - (min*60);
		totalSec--;
		//System.out.println("min: "+format(min)+" sec: "+format(sec));
	}
	private static String format(long value)
	{
		if(value < 10)
		{
			return 0+""+value;
		}
		return value+"";
	}
}