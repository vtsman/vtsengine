package com.vtsman.engine.core.misc;

import java.util.*;

public class Ticker implements Runnable {
	private HashSet<ITickable> tickables = new HashSet<ITickable>();
	private boolean alive = true;

	@Override
	public void run() {
		while (true) {
			long start = System.currentTimeMillis();
			synchronized (this) {
				if (!alive) {
					break;
				}
				for (ITickable t : tickables) {
					synchronized (t) {
						t.tick();
					}
				}
			}
			if (System.currentTimeMillis() - start < 20) {
				try {
					Thread.sleep(20 - (System.currentTimeMillis() - start));
				} catch (InterruptedException e) {
				}
			}
		}
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

}
