package com.h5n1.eventsys.listener;

public interface EventListener<T> {
	public void fired(T event);
}