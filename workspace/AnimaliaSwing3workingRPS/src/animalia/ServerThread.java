package animalia;

import java.util.concurrent.ArrayBlockingQueue;

public class ServerThread extends Thread {
	private Server server;
	private ArrayBlockingQueue<String> toServerQueue;
	private final ServerPlayerStatus player1;
	private final ServerPlayerStatus player2;
	
	public ServerThread(ArrayBlockingQueue<String> toServerQueue,
			String player1ID, ArrayBlockingQueue<String> player1ServerQueue,
			String player2ID, ArrayBlockingQueue<String> player2ServerQueue) {
		this.toServerQueue = toServerQueue;
		player1 = new ServerPlayerStatus(player1ID, player1ServerQueue);
		player2 = new ServerPlayerStatus(player2ID, player2ServerQueue);
	}
	
	public void run() {

	
		while(true) {
			String message = null;
			try {
				message = toServerQueue.take();
				String[] tokens = message.split(",");
				String connectionID = tokens[0];
				ServerPlayerStatus player;
				ServerPlayerStatus otherPlayer;
				if (connectionID.equals(player1.getID())) {
					player = player1;
					otherPlayer = player2;
				}
				else {
					player = player2;
					otherPlayer = player1;
				}
				
				String messageType = tokens[1];

				if (messageType.equals("login")) {
					if (player.isLoggedIn()) {
						System.out.println("player tried to log in twice");
					} else {
						String username = tokens[2];

						if (username.equals("gabe")
								|| username.equals("gabriel")) {
							player.logIn(username);
							player.getToPlayerQueue().put("enjoy!");
						} else {
							player.getToPlayerQueue().put("Incorrect Username");
						}
					}
				} else if (messageType.equals("move")) {
					String move = tokens[2];
					
					if (!player.hasUsedMove() && otherPlayer.hasUsedMove()) {
						player.useMove(move);
						System.out.println("both players have chosen a move");
						System.out.println(player.getMove() + " vs "
								+ otherPlayer.getMove());
						if ((player.getMove().equals("rock") && otherPlayer
								.getMove().equals("scissors"))
								|| (player.getMove().equals("scissors") && otherPlayer
										.getMove().equals("paper"))
								|| (player.getMove().equals("paper") && otherPlayer
										.getMove().equals("rock"))) {
							player.getToPlayerQueue().put(
									"You win!");
							otherPlayer.getToPlayerQueue().put(
									"You lose");
						} else if ((player.getMove().equals("scissors") && otherPlayer
								.getMove().equals("scissors"))
								|| (player.getMove().equals("paper") && otherPlayer
										.getMove().equals("paper"))
								|| (player.getMove().equals("rock") && otherPlayer
										.getMove().equals("rock"))) {
							player1.getToPlayerQueue().put("It's a tie!");
							player2.getToPlayerQueue().put("It's a tie!");
						} else {
							otherPlayer.getToPlayerQueue().put(
									"You win!");
							player.getToPlayerQueue().put(
									"You lose");
						}
						
					} else if (!player.hasUsedMove()
							&& !otherPlayer.hasUsedMove()) {
						player.getToPlayerQueue().put("pending");
						player.useMove(move);
					} else if (player.hasUsedMove()
							&& !otherPlayer.hasUsedMove()) {
						player.getToPlayerQueue().put("you have already chosen a move!");
					}
				}
			}
			  catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			System.out.println("server received message = " + message);
		}
	}
}
