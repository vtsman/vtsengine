package com.vtsman.engine.core.map;

import java.util.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.vtsman.engine.core.Game;
import com.vtsman.engine.core.graphics.IRenderer;
import com.vtsman.engine.core.misc.Entity;
import com.vtsman.engine.core.misc.ITickable;
import com.vtsman.engine.gameObjects.entities.Player;

public class Map implements ITickable {
	final float timeStep = 1.0f / 120.f;
	final int velocityIterations = 6;
	final int positionIterations = 2;
	public World world = new World(new Vector2(0, -20), true);

	private List<Entity> entities = new ArrayList<Entity>();
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Fixture> playfix = new ArrayList<Fixture>();

	Map() {
		world.setContactListener(new ContactListener() {

			@Override
			public void beginContact(Contact contact) {
				if(contact.getFixtureA().getBody() == contact.getFixtureB().getBody())
					return;
				if (playfix.contains(contact.getFixtureA())) {
					players.get(playfix.indexOf(contact.getFixtureA())).onGround++;
				}
				if (playfix.contains(contact.getFixtureB())) {
					players.get(playfix.indexOf(contact.getFixtureB())).onGround++;
				}
			}

			@Override
			public void endContact(Contact contact) {
				if(contact.getFixtureA().getBody() == contact.getFixtureB().getBody())
					return;
				if (playfix.contains(contact.getFixtureA())) {
					players.get(playfix.indexOf(contact.getFixtureA())).onGround--;
				}
				if (playfix.contains(contact.getFixtureB())) {
					players.get(playfix.indexOf(contact.getFixtureB())).onGround--;
				}
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				// TODO Auto-generated method stub

			}
		});
	}

	public synchronized void addEntity(Entity e) {
		entities.add(e);
		if(e instanceof Player){
			players.add((Player) e);
			playfix.add(((Player)e).s);
		}
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
			addEntity((Entity) o);
			System.out.println(o.getClass().getName());
			// ((Entity)o).setPosition(((Entity)o).spawnPos);
		}
		if (o instanceof IRenderer) {
			Game.getRenderer().addRenderer((IRenderer) o);
		}
	}
}
