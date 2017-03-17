package BoxController;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import com.ivan.xinput.XInputAxes;
import com.ivan.xinput.XInputButtons;
import com.ivan.xinput.XInputComponents;
import com.ivan.xinput.XInputDevice14;

public class PlayerBox {
	public final static double LENGTH = 100, WIDTH = 100, VECTOR_MAX_LENGTH = 100;
	private Rectangle2D visible;
	private Color color;
	private Line2D dirVect;
	private XInputDevice14 controller;
	private double vecAng, vecMag; //ang is from 0 to 2pi, mag is 0 to 1
	
	
	public PlayerBox(double xPos, double yPos, Color c, XInputDevice14 xin){
		this.visible = new Rectangle2D.Double(xPos,yPos,LENGTH,WIDTH);
		this.color = c;
		this.controller = xin;
		this.vecMag = 0;
		this.vecAng = 0;
	}

	public void changeState() {
		if (controller.poll()) {
			XInputComponents components = controller.getComponents();
			XInputButtons buttons = components.getButtons();
			XInputAxes axes = components.getAxes();
			
			double ang = Math.atan( axes.ly / axes.lx );
			double magSet = axes.rt;
			
			//transforming into non terrible angles that arctan gives, from 0 to 2pi rather than -pi/2 to pi/2
			if(axes.lx >= 0 && axes.ly >= 0){ //quadrant I
				
			} else if((axes.lx < 0 && axes.ly >= 0) || (axes.lx < 0 && axes.ly < 0)){ //II or III, since the result is the same they are grouped up
				ang += Math.PI;
			}  else if(axes.lx >= 0 && axes.ly < 0){ //IV
				ang += (2*Math.PI);
			}
			
			vecAng = ang;
			vecMag = magSet;
			
			dirVect = new Line2D.Double(visible.getCenterX(), visible.getCenterY(), visible.getCenterX()+vecMag*VECTOR_MAX_LENGTH*Math.cos(vecAng),visible.getCenterY()-vecMag*VECTOR_MAX_LENGTH*Math.sin(vecAng));
			//System.out.println("Left thumb stick x: " + axes.lx + " y: " + axes.ly + " atan ang: " + Math.toDegrees(ang));
			//System.out.println("Right thumb stick x: " + axes.rx + " y: " + axes.ry);
			//System.out.println("Left trigger: " + axes.lt + " Right trigger: " + axes.rt);
			//System.out.println("Buttons: a:" + buttons.a + " b: " + buttons.b + " x: " + buttons.x + " y: " + buttons.y);
		} else {
			System.out.println("Controller disconnected");
		}
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

	public double getVecAng() {
		return vecAng;
	}

	public double getVecMag() {
		return vecMag;
	}
	
	
}
