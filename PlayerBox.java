package BoxController;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Timer;
import java.util.TimerTask;

import com.ivan.xinput.XInputAxes;
import com.ivan.xinput.XInputButtons;
import com.ivan.xinput.XInputButtonsDelta;
import com.ivan.xinput.XInputComponents;
import com.ivan.xinput.XInputComponentsDelta;
import com.ivan.xinput.XInputDevice14;
import com.ivan.xinput.enums.XInputButton;

public class PlayerBox {
	public final static double LENGTH = 80, WIDTH = 40, VECTOR_MAX_LENGTH = 40, VECTOR_MAX_MOVE_AMOUNT = 5;
	private Rectangle2D visible,barrel;
	private Point2D barrelEnd;
	private Color color;
	private Line2D dirVect,aimVect;
	private XInputDevice14 controller;
	private double moveVecAng, moveVecMag, aimVecAng; //ang is from 0 to 2pi, mag is 0 to 1
	private boolean isShooting = false;
	//weapon info
	private int wepID = 0, projCount = 1;
	private double accMin = -0, accMax = 0;
	private Color projColor = Color.YELLOW;
	private double projSize = 15, projSpeed = 50;
	private Timer shootTimer = new Timer();
	private ShootReset sr = new ShootReset();
	private boolean canShoot = true;
	class ShootReset extends TimerTask{
		@Override
		public void run() {
			canShoot = true;
		}
	}
	
	public PlayerBox(double xPos, double yPos, Color c, XInputDevice14 xin){
		this.visible = new Rectangle2D.Double(xPos,yPos,WIDTH,LENGTH);
		this.barrel = new Rectangle2D.Double(visible.getCenterX(),visible.getCenterY()-0.05*LENGTH,1.2*WIDTH,0.1*LENGTH);
		this.color = c;
		this.controller = xin;
		this.moveVecMag = 0;
		this.moveVecAng = 0;
		this.aimVecAng = 0;
		this.shootTimer.schedule(sr, 0, 100);
	}

	public void changeState() {
		if (controller.poll()) {
			XInputComponents components = controller.getComponents();
			XInputButtons buttons = components.getButtons();
			XInputAxes axes = components.getAxes();
			XInputComponentsDelta delta = controller.getDelta();
		    XInputButtonsDelta buttonDelta = delta.getButtons();
		    
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
			
			if(axes.rt > 0 && canShoot){
				isShooting = true;
				canShoot = false;
			} else {
				isShooting = false;
			}
			
			if(buttonDelta.isPressed(XInputButton.RIGHT_SHOULDER)){
				wepID++;
				if(wepID > 2) wepID = 0;
				switch(wepID){
					case 0:
						sr.cancel();
						shootTimer.purge();
						sr = new ShootReset();
						shootTimer.schedule(sr,0,100);
						accMin = -0;
						accMax = 0;
						projCount = 1;
						projColor = Color.YELLOW;
						projSize = 15;
						projSpeed = 50;
						break;
					case 1:
						sr.cancel();
						shootTimer.purge();
						sr = new ShootReset();
						shootTimer.schedule(sr,0,10);
						accMin = -0.4;
						accMax = 0.4;
						projCount = 1;
						projColor = Color.CYAN;
						projSize = 10;
						projSpeed = 30;
						break;
					case 2:
						sr.cancel();
						shootTimer.purge();
						sr = new ShootReset();
						shootTimer.schedule(sr,0,400);
						accMin = -0.2;
						accMax = 0.2;
						projCount = 3;
						projColor = Color.BLUE;
						projSize = 20;
						projSpeed = 40;
						break;
				}
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

	public double getRandInRange(double min, double max) {
		return (Math.random()*(max+Math.abs(min)))+min;
		
	}

	public int getWepID() {
		return wepID;
	}

	public int getProjCount() {
		return projCount;
	}

	public double getAccMin() {
		return accMin;
	}

	public double getAccMax() {
		return accMax;
	}

	public Color getProjColor() {
		return projColor;
	}

	public double getProjSize() {
		return projSize;
	}

	public double getProjSpeed() {
		return projSpeed;
	}

	public Timer getShootTimer() {
		return shootTimer;
	}

	public boolean isCanShoot() {
		return canShoot;
	}
	
}
