package BoxController;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Timer;

import javax.swing.JFrame;

import com.ivan.xinput.XInputDevice;
import com.ivan.xinput.XInputDevice14;
import com.ivan.xinput.enums.XInputButton;
import com.ivan.xinput.exceptions.XInputNotLoadedException;

import BoxController.Enemies.BasicEnemy;
import BoxController.Enemies.Enemy;

public class Runner {

	public static void main(String[] args) {
		try{
			Timer t = new Timer();
			XInputDevice14[] devices = XInputDevice14.getAllDevices();
			PlayerBox p1 = new PlayerBox(20,20,Color.GREEN.darker().darker(),devices[0]);
			PlayerBox p2 = new PlayerBox(20,20,Color.YELLOW.darker().darker(),devices[1]);
			p1.changeState();
			p2.changeState();
			Enemy e1 = new BasicEnemy(200,250);
			
			
			
			Visual v = new Visual(0,0,2000,1000);
			v.addPlayer(p1);
			v.addEnemy(e1);
			e1.setTargetList((ArrayList<PlayerBox>)v.getPlayers().clone()); //cloning because maybe the list will be altered
			e1.setRandomCurrentTarget();
			
			
			//ArrayList<Enemy> enemiesss = new ArrayList<>();
			//for(int i = 0; i < 50; i++){
			//	enemiesss.add(new BasicEnemy( (int)(Math.random()*800),(int)(Math.random()*800) ));
			//	enemiesss.get(i).setTargetList((ArrayList<PlayerBox>)v.getPlayers().clone()); 
			//	enemiesss.get(i).setRandomCurrentTarget();
			//	v.addEnemy(enemiesss.get(i));
			//}
			
			
			
			//v.addPlayer(p2);
			VisualRefresher vr = new VisualRefresher(v);
			t.schedule(vr, 0, 10);
			
			JFrame jf = new JFrame();
			jf.getContentPane().add(v);
			jf.pack();
			jf.setBounds(0, 0, 2000, 1150);
			jf.setVisible(true);
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			//ControllerStatsWindow cw = new ControllerStatsWindow(p1);
			//cw.setVisible(true);
			//StatsWindowRefresher cwr = new StatsWindowRefresher(cw);
			//t.schedule(cwr,0,10);
		} catch (XInputNotLoadedException e){
			System.out.println("XInput device didn't load or something happened");
		}

	}
	
}
