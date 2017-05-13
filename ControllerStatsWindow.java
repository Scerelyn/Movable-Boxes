package BoxController;

import java.awt.*;

import javax.swing.*;

import com.ivan.xinput.XInputAxes;
import com.ivan.xinput.XInputButtons;
import com.ivan.xinput.XInputComponents;

public class ControllerStatsWindow extends JFrame{
	PlayerBox pb;
	JLabel rs,ls,lt,rt,abxy;
	public ControllerStatsWindow(PlayerBox pb){
		XInputComponents components = pb.getController().getComponents();
		XInputButtons buttons = components.getButtons();
		XInputAxes axes = components.getAxes();
		
		Font f = new Font("Sans-serif", Font.PLAIN, 48);
		this.pb = pb;
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		rs = new JLabel("Right Stick: x:" + axes.rx + " y: " + axes.ry);
		rs.setFont(f);
		ls = new JLabel("Left Stick: x:" + axes.lx + " y: " + axes.ly);
		ls.setFont(f);
		lt = new JLabel("Left Trigger: " + axes.lt);
		lt.setFont(f);
		rt = new JLabel("Right Trigger: " + axes.rt);
		rt.setFont(f);
		abxy = new JLabel("A: " + buttons.a + " B: " + buttons.b + " X: " + buttons.x + " Y: " + buttons.y);
		abxy.setFont(f);
		this.getContentPane().add(rs);
		this.getContentPane().add(ls);
		this.getContentPane().add(lt);
		this.getContentPane().add(rt);
		this.getContentPane().add(abxy);
		this.pack();
	}
	
	public void updateLabels(){
		XInputComponents components = pb.getController().getComponents();
		XInputButtons buttons = components.getButtons();
		XInputAxes axes = components.getAxes();
		
		rs.setText("Right Stick: x:" + axes.rx + " y: " + axes.ry);
		ls.setText("Left Stick: x:" + axes.lx + " y: " + axes.ly);
		lt.setText("Left Trigger: " + axes.lt);
		rt.setText("Right Trigger: " + axes.rt);
		abxy.setText("A: " + buttons.a + " B: " + buttons.b + " X: " + buttons.x + " Y: " + buttons.y);
		this.pack();
	}
}
