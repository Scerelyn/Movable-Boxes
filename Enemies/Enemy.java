package BoxController.Enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import BoxController.PlayerBox;

public abstract class Enemy {
	protected PlayerBox currentTarget;
	protected ArrayList<PlayerBox> targetList = new ArrayList<>();
	protected Shape[] visibleParts;
	protected Shape[] hitboxes;
	protected Color[] visibleColors; //indexes of this array match the elements in visibleParts
	protected double xPos = 0,yPos = 0,xRotCenter = 0,yRotCenter = 0,rotAng1 = 0,rotAng2 = 0,rotAng3 = 0,rotAng4 = 0;
	protected int[][] rotationAffectedShapes = new int[4][]; //defines which visible parts/hitboxes are affected by which roation variable
															 //[rotAng#][shape#]
	protected boolean canShoot = true;
	protected static final Timer fireLimiter = new Timer();
	protected ShootReseter reseter;
	protected class ShootReseter extends TimerTask{
		@Override
		public void run(){
			canShoot = true;
		}
	}
	
	public Enemy(double xPos, double yPos){
		this.xPos = xPos;
		this.yPos = yPos;
		rotationAffectedShapes[0] = new int[0];
		rotationAffectedShapes[1] = new int[0];
		rotationAffectedShapes[2] = new int[0];
		rotationAffectedShapes[3] = new int[0];
		this.visibleColors = new Color[0];
	}
	
	
	public abstract void build(); //builds the visibleParts, hitboxes, and visibleColors arrays
	public abstract void update(); //updates things like angles and rebuilds if needed
	
	public Shape[] getVisibleParts() {
		return visibleParts;
	}
	public Shape[] getHitboxes() {
		return hitboxes;
	}
	public Color[] getVisibleColors() {
		return visibleColors;
	}
	public double getxPos() {
		return xPos;
	}
	public double getyPos() {
		return yPos;
	}
	public double getRotAng1() {
		return rotAng1;
	}

	public double getRotAng2() {
		return rotAng2;
	}

	public double getRotAng3() {
		return rotAng3;
	}

	public double getRotAng4() {
		return rotAng4;
	}

	public int[][] getRotationAffectedShapes() {
		return rotationAffectedShapes;
	}
	
	public double getxRotCenter() {
		return xRotCenter;
	}

	public double getyRotCenter() {
		return yRotCenter;
	}

	public int rotationAffected(int shapeIndex){
		int angleAffected = 0;
		for(int angleNum = 0; angleNum < 4; angleNum++){
			for(int shapeNum : rotationAffectedShapes[angleNum]){
				if(shapeNum == shapeIndex){
					return angleNum;
				}
			}
		}
		return -1;
	}

	public PlayerBox getCurrentTarget() {
		return currentTarget;
	}

	public ArrayList<PlayerBox> getTargetList() {
		return targetList;
	}

	public void setRandomCurrentTarget() {
		this.currentTarget = targetList.get( (int)(Math.random()*targetList.size()) );
	}

	public void setTargetList(ArrayList<PlayerBox> targetList) {
		this.targetList = targetList;
	}
	
	
}
