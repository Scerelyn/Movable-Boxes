package BoxController;

import java.awt.*;

import javax.swing.*;

import com.ivan.xinput.XInputAxes;
import com.ivan.xinput.XInputButtons;
import com.ivan.xinput.XInputComponents;

public class ControllerStatsWindow extends JFrame{
	PlayerBox pb;
	JLabel cxn,rs,ls,lt,rt;
	public ControllerStatsWindow(PlayerBox pb){
		XInputComponents components = pb.getController().getComponents();
		XInputButtons buttons = components.getButtons();
		XInputAxes axes = components.getAxes();
		
		this.pb = pb;
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		cxn = new JLabel("Controller: " + pb.getController().poll());
		rs = new JLabel("Right Stick: x:" + axes.rx + " y: " + axes.ry);
		ls = new JLabel("Left Stick: x:" + axes.lx + " y: " + axes.ly);
		lt = new JLabel("Left Trigger: " + axes.lt);
		rt = new JLabel("Right Trigger: " + axes.rt);
		this.getContentPane().add(cxn);
		this.getContentPane().add(rs);
		this.getContentPane().add(ls);
		this.getContentPane().add(lt);
		this.getContentPane().add(rt);
		this.pack();
	}
	
	public void updateLabels(){
		XInputComponents components = pb.getController().getComponents();
		XInputButtons buttons = components.getButtons();
		XInputAxes axes = components.getAxes();
		
		cxn.setText("Controller: " + pb.getController().poll());
		rs.setText("Right Stick: x:" + axes.rx + " y: " + axes.ry);
		ls.setText("Left Stick: x:" + axes.lx + " y: " + axes.ly);
		lt.setText("Left Trigger: " + axes.lt);
		rt.setText("Right Trigger: " + axes.rt);
	}
}
