package com.vtsman.engine.primitive.graphics;

import java.util.Arrays;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.physics.box2d.Body;
import com.vtsman.engine.core.graphics.IRenderer;
import com.vtsman.engine.core.graphics.RenderManager;
import com.vtsman.engine.core.graphics.RenderType;
import com.vtsman.engine.core.graphics.TextureRepo;

public class TexturedPolygon implements IRenderer {
	PolygonSprite ps;
	Body bound;
	int layer;
	static final EarClippingTriangulator tr = new EarClippingTriangulator();
	public TexturedPolygon(float[] verts, Texture t, int layer) {
		PolygonRegion polyReg = new PolygonRegion(new TextureRegion(
				t), verts, tr.computeTriangles(verts).items);
		ps = new PolygonSprite(polyReg);
		ps.setOrigin(0, 0);
		this.layer = layer;
	}

	@Override
	public void render(RenderManager rm) {
		if(bound != null){
			setPosition(bound.getPosition().x * 60, bound.getPosition().y * 60);
			setRotation((float) Math.toDegrees(bound.getAngle()));
		}
		ps.draw(rm.polySpriteBatch);
	}

	@Override
	public RenderType getRenderType() {
		return RenderType.polySprite;
	}

	@Override
	public int getLayer() {
		return layer;
	}

	@Override
	public void setPosition(float x, float y) {
		ps.setPosition(x, y);
	}

	@Override
	public void setRotation(float rot) {
		ps.setRotation(rot);
	}

	public void setColor(Color c){
		ps.setColor(c);
	}
	
	public void setScale(float x, float y){
		ps.setScale(x, y);
	}
	
	@Override
	public void bindToBody(Body b) {
		bound = b;
	}

}
