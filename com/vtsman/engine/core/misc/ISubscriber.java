package com.vtsman.engine.core.misc;

public interface ISubscriber {
	public void onEvent(String event, Object ... args);
}
