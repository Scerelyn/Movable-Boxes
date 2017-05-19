package BoxController;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Projectile {
	private Ellipse2D visible;
	private int damage;
	private double ang,speed;
	private Color pColor;
	private boolean playerFired;
	public Projectile(double x, double y, double width, double height, int damage, double angle, double speed, Color c, boolean playerFired){
		this.visible = new Ellipse2D.Double(x,y,width,height);
		this.damage = damage;
		this.ang = angle;
		this.speed = speed;
		this.pColor = c;
		this.playerFired = playerFired;
	}
	
	public void setPos(double x, double y){
		visible = new Ellipse2D.Double(x,y,visible.getWidth(),visible.getHeight());
	}
	
	public void move(){
		visible = new Ellipse2D.Double(
				visible.getX()+speed*Math.cos(ang),
				visible.getY()-speed*Math.sin(ang),
				visible.getWidth(),
				visible.getHeight());
	}

	public Ellipse2D getVisible() {
		return visible;
	}

	public int getDamage() {
		return damage;
	}

	public double getAng() {
		return ang;
	}

	public double getSpeed() {
		return speed;
	}

	public void setAng(double ang) {
		this.ang = ang;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public Color getpColor() {
		return pColor;
	}

	public boolean isPlayerFired() {
		return playerFired;
	}
	
	
}
