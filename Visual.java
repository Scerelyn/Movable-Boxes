package BoxController;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import javax.swing.JComponent;

public class Visual extends JComponent{
	private ArrayList<PlayerBox> players = new ArrayList<>();
	private Rectangle2D bg;
	
	public Visual(int xPos, int yPos, int width, int height){
		this.setBounds(xPos, yPos, width, height);
		bg = new Rectangle2D.Double(xPos,yPos,width,height);
	}
	
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.setPaint(Color.LIGHT_GRAY);
		g2.fill(bg);
		for(PlayerBox pb : players){
			if( !(pb.getVisible().getX() < 0 || pb.getVisible().getX()+pb.getVisible().getWidth() > bg.getX()+bg.getWidth() || pb.getVisible().getY() < 0 || pb.getVisible().getY()+pb.getVisible().getHeight() > bg.getY()+bg.getHeight()) ){
				pb.move();
			} else {
				if(pb.getVisible().getX() < 0){
					pb.setPos(0, pb.getVisible().getY());
				} else if(pb.getVisible().getX()+pb.getVisible().getWidth() > bg.getX()+bg.getWidth()){
					pb.setPos(this.bg.getWidth()-pb.getVisible().getWidth(), pb.getVisible().getY());
				} else if(pb.getVisible().getY() < 0){
					pb.setPos(pb.getVisible().getX(),0);
				} else if(pb.getVisible().getY()+pb.getVisible().getHeight() > bg.getY()+bg.getHeight()){
					pb.setPos(pb.getVisible().getX(),this.bg.getHeight()-pb.getVisible().getHeight());
				}
			}
			pb.changeState();
			g2.setPaint(pb.getColor());
			g2.fill(pb.getVisible());
			g2.setPaint(pb.getColor().darker());
			
			g2.setPaint(Color.BLACK);
			g2.draw(pb.getDirVect());
			g2.draw(pb.getAimVect());
			//negate angle and subtract 90deg, idk what the anle is based on but it works now i guess
			g2.rotate((-Math.PI/2)-pb.getAimVecAng(),pb.getVisible().getCenterX(), pb.getVisible().getCenterY());
			g2.fill(pb.getBarrel());
		}
	}
	
	public void addPlayer(PlayerBox pb){
		players.add(pb);
	}
}
