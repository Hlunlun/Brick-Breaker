package application;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

//1. instantiate the class Explosion
//2. add the fragment group to the scene
//3. use method startExplode 

public class Explosion{
	
	private static final int CNT=100;
	private ArrayList<Rectangle>fragments=new ArrayList<>();
	private final double[]ang=new double[CNT];
	private final long[] start=new long[CNT];
	private final Random rnd=new Random();
	private final Group group=new Group();
	
	private double posX;
	private double posY;
	
	private double opacity=1;
	
	private boolean stopExplosion=false;	
	
    private void createFragments() {

		for(int i=0;i<CNT;i++) {
			Rectangle rectangle=new Rectangle(5,5,Color.hsb(new Random().nextInt(360), 1, 1));
			group.getChildren().add(rectangle);
			fragments.add(rectangle);
			//angle
			ang[i]=3.0*Math.PI*rnd.nextDouble();
			//start
			start[i]=rnd.nextInt(2000000000);
		}
    }
    
    public Group getExplosionGroup() {
    	
    	return group;
    }
	
    AnimationTimer explode=new AnimationTimer() {

		@Override
		public void handle(long now) {
			//width
			final double w=posX;
			//height
			final double h=posY;
			//radius
			final double r=80;//Math.sqrt(2)*Math.max(w, h);
			
			//loop
			for(int i=0;i<CNT;i++) {
				//node
				final Node nd=fragments.get(i);
				//angle
				final double ag=ang[i];
				final long tm=(now-start[i])%2000000000;
				final double dt=tm*r/2000000000.0;
				
				nd.setTranslateX(Math.cos(ag)*dt+w);
				nd.setTranslateY(Math.sin(ag)*dt+h);
			}
		}
	};
	
	public void Explode() {
		
		Reset();
		
		opacity=1;
		stopExplosion=false;
		
		createFragments();	
		
		explode.start();
		
		new AnimationTimer() {			

			@Override			
			public void handle(long arg0) {
				
				doHandle();
				
			}
			
			private void doHandle() {
				
				for(int i=0;i<CNT;i++) {
					Node nd=fragments.get(i);
					opacity-=0.0003;
					nd.opacityProperty().set(opacity);
					
					if(opacity<=0) {
						
						explode.stop();
						stopExplosion=true;
					}
					if(stopExplosion) {
						stop();
						Reset();
						break;
					}
					
				}
				
			}
			
		}.start();	
		
	}
	
	public void startExplode(double LayoutX,double LayoutY) {		
		
		posX=LayoutX;
		posY=LayoutY;
		
		Explode();
				
	}
	
	private void Reset() {
		
	    fragments.clear();
		group.getChildren().clear();
		
	}

}
