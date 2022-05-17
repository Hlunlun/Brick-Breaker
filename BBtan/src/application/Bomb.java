package application;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Bomb extends Circle{
	
	
	private Random random=new Random();
	
	
	
	public Bomb() {	
		
		//bomb picture
		//https://www.flaticon.com/search/2?word=bomb
		Image rowBomb =new Image("https://cdn-icons-png.flaticon.com/128/2716/2716593.png");
		
		setRadius(20);
		setStroke(Color.hsb(random.nextInt(360), 0.8, 1));
		setStrokeWidth(5);
		setFill(new ImagePattern(rowBomb));
		
        //setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
	
	}
	
}
