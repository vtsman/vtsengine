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
import com.vtsman.engine.core.physics.BodyPackage;
import com.vtsman.engine.primitive.graphics.RenderTexture;
import com.vtsman.engine.primitive.physics.BodyMaker;

public class Bullet extends Entity{
	RenderTexture renderer;
	int angle;
	Vector2 pos;
	float velocity;
	public Bullet(Vector2 pos, Vector2 dimensions, int angle, float velocity, String texture, int layer) {
		super(pos);
		this.angle = angle;
		this.pos = pos;
		this.velocity = velocity;
		this.body = BodyMaker.makeRectangle(dimensions.x, dimensions.y, pos.x, pos.y, 0, 0, 1, BodyType.DynamicBody);
		this.renderer = new RenderTexture(TextureRepo.getTexture(texture), layer);
		this.renderer.setDimensions((int)dimensions.x, (int)dimensions.y);
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
		return this.body.getBody(null).getAngle();
	}

	@Override
	public Body getBody() {
		return this.body.getBody(null);
	}

	@Override
	public IRenderer getRenderer() {
		System.out.println("here");
		return this.renderer;
	}
	
	@Override
	public void create(Map parent){
		super.create(parent);
		this.body.getBody(null).setGravityScale(0);
		this.body.getBody(null).setTransform(pos.x, pos.y, (float) Math.toRadians(angle));
		this.body.getBody(null).applyForceToCenter(new Vector2(velocity, 0), true);
		this.body.getBody(null).setFixedRotation(true);
		this.renderer.bindToBody(this.body.getBody(null));
	}

	@Override
	protected BodyPackage getBodyPackage() {
		return this.body;
	}

	@Override
	public void harm(DamageType dt, int intesity) {
		
	}

	@Override
	public Vector2 getPos() {
		return this.body.getBody(null).getPosition();
	}

}
