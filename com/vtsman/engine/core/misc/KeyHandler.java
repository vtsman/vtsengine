package com.vtsman.engine.core.misc;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;

public class KeyHandler implements ITickable {
	HashMap<String, Integer> keys = new HashMap<String, Integer>();
	HashMap<ISubscribable, String> subbed = new HashMap<ISubscribable, String>();
	@Override
	public void tick() {
		for(Entry<ISubscribable, String> a : subbed.entrySet()){
			if(Gdx.input.isKeyPressed(keys.get(a.getValue()).intValue()))
			((ISubscribable)a.getKey()).onEvent(a.getValue());
		}
	}
	
	public void subscribe(ISubscribable s, String key){
		subbed.put(s, key);
	}
	
	public void addKey(String name, int key){
		keys.put(name, key);
	}

}
