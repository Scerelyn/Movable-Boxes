package BoxController;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import javax.swing.JComponent;

public class Visual extends JComponent{
	private ArrayList<PlayerBox> players = new ArrayList<>();
	private ArrayList<Projectile> projectiles = new ArrayList<>();
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
		ArrayList<Projectile> toRemove = new ArrayList<>();
		for(Projectile p : projectiles){
			if( !(p.getVisible().getX() < 0 || p.getVisible().getX()+p.getVisible().getWidth() > bg.getX()+bg.getWidth() || p.getVisible().getY() < 0 || p.getVisible().getY()+p.getVisible().getHeight() > bg.getY()+bg.getHeight()) ){
				p.move();
			} else {
				toRemove.add(p);
			}
			g2.setPaint(p.getpColor());
			g2.fill(p.getVisible());
			
		}
		
		for(PlayerBox pb : players){
			Graphics2D g2clone = (Graphics2D)(g2.create());
			
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
			
			if(pb.isShooting()){
				for(int i = 0; i < pb.getProjCount(); i++){					
					projectiles.add( new Projectile(
						pb.getBarrelEnd().getX()-pb.getProjSize()/2, 
						pb.getBarrelEnd().getY()-pb.getProjSize()/2,
						pb.getProjSize(),
						pb.getProjSize(),
						1,
						pb.getAimVecAng()+pb.getRandInRange(pb.getAccMin(), pb.getAccMax()),
						pb.getProjSpeed(),
						pb.getProjColor()
						)
					);
				}
			}
			g2clone.rotate((Math.PI/2)-pb.getMoveVecAng(),pb.getVisible().getCenterX(),pb.getVisible().getCenterY());
			g2clone.setPaint(pb.getColor());
			g2clone.fill(pb.getVisible());;
			g2clone.dispose();
			
			Graphics2D g2clone2 = (Graphics2D)(g2.create());
			//negate angle and subtract 90deg, idk what the angle is based on but it works now i guess
			g2clone2.setPaint(Color.BLACK);
			g2clone2.rotate(-pb.getAimVecAng(),pb.getVisible().getCenterX(), pb.getVisible().getCenterY());
			g2clone2.fill(pb.getBarrel());
			g2clone2.dispose();
			
			g2.setPaint(Color.CYAN);
			g2.draw(pb.getDirVect());
			g2.draw(pb.getAimVect());
		}
		for(Projectile p : toRemove){
			projectiles.remove(p);
		}
	}
	
	public void addPlayer(PlayerBox pb){
		players.add(pb);
	}
	
	public void addProjectile(Projectile p){
		projectiles.add(p);
	}
}
