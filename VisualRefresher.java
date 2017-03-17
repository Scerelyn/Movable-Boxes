package BoxController;

import java.util.TimerTask;

public class VisualRefresher extends TimerTask{
	
	private Visual v;
	
	public VisualRefresher(Visual v) {
		this.v = v;
	}

	@Override
	public void run() {
		v.repaint();
		
		
	}
	
}
