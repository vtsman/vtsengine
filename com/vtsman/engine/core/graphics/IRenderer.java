package com.vtsman.engine.core.graphics;

import com.badlogic.gdx.physics.box2d.Body;

public interface IRenderer {
	public void render(RenderManager rm);
	public RenderType getRenderType();
	public int getLayer();
	public void setPosition(float x, float y);
	public void setRotation(float rot);
	public void bindToBody(Body b);
}
