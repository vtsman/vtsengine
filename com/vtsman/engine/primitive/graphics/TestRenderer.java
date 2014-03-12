package com.vtsman.engine.primitive.graphics;

import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.physics.box2d.Body;
import com.vtsman.engine.core.graphics.IRenderer;
import com.vtsman.engine.core.graphics.RenderManager;
import com.vtsman.engine.core.graphics.RenderType;
import com.vtsman.engine.core.graphics.TextureRepo;

public class TestRenderer implements IRenderer{
	float[] vertices = {106.75f,16.749996f,89.25f,32.0f,83.0f,44.75f,84.25f,70.5f,80.5f,78.25f,77.25f,120.25f,62.5f,112.25f,52.75f,98.25f,49.25f,78.25f,28.75f,97.75f,33.75f,112.0f,19.500004f,109.0f,6.25f,109.25f,11.75f,98.5f,24.0f,90.0f,11.25f,78.75f,17.500004f,74.75f,30.125f,80.5f,33.25f,76.25f,13.375006f,64.5f,31.249996f,58.75f,37.0f,70.0f,49.5f,61.0f,47.0f,28.749996f,25.5f,13.5f,64.0f,9.25f,76.25001f,5.0f,88.5f,15.0f};
	private EarClippingTriangulator triangulator = new EarClippingTriangulator();
	@Override
	public void render(RenderManager rm) {
		PolygonRegion p = new PolygonRegion(new TextureRegion(TextureRepo.getTexture("tree")), vertices, triangulator.computeTriangles(vertices).toArray());
		PolygonSprite s = new PolygonSprite(p);
		
		s.draw(rm.polySpriteBatch);
	}

	@Override
	public RenderType getRenderType() {
		return RenderType.polySprite;
	}

	@Override
	public int getLayer() {
		return 0;
	}

	@Override
	public void setPosition(float x, float y) {
		
	}

	@Override
	public void setRotation(float rot) {
		
	}

	@Override
	public void bindToBody(Body b) {
		
	}

	public void setDimensions(int width, int height) {
		
	}

}
