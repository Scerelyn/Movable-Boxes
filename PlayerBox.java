package BoxController;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.ivan.xinput.XInputAxes;
import com.ivan.xinput.XInputButtons;
import com.ivan.xinput.XInputComponents;
import com.ivan.xinput.XInputDevice14;

public class PlayerBox {
	public final static double LENGTH = 100, WIDTH = 100, VECTOR_MAX_LENGTH = 100, VECTOR_MAX_MOVE_AMOUNT = 10;
	private Rectangle2D visible,barrel;
	private Point2D barrelEnd;
	private Color color;
	private Line2D dirVect,aimVect;
	private XInputDevice14 controller;
	private double moveVecAng, moveVecMag, aimVecAng; //ang is from 0 to 2pi, mag is 0 to 1
	private boolean isShooting = false;
	
	
	public PlayerBox(double xPos, double yPos, Color c, XInputDevice14 xin){
		this.visible = new Rectangle2D.Double(xPos,yPos,LENGTH,WIDTH);
		this.barrel = new Rectangle2D.Double(0.4*LENGTH+xPos,0.4*WIDTH+yPos,0.2*LENGTH,WIDTH);
		this.color = c;
		this.controller = xin;
		this.moveVecMag = 0;
		this.moveVecAng = 0;
		this.aimVecAng = 0;
	}

	public void changeState() {
		if (controller.poll()) {
			XInputComponents components = controller.getComponents();
			XInputAxes axes = components.getAxes();
			double moveDirAng = Math.atan( axes.ly / axes.lx );
			aimVecAng = Math.atan( axes.ry / axes.rx );
			double castedLY = Math.floor(axes.ly*10.0)/10.0;
			double castedLX = Math.floor(axes.lx*10.0)/10.0;
			//System.out.println(castedLY + " " + castedLX);
			double moveDirMag = Math.sqrt( (castedLY*castedLY) + (castedLX*castedLX) )/Math.sqrt(2);
			
			//transforming into non terrible angles that arctan gives, from 0 to 2pi rather than -pi/2 to pi/2
			if(axes.lx >= 0 && axes.ly >= 0){ //quadrant I
				
			} else if((axes.lx < 0 && axes.ly >= 0) || (axes.lx < 0 && axes.ly < 0)){ //II or III, since the result is the same they are grouped up
				moveDirAng += Math.PI;
			} else if(axes.lx >= 0 && axes.ly < 0){ //IV
				moveDirAng += (2*Math.PI);
			}
			//now adjusting aimvecang
			if(axes.rx >= 0 && axes.ry >= 0){ //quadrant I
				
			} else if((axes.rx < 0 && axes.ry >= 0) || (axes.rx < 0 && axes.ry < 0)){ //II or III
				aimVecAng += Math.PI;
			} else if(axes.rx >= 0 && axes.ry < 0){ //IV
				aimVecAng += (2*Math.PI);
			}
			moveVecAng = moveDirAng;
			moveVecMag = moveDirMag;
			dirVect = new Line2D.Double(visible.getCenterX(), visible.getCenterY(), visible.getCenterX()+moveVecMag*VECTOR_MAX_LENGTH*Math.cos(moveVecAng),visible.getCenterY()-moveVecMag*VECTOR_MAX_LENGTH*Math.sin(moveVecAng));
			aimVect = new Line2D.Double(visible.getCenterX(), visible.getCenterY(), visible.getCenterX()+VECTOR_MAX_LENGTH*Math.cos(aimVecAng),visible.getCenterY()-VECTOR_MAX_LENGTH*Math.sin(aimVecAng));
			barrelEnd = new Point2D.Double(visible.getCenterX()+VECTOR_MAX_LENGTH*Math.cos(aimVecAng),visible.getCenterY()-VECTOR_MAX_LENGTH*Math.sin(aimVecAng));
			
			if(axes.rt > 0){
				isShooting = true;
			} else {
				isShooting = false;
			}
			//System.out.println("Left thumb stick x: " + axes.lx + " y: " + axes.ly + " atan ang: " + Math.toDegrees(ang));
			//System.out.println("Right thumb stick x: " + axes.rx + " y: " + axes.ry);
			//System.out.println("Left trigger: " + axes.lt + " Right trigger: " + axes.rt);
			//System.out.println("Buttons: a:" + buttons.a + " b: " + buttons.b + " x: " + buttons.x + " y: " + buttons.y);
		} else {
			dirVect = new Line2D.Double(visible.getCenterX(), visible.getCenterY(), visible.getCenterX()+moveVecMag*VECTOR_MAX_LENGTH*Math.cos(moveVecAng),visible.getCenterY()-moveVecMag*VECTOR_MAX_LENGTH*Math.sin(moveVecAng));
			aimVect = new Line2D.Double(visible.getCenterX(), visible.getCenterY(), visible.getCenterX()+VECTOR_MAX_LENGTH*Math.cos(aimVecAng),visible.getCenterY()-VECTOR_MAX_LENGTH*Math.sin(aimVecAng));
			moveVecAng = 0;
			moveVecMag = 0;
			System.out.println("Controller disconnected");
		}
	}
	
	public void move(){
		visible = new Rectangle2D.Double(
				visible.getX()+moveVecMag*VECTOR_MAX_MOVE_AMOUNT*Math.cos(moveVecAng),
				visible.getY()-moveVecMag*VECTOR_MAX_MOVE_AMOUNT*Math.sin(moveVecAng),
				visible.getWidth(),visible.getHeight());
		barrel = new Rectangle2D.Double(
				barrel.getX()+moveVecMag*VECTOR_MAX_MOVE_AMOUNT*Math.cos(moveVecAng),
				barrel.getY()-moveVecMag*VECTOR_MAX_MOVE_AMOUNT*Math.sin(moveVecAng),
				barrel.getWidth(),barrel.getHeight());
	}
	
	public void setPos(double x, double y){
		visible = new Rectangle2D.Double(x,y,visible.getWidth(),visible.getHeight());
		barrel = new Rectangle2D.Double(x+0.4*visible.getWidth(),y+0.4*visible.getHeight(),barrel.getWidth(),barrel.getHeight());
		
	}
	
	public Rectangle2D getVisible() {
		return visible;
	}

	public Color getColor() {
		return color;
	}

	public Line2D getDirVect() {
		return dirVect;
	}

	public XInputDevice14 getController() {
		return controller;
	}

	public Line2D getAimVect() {
		return aimVect;
	}

	public Rectangle2D getBarrel() {
		return barrel;
	}

	public double getAimVecAng() {
		return aimVecAng;
	}

	public double getMoveVecAng() {
		return moveVecAng;
	}

	public double getMoveVecMag() {
		return moveVecMag;
	}

	public boolean isShooting() {
		return isShooting;
	}

	public Point2D getBarrelEnd() {
		return barrelEnd;
	}

	public void setMoveVecAng(double moveVecAng) {
		this.moveVecAng = moveVecAng;
	}

	public void setMoveVecMag(double moveVecMag) {
		this.moveVecMag = moveVecMag;
	}
	
}
