package application;

import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Brick extends Rectangle{

	private Random random=new Random();
	public Brick() {
		setHeight(40);
		setWidth(40);
        setFill(Color.TRANSPARENT);
        setStroke(Color.hsb(random.nextInt(360), 1, 1));//hue,saturation,brightness
        setStrokeWidth(5);
	}
}
