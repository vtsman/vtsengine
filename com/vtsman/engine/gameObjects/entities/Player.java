package com.vtsman.engine.gameObjects.entities;

import java.awt.Rectangle;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
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
	public Player(Vector2 position, String spriteName) {
		super(position);
		this.body = BodyMaker.makeRectangle(32, 64, position.x, position.y, .5f, 0, 1, BodyType.DynamicBody);
		renderer = new RenderTexture(TextureRepo.getTexture(spriteName), 5);
		renderer.setDimensions(32, 64);
		KeyHandler.subscribe(this, "up");
		KeyHandler.subscribe(this, "down");
		KeyHandler.subscribe(this, "left");
		KeyHandler.subscribe(this, "right");
		KeyHandler.subscribe(this, "jump");
	}

	@Override
	public void create(Map parent){
		super.create(parent);
		renderer.bindToBody(this.getBody());
		this.body.getBody(null).setFixedRotation(true);
	}
	@Override
	public void tick() {
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
			getBody().applyForceToCenter(new Vector2(0, 20), true);
		}
	}

}