package com.h5n1.eventsys;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.h5n1.eventsys.events.GPSEvent;
import com.h5n1.eventsys.events.GPSEvent.GPSEventType;

public class TestEventsys extends JFrame {

	EventSystem eventSystem = EventSystem.getInstance();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6840766590790158861L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestEventsys frame = new TestEventsys();
		frame.setTitle("Test Events");
		frame.setSize(300, 200);
		final JTextField request = new JTextField();
		JButton send = new JButton("Send");
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String msg = request.getText();
				String[] split = msg.split("[,]");
				GPSEvent gps = new GPSEvent(GPSEventType.NO_SIGNAL, 1234, 5678);
				gps.setRecieverId(JsonRequester.getDeviceID());
				JsonRequester.newEvent(gps);
			}
		});
		JButton getall = new JButton("Get All Events");
		getall.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JsonRequester.getAllEvents();
				
			}
		});
		JTextArea area = new JTextArea();
		frame.setLayout(new BorderLayout());
		frame.add(request, BorderLayout.NORTH);
		frame.add(send, BorderLayout.WEST);
		frame.add(getall, BorderLayout.EAST);
		frame.add((new JPanel()).add(area), BorderLayout.CENTER);
		
		JsonRequester.getToken();
		frame.setVisible(true);
	}

}
