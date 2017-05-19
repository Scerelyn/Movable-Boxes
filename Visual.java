package BoxController;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import javax.swing.JComponent;

import BoxController.Enemies.Enemy;

public class Visual extends JComponent{
	private static final boolean SHOW_HITBOXES = false;
	private ArrayList<PlayerBox> players = new ArrayList<>();
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	private ArrayList<Enemy> enemies = new ArrayList<>();
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
		
		for(PlayerBox pb : players){
			for(Projectile p : projectiles){
				if(!p.isPlayerFired() && p.getVisible().intersects(pb.getHitbox())){
					pb.setHitEffectDuration(5);
					toRemove.add(p);
				}
			}
			if(pb.getHitEffectDuration() > 0){
				pb.setColor(pb.getGotHitColor());
				pb.setHitEffectDuration(pb.getHitEffectDuration()-1);
			} else {
				pb.setColor(pb.getColorOrig());
			}
			Graphics2D g2clone = (Graphics2D)(g2.create());
			
			if( !(pb.getVisible().getX() < 0 || pb.getVisible().getX()+PlayerBox.MOVEMENT_HITBOX_LENGTH > bg.getX()+bg.getWidth() || pb.getVisible().getY() < 0 || pb.getVisible().getY()+PlayerBox.MOVEMENT_HITBOX_LENGTH > bg.getY()+bg.getHeight()) ){
				pb.move();
			} else {
				if(pb.getVisible().getX() < 0){
					pb.setPos(0, pb.getVisible().getY());
				} else if(pb.getVisible().getX()+PlayerBox.MOVEMENT_HITBOX_LENGTH > bg.getX()+bg.getWidth()){
					pb.setPos(this.bg.getWidth()-PlayerBox.MOVEMENT_HITBOX_LENGTH, pb.getVisible().getY());
				} else if(pb.getVisible().getY() < 0){
					pb.setPos(pb.getVisible().getX(),0);
				} else if(pb.getVisible().getY()+PlayerBox.MOVEMENT_HITBOX_LENGTH > bg.getY()+bg.getHeight()){
					pb.setPos(pb.getVisible().getX(),this.bg.getHeight()-PlayerBox.MOVEMENT_HITBOX_LENGTH);
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
						pb.getProjColor(),
						true
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
		
			g2.setPaint(Color.GRAY);
			g2.fill(pb.getVisibleHitbox());
			if(SHOW_HITBOXES){
				g2.setPaint(Color.RED);
				g2.fill(pb.getHitbox());
			}
		}
		
		for(Enemy e : enemies){
			e.build();
			e.update();
			
			for(Projectile p : projectiles){
				if(p.isPlayerFired() && p.getVisible().intersects(e.getHitbox().getBounds2D())){
					e.setHitEffectDuration(5);
					toRemove.add(p);
				}
			}
			if(e.getHitEffectDuration() > 0){
				e.setMainColor(Color.WHITE);
				e.setHitEffectDuration(e.getHitEffectDuration()-1);
			} else {
				e.setMainColor(e.getVisibleColorsOrig()[0]);
			}
			
			for(int i = 0; i < e.getVisibleParts().length; i++){
				g2.setPaint(e.getVisibleColors()[i]);
				int rotAngleID = e.rotationAffected(i);
				if(rotAngleID != -1){
					Graphics2D g2clone3 = (Graphics2D)(g2.create());
					switch(rotAngleID){
					case 0:
						g2clone3.rotate(e.getRotAng1(),e.getxRotCenter(),e.getyRotCenter());
						break;
					case 1:
						g2clone3.rotate(e.getRotAng2(),e.getxRotCenter(),e.getyRotCenter());
						break;
					case 2:
						g2clone3.rotate(e.getRotAng3(),e.getxRotCenter(),e.getyRotCenter());
						break;
					case 3:
						g2clone3.rotate(e.getRotAng4(),e.getxRotCenter(),e.getyRotCenter());
						break;
					}
					g2clone3.fill(e.getVisibleParts()[i]);
				} else {
					g2.fill(e.getVisibleParts()[i]);
				}
			}
			if(e.canShoot()){
				Projectile p = e.shoot();
				if(p != null){
					projectiles.add(p);
				}
			}
		}
		
		for(Projectile p : projectiles){
			if( !(p.getVisible().getX() < 0 || p.getVisible().getX()+p.getVisible().getWidth() > bg.getX()+bg.getWidth() || p.getVisible().getY() < 0 || p.getVisible().getY()+p.getVisible().getHeight() > bg.getY()+bg.getHeight()) ){
				p.move();
			} else {
				toRemove.add(p);
			}
			g2.setPaint(p.getpColor());
			g2.fill(p.getVisible());
			
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
	
	public void addEnemy(Enemy e){
		enemies.add(e);
	}

	public ArrayList<PlayerBox> getPlayers() {
		return players;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}
	
	
}
