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
	public Bullet(Vector2 pos, Vector2 dimensions, int angle, Map owner, float velocity, String texture, int layer) {
		super(pos);
		this.body = BodyMaker.makeRectangle(dimensions.x, dimensions.y, pos.x, pos.y, 0, 0, 1, BodyType.DynamicBody);
		this.body.getBody(owner.world).setGravityScale(0);
		this.body.getBody(null).setTransform(pos.x, pos.y, angle);
		this.body.getBody(null).applyForceToCenter(new Vector2(velocity, 0), true);
		this.renderer = new RenderTexture(TextureRepo.getTexture(texture), layer);
		this.renderer.setDimensions((int)dimensions.x, (int)dimensions.y);
		this.renderer.bindToBody(this.body.getBody(null));
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
		return this.renderer;
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
