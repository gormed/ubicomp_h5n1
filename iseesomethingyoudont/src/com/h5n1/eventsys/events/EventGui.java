package com.h5n1.eventsys.events;


import com.ubicomp.iseesomethingyoudont.ControlActivity;

import android.app.ActionBar;




@SuppressWarnings("unused")
public class EventGui {
	private static EventGui instance;

	private EventGui() {

	}

	public static EventGui getInstance() {
		if (instance != null) {
			return instance;
		}
		return instance = new EventGui();
	}

	// =================================================

	
}
