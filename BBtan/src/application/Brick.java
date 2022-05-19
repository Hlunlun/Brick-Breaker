package application;

import java.util.Random;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Brick extends Label{

	
	private Random random=new Random();
	private double borderWidth=5;
	public Brick() {
		
		//reference
		//https://www.demo2s.com/java/javafx-vbox-setborder-border-value.html
		BorderStroke borderStroke = new BorderStroke(Color.hsb(random.nextInt(360), 1, 1), BorderStrokeStyle.SOLID,  CornerRadii.EMPTY,
                new BorderWidths(borderWidth));
        Border border = new Border(borderStroke);
		setBorder(border);
		
		setPrefHeight(40);
		setPrefWidth(40);
		
		setStyle("-fx-background-color: 0");
        
	}
	
	public Brick(String text) {
		
		BorderStroke borderStroke = new BorderStroke(Color.hsb(random.nextInt(360), 1, 1), BorderStrokeStyle.SOLID,  CornerRadii.EMPTY,
                new BorderWidths(borderWidth));
        Border border = new Border(borderStroke);
		setBorder(border);
		
		
		setPrefHeight(40);
		setPrefWidth(40);
		
		setStyle("-fx-background-color: 0");
		
		Font font = Font.font("Impact", FontWeight.NORMAL, FontPosture.REGULAR, 20);
		setFont(font);
		setText(text);
		setAlignment(Pos.CENTER);
		setTextFill(Color.GRAY);
	}
	
	public double getBorderWidth() {
		return borderWidth;
	}
}
