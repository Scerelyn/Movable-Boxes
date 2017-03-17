package BoxController;

import java.awt.Color;
import java.util.Timer;

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
			
			ControllerStatsWindow cw = new ControllerStatsWindow(p1);
			cw.setVisible(true);
			StatsWindowRefresher cwr = new StatsWindowRefresher(cw);
			t.schedule(cwr,0,10);
		} catch (XInputNotLoadedException e){
			
		}

	}
	
}
