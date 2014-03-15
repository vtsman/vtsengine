package com.vtsman.engine.core.misc;

import java.util.ArrayList;

public abstract class Sensor implements ITickable{
	private ArrayList<ISubscriber> subbed = new ArrayList<ISubscriber>();
	
	public Sensor(Ticker t){
		t.add(this);
	}
	
	public abstract boolean triggered();
	
	public abstract String getEventID();
	
	public void subscribe(ISubscriber s){
		subbed.add(s);
	}
	
	public void unSubscribe(ISubscriber s){
		subbed.remove(s);
	}
	
	public void tick(){
		for(ISubscriber s : subbed){
			s.onEvent(getEventID());
		}
	}
}
