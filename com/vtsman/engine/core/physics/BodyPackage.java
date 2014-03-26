package com.vtsman.engine.core.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class BodyPackage {
	public BodyDef bod;
	public FixtureDef fix;
	public Body body;
	public Fixture f;
	public BodyPackage(BodyDef body, FixtureDef fixture) {
		bod = body;
		fix = fixture;
	}

	public Body createBody(World w) {
		if (!isCreated()) {
			body = w.createBody(bod);
			f = body.createFixture(fix);
			//ret.setBullet(true);
		}
		return body;
	}

	public void destroyBody(World w) {
		w.destroyBody(body);
		body = null;
	}

	public boolean isCreated() {
		return body != null;
	}
}
