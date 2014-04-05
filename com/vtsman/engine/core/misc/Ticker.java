package com.vtsman.engine.core.misc;

import java.util.*;

import com.vtsman.engine.core.Game;

public class Ticker implements Runnable {
	private HashSet<ITickable> tickables = new HashSet<ITickable>();
	private boolean alive = true;
	public int ticktime = 20;
	@Override
	public void run() {
		while (true) {
			long start = System.currentTimeMillis();
			synchronized (this) {
				if (!alive) {
					break;
				}
				runOnce();
			}
			if (System.currentTimeMillis() - start < ticktime) {
				try {
					Thread.sleep(ticktime - (System.currentTimeMillis() - start));
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public void runOnce(){
		for (ITickable t : tickables) {
			synchronized (t) {
				t.tick();
			}
		}
	}
	
	public synchronized void addEssentials(){
		tickables.add(Game.keyHandler);
	}
	
	public synchronized void kill() {
		alive = false;
	}

	public boolean isAlive() {
		return alive;
	}

	public synchronized void add(ITickable t) {
		tickables.add(t);
	}

	public synchronized void remove(ITickable t) {
		tickables.remove(t);
	}

	public synchronized void flush(){
		tickables = new HashSet<ITickable>();
	}
	
}
