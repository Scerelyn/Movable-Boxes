package BoxController.Enemies;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class BasicEnemy extends Enemy{

	public BasicEnemy(double xPos, double yPos) {
		super(xPos, yPos);
		this.rotationAffectedShapes[0] = new int[1];
		this.rotationAffectedShapes[0][0] = 2;
		this.visibleColors = new Color[3];
		build();
	}

	@Override
	public void build() { //magic numbers for testing reasons
		xRotCenter = xPos + 15;
		yRotCenter = yPos + 15;
		this.visibleParts = new Shape[3];
		this.visibleParts[0] = new Rectangle2D.Double(xPos,yPos,30,30);
		this.visibleColors[0] = Color.RED;
		this.visibleParts[1] = new Ellipse2D.Double(xPos+5,yPos+5,20,20);
		this.visibleColors[1] = Color.YELLOW;
		this.visibleParts[2] = new Rectangle2D.Double(xPos+10,yPos+10,40,10);
		this.visibleColors[2] = Color.BLUE;
	}

	@Override
	public void update() {
		rotAng1 += Math.PI/45.0; //so it just spins around for now
	}
	
}
