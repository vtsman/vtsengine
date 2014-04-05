package com.vtsman.engine.core.map;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.vtsman.engine.core.Game;
import com.vtsman.engine.core.graphics.IRenderer;
import com.vtsman.engine.core.misc.Entity;
import com.vtsman.engine.core.misc.ISubscriber;
import com.vtsman.engine.core.misc.ITickable;
import com.vtsman.engine.core.misc.Sensor;
import com.vtsman.engine.core.utils.IBoolExpr;
import com.vtsman.engine.gameObjects.entities.Player;

public class Map implements ITickable {
	final float timeStep = 1.0f / 120.f;
	final int velocityIterations = 6;
	final int positionIterations = 2;
	public World world = new World(new Vector2(0, -20), true);
	int height = Gdx.graphics.getHeight();
	int width = Gdx.graphics.getWidth();

	private List<Entity> entities = new ArrayList<Entity>();
	public ArrayList<Player> players = new ArrayList<Player>();
	public ArrayList<Fixture> playfix = new ArrayList<Fixture>();
	public HashMap<Fixture, String> fixName = new HashMap<Fixture, String>();
	public HashMap<Fixture, Object> fixData = new HashMap<Fixture, Object>();
	public HashMap<String, HashMap<Fixture, ISubscriber>> subbed = new HashMap<String, HashMap<Fixture, ISubscriber>>();
	public HashMap<String, Sensor> sensors = new HashMap<String, Sensor>();
	public HashMap<String, IBoolExpr> sensorsIBool = new HashMap<String, IBoolExpr>();

	public Map() {
		world.setContactListener(new MapContactListener(this));
	}

	public synchronized void addEntity(Entity e) {
		entities.add(e);
		/*
		 * if (e instanceof Player) { players.add((Player) e);
		 * playfix.add(((Player) e).s); }
		 */
	}

	public synchronized void removeEntity(Entity e) {
		if (entities.contains(e))
			entities.remove(e);
	}

	@Override
	public void tick() {
		world.step(timeStep, velocityIterations, positionIterations);
	}

	public void addObject(Object o) {
		if (o instanceof Entity) {
			((Entity) o).create(this);
			// addEntity((Entity) o);
		}
		if (o instanceof IRenderer) {
			Game.getRenderer().addRenderer((IRenderer) o);
		}
		if (o instanceof ITickable) {
			Game.getTicker().add((ITickable) o);
		}
	}

	public void addFixtureEvent(Fixture f, String name) {
		fixName.put(f, name);
	}

	public void addFixtureData(Fixture f, Object data) {
		fixData.put(f, data);
	}

	public void subscribeToFixtureEvent(String event, Fixture f,
			ISubscriber subber) {
		if (!subbed.containsKey(event)) {
			subbed.put(event, new HashMap<Fixture, ISubscriber>());
		}
		subbed.get(event).put(f, subber);
	}
}
