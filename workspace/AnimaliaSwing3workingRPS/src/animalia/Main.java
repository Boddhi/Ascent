package animalia;

import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.SwingUtilities;

public class Main {
	public static void main(String[] args) {
		ArrayBlockingQueue<String> toServerQueue = new ArrayBlockingQueue<String>(5);
		ArrayBlockingQueue<String> player1FromServerQueue = new ArrayBlockingQueue<String>(5);
		ArrayBlockingQueue<String> player2FromServerQueue = new ArrayBlockingQueue<String>(5);
		
		ServerThread serverThread = new ServerThread(toServerQueue, "1", player1FromServerQueue, "2", player2FromServerQueue);
		serverThread.start();

		createPlayer("1", toServerQueue, player1FromServerQueue);
		createPlayer("2", toServerQueue, player2FromServerQueue);
	}
	
	private static void createPlayer(final String id,
			final ArrayBlockingQueue<String> toServerQueue,
			final ArrayBlockingQueue<String> fromServerQueue) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Player(new Connection(id, toServerQueue, fromServerQueue));
			}
		});
	}
}
