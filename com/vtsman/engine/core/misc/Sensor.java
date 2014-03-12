package com.vtsman.engine.core.misc;

import java.util.ArrayList;

public abstract class Sensor implements ITickable{
	private ArrayList<ISubscribable> subbed = new ArrayList<ISubscribable>();
	
	public Sensor(Ticker t){
		t.add(this);
	}
	
	public abstract boolean triggered();
	
	public abstract String getEventID();
	
	public void subscribe(ISubscribable s){
		subbed.add(s);
	}
	
	public void unSubscribe(ISubscribable s){
		subbed.remove(s);
	}
	
	public void tick(){
		for(ISubscribable s : subbed){
			s.onEvent(getEventID());
		}
	}
}
