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
			g2.setPaint(pb.getColor());
			g2.fill(pb.getVisible());
			g2.setPaint(Color.BLACK);
			g2.draw(pb.getDirVect());
		}
	}
}
