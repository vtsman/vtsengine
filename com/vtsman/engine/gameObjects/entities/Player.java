package com.vtsman.engine.gameObjects.entities;

import java.awt.Rectangle;
import java.util.HashMap;

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
import com.vtsman.engine.core.physics.BodyPackage;
import com.vtsman.engine.primitive.graphics.CombinedRenderer;
import com.vtsman.engine.primitive.graphics.RenderTexture;
import com.vtsman.engine.primitive.physics.BodyMaker;

public class Player extends Entity implements ISubscriber {
	CombinedRenderer r = new CombinedRenderer(5);
	RenderTexture renderer;
	PolygonShape sensor;
	FixtureDef f;
	public Fixture s;
	public int onGround = 0;
	int charge = 20;
	RenderTexture useIcon;
	public boolean isUsing = false;
	public Player(Vector2 position, String spriteName) {
		super(position);
		useIcon = new RenderTexture(TextureRepo.getTexture("usable"), 5);
		useIcon.offsetBody(0, 64);
		this.body = BodyMaker.makeRectangle(32, 64, position.x, position.y,
				.9f, 0, 1, BodyType.DynamicBody);
		sensor = new PolygonShape();
		sensor.setAsBox(28f / 120f, 4f / 120f, new Vector2(0f / 120f,
				-64f / 120f), 0);
		f = new FixtureDef();
		f.shape = sensor;
		f.friction = .9f;
		f.isSensor = true;
		renderer = new RenderTexture(TextureRepo.getTexture(spriteName), 5);
		renderer.setDimensions(32, 64);
		r.addRenderer(renderer);
		Game.keyHandler.subscribe(this, "up");
		Game.keyHandler.subscribe(this, "down");
		Game.keyHandler.subscribe(this, "left");
		Game.keyHandler.subscribe(this, "right");
		Game.keyHandler.subscribe(this, "jump");
		Game.keyHandler.subscribe(this, "use");
	}

	@Override
	public void create(Map parent) {
		super.create(parent);
		renderer.bindToBody(this.getBody());
		s = this.body.body.createFixture(f);
		s.setUserData(this);
		this.body.body.setFixedRotation(true);
		parent.addFixtureEvent(this.body.f, "player");
		parent.addFixtureData(this.body.f, this);
		parent.subscribeToFixtureEvent("usable", this.body.f, this);
		useIcon.bindToBody(this.body.body);
		parent.players.add(this);
		parent.playfix.add(this.s);
	}

	@Override
	public void tick() {
		if((!Game.keyHandler.isButtonPressed("jump")) && onGround == 0){
			charge = 0;
		}
		
		if(!Game.keyHandler.isButtonPressed("use")){
			isUsing = false;
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
		return this.body.body;
	}

	@Override
	public IRenderer getRenderer() {
		return r;
	}

	@Override
	public BodyPackage getBodyPackage() {
		return this.body;
	}

	@Override
	public Vector2 getPos() {
		return this.body.body.getPosition();
	}

	@Override
	public void harm(DamageType dt, int intesity) {
		health -= intesity;
	}

	@Override
	public void onEvent(String event, Object ... args) {
		if (event.equals("right")) {
			getBody().applyForceToCenter(new Vector2(10, 0), true);
		}
		if (event.equals("left")) {
			getBody().applyForceToCenter(new Vector2(-10, 0), true);
		}
		if (event.equals("jump")) {
			if (onGround > 0) {
				getBody().applyForceToCenter(new Vector2(0, 50), true);
				charge = 20;
			} else if (charge > 0) {
				getBody().applyForceToCenter(new Vector2(0, 30), true);
				charge--;
			}
		}
		if(event.equals("startusable")){
			r.addRenderer(useIcon);
		}
		if(event.equals("endusable")){
			r.removeRenderer(useIcon);
		}
		if(event.equals("use")){
			isUsing = true;
		}
	}
}
