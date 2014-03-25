package com.vtsman.engine.gameObjects.entities;

import java.awt.Rectangle;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.vtsman.engine.core.Game;
import com.vtsman.engine.core.graphics.IRenderer;
import com.vtsman.engine.core.graphics.TextureRepo;
import com.vtsman.engine.core.map.Map;
import com.vtsman.engine.core.misc.DamageType;
import com.vtsman.engine.core.misc.Entity;
import com.vtsman.engine.core.misc.ISubscriber;
import com.vtsman.engine.core.misc.KeyHandler;
import com.vtsman.engine.core.physics.BodyPackage;
import com.vtsman.engine.primitive.graphics.RenderTexture;
import com.vtsman.engine.primitive.physics.BodyMaker;

public class Player extends Entity implements ISubscriber {
	RenderTexture renderer;
	PolygonShape sensor;
	FixtureDef f;
	public Fixture s;
	public int onGround = 0;
	int charge = 20;

	public Player(Vector2 position, String spriteName) {
		super(position);
		this.body = BodyMaker.makeRectangle(32, 64, position.x, position.y,
				1f, 0, 1, BodyType.DynamicBody);
		sensor = new PolygonShape();
		sensor.setAsBox(28f / 120f, 4f / 120f, new Vector2(0f / 120f,
				-64f / 120f), 0);
		f = new FixtureDef();
		f.shape = sensor;
		f.friction = 1f;
		f.isSensor = true;
		renderer = new RenderTexture(TextureRepo.getTexture(spriteName), 5);
		renderer.setDimensions(32, 64);
		Game.keyHandler.subscribe(this, "up");
		Game.keyHandler.subscribe(this, "down");
		Game.keyHandler.subscribe(this, "left");
		Game.keyHandler.subscribe(this, "right");
		Game.keyHandler.subscribe(this, "jump");
	}

	@Override
	public void create(Map parent) {
		super.create(parent);
		renderer.bindToBody(this.getBody());
		s = this.body.getBody(null).createFixture(f);
		System.out.println(s);
		this.body.getBody(null).setFixedRotation(true);
	}

	@Override
	public void tick() {
		if(!Game.keyHandler.isButtonPressed("jump") && onGround == 0){
			charge = 0;
		}
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

	@Override
	public float getRot() {
		return 0;
	}

	@Override
	public Body getBody() {
		return this.body.getBody(null);
	}

	@Override
	public IRenderer getRenderer() {
		return renderer;
	}

	@Override
	protected BodyPackage getBodyPackage() {
		return this.body;
	}

	@Override
	public Vector2 getPos() {
		return this.body.getBody(null).getPosition();
	}

	@Override
	public void harm(DamageType dt, int intesity) {
		health -= intesity;
	}

	@Override
	public void onEvent(String event) {
		if (event == "right") {
			getBody().applyForceToCenter(new Vector2(10, 0), true);
		}
		if (event == "left") {
			getBody().applyForceToCenter(new Vector2(-10, 0), true);
		}
		if (event == "jump") {
			if (onGround > 0) {
				getBody().applyForceToCenter(new Vector2(0, 50), true);
				charge = 20;
			} else if (charge > 0) {
				getBody().applyForceToCenter(new Vector2(0, 30), true);
				charge--;
			}
		}
	}
}
