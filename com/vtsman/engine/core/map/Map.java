package com.vtsman.engine.core.map;

import java.util.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.vtsman.engine.core.Game;
import com.vtsman.engine.core.graphics.IRenderer;
import com.vtsman.engine.core.misc.Entity;
import com.vtsman.engine.core.misc.ITickable;

public class Map implements ITickable {
	final float timeStep = 1.0f / 120.f;
	final int velocityIterations = 6;
	final int positionIterations = 2;
	public World world = new World(new Vector2(0, -20), true);

	private List<Entity> entities = new ArrayList<Entity>();

	public synchronized void addEntity(Entity e) {
		entities.add(e);
	}

	public synchronized void removeEntity(Entity e) {
		if (entities.contains(e))
			entities.remove(e);
	}

	@Override
	public void tick() {
		world.step(timeStep, velocityIterations, positionIterations);
	}
	
	public void addObject(Object o){
		if(o instanceof Entity){
			((Entity)o).create(this);
			System.out.println(o.getClass().getName());
		//	((Entity)o).setPosition(((Entity)o).spawnPos);
		}
		if(o instanceof IRenderer){
			Game.getRenderer().addRenderer((IRenderer) o);
		}
	}
}
