package BoxController.Enemies;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import BoxController.Projectile;

public class BasicEnemy extends Enemy{

	public BasicEnemy(double xPos, double yPos) {
		super(xPos, yPos);
		this.rotationAffectedShapes[0] = new int[1];
		this.rotationAffectedShapes[0][0] = 2;
		this.visibleColors = new Color[3];
		this.visibleColorsOrig = new Color[3];
		build();
		this.reseter = new ShootReseter();
		fireLimiter.schedule(this.reseter,0,1000);
	}

	@Override
	public void build() { //magic numbers for testing reasons
		xRotCenter = xPos + 15;
		yRotCenter = yPos + 15;
		this.visibleParts = new Shape[3];
		this.visibleParts[0] = new Rectangle2D.Double(xPos,yPos,30,30);
		mainColor = Color.ORANGE.darker().darker();
		this.visibleColors[0] = mainColor;
		this.visibleColorsOrig[0] = Color.ORANGE.darker().darker();
		this.visibleParts[1] = new Ellipse2D.Double(xPos+5,yPos+5,20,20);
		this.visibleColors[1] = Color.GRAY;
		this.visibleColorsOrig[1] = Color.GRAY;
		this.visibleParts[2] = new Rectangle2D.Double(xPos+10,yPos+10,40,10);
		this.visibleColors[2] = Color.BLACK;
		this.visibleColorsOrig[2] = Color.BLACK;
		this.hitbox = new Rectangle2D.Double(xPos,yPos,30,30);
	}

	@Override
	public void update() {
		double y = this.getVisibleParts()[0].getBounds().getCenterY()-this.currentTarget.getVisible().getCenterY();
		double x = this.getVisibleParts()[0].getBounds().getCenterX()-this.currentTarget.getVisible().getCenterX();
		rotAng1 = Math.atan((y)/(x));
		rotAng1 += Math.PI; //the drawn rotation is backwards for some reason
		if(x >= 0 && y >= 0){ //quadrant I
			
		} else if((x < 0 && y >= 0) || (x < 0 && y < 0)){ //II or III
			rotAng1 += Math.PI;
		} else if(x >= 0 && y < 0){ //IV
			rotAng1 += (2*Math.PI);
		}
		if(canShoot){
			this.visibleColors[2] = Color.YELLOW;
		} else {
			this.visibleColors[2] = Color.BLACK;
		}
		
	}
	@Override
	public Projectile shoot(){
		canShoot = false;
		return new Projectile(
				this.visibleParts[0].getBounds().getCenterX()-5, 
				this.visibleParts[0].getBounds().getCenterY()-5,
				10,
				10,
				1,
				-rotAng1, //angles sure are hard too keep track of, good thing transforming is ezpz
				5,
				Color.YELLOW,
				false
				);
	}
}
