package com.vtsman.engine.core.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class BodyPackage {
	public BodyDef bod;
	public FixtureDef fix;
	private Body ret;

	public BodyPackage(BodyDef body, FixtureDef fixture) {
		bod = body;
		fix = fixture;
	}

	public Body getBody(World w) {
		if (!isCreated()) {
			ret = w.createBody(bod);
			ret.createFixture(fix);
			ret.setBullet(true);
		}
		return ret;
	}

	public void destroyBody(World w) {
		w.destroyBody(ret);
		ret = null;
	}

	public boolean isCreated() {
		return ret != null;
	}
}
