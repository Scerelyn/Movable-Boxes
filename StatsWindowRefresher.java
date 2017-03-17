package BoxController;

import java.util.TimerTask;

public class StatsWindowRefresher extends TimerTask{
	private ControllerStatsWindow cw;
	
	public StatsWindowRefresher(ControllerStatsWindow cw){
		this.cw = cw;
	}
	@Override
	public void run() {
		cw.updateLabels();
		
	}

}
