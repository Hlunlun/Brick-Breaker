package application;

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

public class Score extends Label {
	
	private double borderWidth=5;
	private long score=0L;
	
	private double posX=500;
	private double posY=0;

	public Score() {
		BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,  CornerRadii.EMPTY,
                new BorderWidths(borderWidth));
        Border border = new Border(borderStroke);
		setBorder(border);		
		
		setPrefHeight(40);
		setPrefWidth(100);
		
		setLayoutX(posX);
		setLayoutY(posY);
		
		setStyle("-fx-background-color: transparent");
		
		Font font = Font.font("Impact", FontWeight.NORMAL, FontPosture.REGULAR, 20);
		setFont(font);
		setText("score: "+String.valueOf(score));
		setAlignment(Pos.CENTER);
		setTextFill(Color.BLACK);
	}
	
	public void Reset() {
		score=0;
		setText("score: "+String.valueOf(score));
	}
	
	public void setStroke(Color color) {
		BorderStroke borderStroke = new BorderStroke(color, BorderStrokeStyle.SOLID,  CornerRadii.EMPTY,
                new BorderWidths(borderWidth));
        Border border = new Border(borderStroke);
		setBorder(border);
	}
	
	public void plus() {
		
		score++;		
		setText("score: "+String.valueOf(score));
	}
	
	public String getScore() {
		return String.valueOf(score);
	}
}
