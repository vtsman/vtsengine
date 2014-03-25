package com.vtsman.engine.core.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class KeyHandler implements ITickable {
	static HashMap<String, Integer> keys = new HashMap<String, Integer>();
	static HashMap<ISubscriber, ArrayList<String>> subbed = new HashMap<ISubscriber, ArrayList<String>>();

	static {
		System.out.println("Binding Keys");
		keys.put("up", Keys.W);
		keys.put("down", Keys.S);
		keys.put("left", Keys.A);
		keys.put("right", Keys.D);
		keys.put("jump", Keys.SPACE);
		keys.put("use", Keys.F);

		keys.put("dev", Keys.BACKSLASH);
	}

	@Override
	public synchronized void tick() {
		for (Entry<ISubscriber, ArrayList<String>> a : subbed.entrySet()) {
			for (String s : a.getValue()) {
				if (Gdx.input.isKeyPressed(keys.get(s).intValue())) {
					((ISubscriber) a.getKey()).onEvent(s);
				}
			}
		}
	}

	public synchronized void subscribe(ISubscriber s, String key) {
		if (!subbed.containsKey(s)) {
			subbed.put(s, new ArrayList<String>());
		}
		subbed.get(s).add(key);
	}

	static synchronized public void addKey(String name, int key) {
		keys.put(name, key);
	}

	public synchronized void remove(ISubscriber s) {
		subbed.remove(s);
	}

	public boolean isButtonPressed(String s) {
		return Gdx.input.isButtonPressed(keys.get(s));
	}

}
