package animalia;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Panel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Player implements PropertyChangeListener {
	private static final int WIDTH = 200;
	private static final int HEIGHT = 200;
	private String userName = "Username";
	private JFormattedTextField userNameField;
	private String text = "Select a move";
    private JLabel textBox = new JLabel(text);
	
	
	private final Connection playerEnvironment;

	JFrame buttonFrame = new JFrame("Animalia");
    JFrame logInFrame = new JFrame("Animalia");
    
    JButton rock = new JButton("Rock");
    JButton paper = new JButton("Paper");
    JButton scissors = new JButton("scissors");

	public Player(Connection playerEnvironment) {
		this.playerEnvironment = playerEnvironment;

		Dimension size = new Dimension(WIDTH, HEIGHT);
		

		
		JPanel buttonPanel = new JPanel();
	    buttonPanel.setOpaque(true); //content panes must be opaque

	    JPanel logInPanel = new JPanel();
	    logInPanel.setOpaque(true);
	    
//	    JFormattedTextField authenticatingField = new JFormattedTextField();
//	    authenticatingField.setValue(authenticating);
//	    authenticatingField.setColumns(10);
//	    authenticatingField.setEditable(false);
//	    authenticatingField.setForeground(Color.green);
//	    logInPanel.add(authenticatingField);

	    userNameField = new JFormattedTextField();
	    userNameField.setColumns(20);
	    userNameField.setValue(userName);
	    logInPanel.add(userNameField);
	    userNameField.addPropertyChangeListener("value", this);
	    
        rock.setVerticalTextPosition(AbstractButton.CENTER);
        rock.setHorizontalTextPosition(AbstractButton.LEADING);
        rock.addActionListener(new MoveListener(playerEnvironment, "rock"));
        buttonPanel.add(rock);
        
        paper.setVerticalTextPosition(AbstractButton.CENTER);
        paper.setHorizontalTextPosition(AbstractButton.LEADING);
        paper.addActionListener(new MoveListener(playerEnvironment, "paper"));
        buttonPanel.add(paper);
        
        scissors.setVerticalTextPosition(AbstractButton.CENTER);
        scissors.setHorizontalTextPosition(AbstractButton.LEADING);
        scissors.addActionListener(new MoveListener(playerEnvironment, "scissors"));
        buttonPanel.add(scissors);   

        textBox.setVisible(true);
        buttonPanel.add(textBox);
		
        logInFrame.setResizable(false);
        logInFrame.setContentPane(logInPanel);
        logInFrame.pack();
        logInFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logInFrame.setVisible(true);
        logInFrame.setLocation(300, 300);
        
		buttonFrame.setResizable(false);
	    buttonFrame.setContentPane(buttonPanel);
		buttonFrame.pack();
		buttonFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buttonFrame.setVisible(false);
		buttonFrame.setLocation(300, 300);
		
		playerEnvironment.addInputListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		Object source = e.getSource();
		if (e.getPropertyName().equals("value")) {
			if (source == userNameField) {
				String message = "login," + userNameField.getValue() + ",xyz";
				playerEnvironment.send(message);
			}
		} else if (e.getPropertyName().equals("progress")) {
			String serverSaid = playerEnvironment.removeMessage();
			System.out.println("server said = " + serverSaid);

			if (serverSaid.equals("enjoy!")) {
				buttonFrame.setTitle((String) userNameField.getValue());
				buttonFrame.setVisible(true);
				logInFrame.setVisible(false);
			}
			else {
				textBox.setText(serverSaid);
				if (serverSaid.equals("pending") || serverSaid.equals("You lose") || serverSaid.equals("You Win!") || serverSaid.equals("It's a tie!")) {
					rock.setEnabled(false);
					paper.setEnabled(false);
					scissors.setEnabled(false);
					buttonFrame.pack();
				}
			}
		} else {
			System.out.println("property name = " + e.getPropertyName());
		}
	}
}

