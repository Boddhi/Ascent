package animalia;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.SwingWorker;

public class ServerWorker extends SwingWorker <Object,Object> {
	private final ArrayBlockingQueue<String> inputQueue;
	private final LinkedBlockingQueue<String> receivedMessagesQueue = new LinkedBlockingQueue<String>();
	public ServerWorker (ArrayBlockingQueue<String> queue) {
		this.inputQueue = queue;
	}
	@Override
	public Object doInBackground() {
		int messageNumber = 0;
		while (true) {
			try {
				System.out.println("about to take");
				String message = inputQueue.take();
				System.out.println("server worker message = " + message);
				messageNumber++;
				receivedMessagesQueue.put(message);
				setProgress(messageNumber);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}
	public String removeMessage() {
		try {
			return receivedMessagesQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
