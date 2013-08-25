package animalia;

import java.util.concurrent.ArrayBlockingQueue;

public class ServerPlayerStatus {
	private boolean loggedIn = false;
	private boolean usedMove = false;
	private String name = null;
	private String move = null;
	private final String id;
	private final ArrayBlockingQueue<String> toPlayerQueue;
	
	public ServerPlayerStatus(String id, ArrayBlockingQueue<String> toPlayerQueue) {
		this.id = id;
		this.toPlayerQueue = toPlayerQueue;
	}
	public void logIn(String name) {
		this.name = name;
		loggedIn = true; 
	}
	public boolean isLoggedIn() {
		return loggedIn;
	}
	public void useMove(String move) {
		this.move = move;
		usedMove = true; 
	}
	public boolean hasUsedMove() {
		return usedMove;
	}
	
	public String getMove() {
		return move;
	}
	
	public String getID() {
		return id;
	}
	public ArrayBlockingQueue<String> getToPlayerQueue() {
		return toPlayerQueue;
	}
	

}
