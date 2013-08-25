package animalia;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MoveListener implements ActionListener {
	private final Connection playerEnvironment;
	String moveName;
	
	public MoveListener(Connection playerEnvironment, String moveName) {
		this.playerEnvironment = playerEnvironment;
		this.moveName = moveName;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		playerEnvironment.send("move," + moveName);
	}
}
