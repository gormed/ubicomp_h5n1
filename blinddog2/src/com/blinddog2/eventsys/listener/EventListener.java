package com.blinddog2.eventsys.listener;

public interface EventListener<T> {
	public void fired(T event);
}