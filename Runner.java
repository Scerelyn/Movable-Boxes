package BoxController;

import java.awt.Color;
import com.ivan.xinput.XInputDevice;
import com.ivan.xinput.XInputDevice14;
import com.ivan.xinput.enums.XInputButton;
import com.ivan.xinput.exceptions.XInputNotLoadedException;

public class Runner {

	public static void main(String[] args) {
		try{
			XInputDevice14[] devices = XInputDevice14.getAllDevices();
			PlayerBox p1 = new PlayerBox(50,50,Color.RED,devices[0]);
			p1.changeState();
		} catch (XInputNotLoadedException e){
			
		}

	}
	
}
