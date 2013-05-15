package com.h5n1.eventsys;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.h5n1.eventsys.events.CompanionEvent;
import com.h5n1.eventsys.events.CompanionEvent.CompanionEventType;
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
				CompanionEvent cpe = new CompanionEvent(CompanionEventType.DANGER_SITUATION, msg);
				cpe.setRecieverId(JsonRequester.getDeviceID());
				String s = JsonRequester.newEvent(cpe);
			}
		});
		final JTextArea area = new JTextArea();
		JButton getall = new JButton("Get All Events");
		getall.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<HashMap<String, String>> temp = JsonRequester.getAllEvents();
				for (HashMap<String, String> s : temp) {
					area.append(s.toString()+"\n");
//					for (Map.Entry<String, String> entry : s.entrySet()) {
//
//						area.append(entry.getKey() + " | " + entry.getValue() + "\n");
//					}
					//area.append("==================================\n");
				}
			}
		});
		frame.setLayout(new BorderLayout());
		frame.add(request, BorderLayout.NORTH);
		frame.add(send, BorderLayout.WEST);
		frame.add(getall, BorderLayout.EAST);
		frame.add((new JPanel()).add(area), BorderLayout.CENTER);
		
//		JsonRequester.getToken();
		frame.setVisible(true);
	}

}
