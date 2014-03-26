package com.vtsman.engine.core.map;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MapContactListener implements ContactListener{
	Map m;
	public MapContactListener(Map m){
		this.m = m;
	}
	
	@Override
	public void beginContact(Contact contact) {
		if (contact.getFixtureA().getBody() == contact.getFixtureB()
				.getBody())
			return;
		if (m.playfix.contains(contact.getFixtureA())) {
			if(!contact.getFixtureB().isSensor())
				m.players.get(m.playfix.indexOf(contact.getFixtureA())).onGround++;
		}
		if (m.playfix.contains(contact.getFixtureB())) {
			if(!contact.getFixtureA().isSensor())
				m.players.get(m.playfix.indexOf(contact.getFixtureB())).onGround++;
		}
		if (m.fixName.containsKey(contact.getFixtureA())) {
			String event = m.fixName.get(contact.getFixtureA());
			if (m.subbed.containsKey(event)) {
				if (m.subbed.get(event)
						.containsKey(contact.getFixtureB())) {
					m.subbed.get(event)
							.get(contact.getFixtureB())
							.onEvent("start" + event,
									contact.getFixtureB(), m.fixData.get(contact.getFixtureA()));
				}
			}
		}

		if (m.fixName.containsKey(contact.getFixtureB())) {
			String event = m.fixName.get(contact.getFixtureB());
			if (m.subbed.containsKey(event)) {
				if (m.subbed.get(event)
						.containsKey(contact.getFixtureA())) {
					m.subbed.get(event)
							.get(contact.getFixtureA())
							.onEvent("start" + event,
									contact.getFixtureA(), m.fixData.get(contact.getFixtureB()));
				}
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		if (contact.getFixtureA().getBody() == contact.getFixtureB()
				.getBody())
			return;
		if (m.playfix.contains(contact.getFixtureA())) {
			if(!contact.getFixtureB().isSensor())
				m.players.get(m.playfix.indexOf(contact.getFixtureA())).onGround--;
		}
		if (m.playfix.contains(contact.getFixtureB())) {
			if(!contact.getFixtureA().isSensor())
				m.players.get(m.playfix.indexOf(contact.getFixtureB())).onGround--;
		}
		if (m.fixName.containsKey(contact.getFixtureA())) {
			String event = m.fixName.get(contact.getFixtureA());
			if (m.subbed.containsKey(event)) {
				if (m.subbed.get(event)
						.containsKey(contact.getFixtureB())) {
					m.subbed.get(event)
							.get(contact.getFixtureB())
							.onEvent("end" + event,
									contact.getFixtureB(), m.fixData.get(contact.getFixtureA()));
				}
			}
		}

		if (m.fixName.containsKey(contact.getFixtureB())) {
			String event = m.fixName.get(contact.getFixtureB());
			if (m.subbed.containsKey(event)) {
				if (m.subbed.get(event)
						.containsKey(contact.getFixtureA())) {
					m.subbed.get(event)
							.get(contact.getFixtureA())
							.onEvent("end" + event,
									contact.getFixtureA(), m.fixData.get(contact.getFixtureB()));
				}
			}
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {


	}
}