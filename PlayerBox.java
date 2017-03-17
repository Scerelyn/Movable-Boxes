package BoxController;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import com.ivan.xinput.XInputAxes;
import com.ivan.xinput.XInputButtons;
import com.ivan.xinput.XInputComponents;
import com.ivan.xinput.XInputDevice14;

public class PlayerBox {
	public final static double LENGTH = 100, WIDTH = 100;
	Rectangle2D visible;
	Color color;
	Line2D dirVect;
	XInputDevice14 controller;
	double vectAng, vecMag;
	public PlayerBox(double xPos, double yPos, Color c, XInputDevice14 xin){
		this.visible = new Rectangle2D.Double(xPos,yPos,LENGTH,WIDTH);
		this.color = c;
		this.controller = xin;
		this.vecMag = 0;
		this.vectAng = 0;
	}

	public void changeState() {
		if (controller.poll()) {
			XInputComponents components = controller.getComponents();
			XInputButtons buttons = components.getButtons();
			XInputAxes axes = components.getAxes();
			
			double ang = Math.atan( axes.ry / axes.rx );
			double magSet = axes.rt;
			
			
			System.out.println("Left thumb stick x: " + axes.lx + " y: " + axes.ly);
			System.out.println("Right thumb stick x: " + axes.rx + " y: " + axes.ry);
			System.out.println("Left trigger: " + axes.lt + " Right trigger: " + axes.rt);
			System.out.println("Buttons: a:" + buttons.a + " b: " + buttons.b + " x: " + buttons.x + " y: " + buttons.y);
		} else {
			System.out.println("Controller disconnected");
		}
	}
}
