package animalia;

import java.beans.PropertyChangeListener;
import java.util.concurrent.ArrayBlockingQueue;

public class Connection {
	private final ServerWorker serverWorker;
	private final ArrayBlockingQueue<String> toServerQueue;
	private final ArrayBlockingQueue<String> fromServerQueue;
	private final String id;
	
	public Connection (String id, ArrayBlockingQueue<String> toServerQueue, ArrayBlockingQueue<String> fromServerQueue) {
		this.id = id;
		this.toServerQueue = toServerQueue;
		this.fromServerQueue = fromServerQueue;
		this.serverWorker = new ServerWorker(fromServerQueue);
		serverWorker.execute();
 	}
	
	public void send(String message) {
		String information = (id + "," + message);
		System.out.println("player sending message = " + information);
		try {
			toServerQueue.put(information);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
//	public String poll() {
//		String receivedMessage = fromServerQueue.poll();
//		System.out.println("player " + id + "poll received = " + receivedMessage);
//		return receivedMessage;
//	}
	
	public String removeMessage(){
		String receivedMessage = serverWorker.removeMessage();
		System.out.println("player " + id + "take received = " + receivedMessage);
		return receivedMessage;
//		try {
//			String receivedMessage = fromServerQueue.take();
//			System.out.println("player " + id + "take received = " + receivedMessage);
//			return receivedMessage;
//		} catch (InterruptedException e) {
//			throw new RuntimeException("interrupted take", e);
//		}
	}
	
	public void addInputListener(PropertyChangeListener listener){
		serverWorker.addPropertyChangeListener(listener);
	}
}
