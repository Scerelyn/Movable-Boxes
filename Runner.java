package BoxController;

import java.awt.Color;
import java.util.Timer;

import javax.swing.JFrame;

import com.ivan.xinput.XInputDevice;
import com.ivan.xinput.XInputDevice14;
import com.ivan.xinput.enums.XInputButton;
import com.ivan.xinput.exceptions.XInputNotLoadedException;

public class Runner {

	public static void main(String[] args) {
		try{
			Timer t = new Timer();
			XInputDevice14[] devices = XInputDevice14.getAllDevices();
			PlayerBox p1 = new PlayerBox(50,50,Color.RED,devices[0]);
			p1.changeState();
			
			Visual v = new Visual(0,0,1200,700);
			v.addPlayer(p1);
			VisualRefresher vr = new VisualRefresher(v);
			t.schedule(vr, 0, 10);
			
			JFrame jf = new JFrame();
			jf.getContentPane().add(v);
			jf.pack();
			jf.setBounds(0, 500, 1250, 750);
			jf.setVisible(true);
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			ControllerStatsWindow cw = new ControllerStatsWindow(p1);
			cw.setVisible(true);
			StatsWindowRefresher cwr = new StatsWindowRefresher(cw);
			t.schedule(cwr,0,10);
		} catch (XInputNotLoadedException e){
			System.out.println("XInput device didn't load or something happened");
		}

	}
	
}
