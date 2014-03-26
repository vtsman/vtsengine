package com.vtsman.engine.primitive.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.vtsman.engine.core.graphics.IRenderer;
import com.vtsman.engine.core.graphics.RenderManager;
import com.vtsman.engine.core.graphics.RenderType;

public class RenderTexture implements IRenderer {
	Sprite s;
	int layer;
	Body bod = null;
	public boolean hold = true;
	int bX = 0;
	int bY = 0;
	public RenderTexture(Texture t, int layer) {
		s = new Sprite(t);
		this.layer = layer;
	}

	public RenderTexture(TextureRegion r, int layer) {
		s = new Sprite(r);
		this.layer = layer;
	}

	public void setDimensions(int width, int height) {
		if (s != null) {
			s.setSize(width, height);
			setOrigin(width / 2, height / 2);
		}
	}

	public void setColor(Color c) {
		if (s != null)
			s.setColor(c);
	}

	public void setPosition(float x, float y) {
		if (s != null)
			s.setPosition(x, y);
	}

	public void setOrigin(float x, float y) {
		if (s != null)
			s.setOrigin(x, y);
	}

	public void setRotation(float rot) {
		if (s != null)
			s.setRotation(360 - rot);
	}

	public void setAlpha(float alpha) {
		if (s != null)
			s.setAlpha(alpha);
	}
	@Override
	public void render(RenderManager rm) {
		if (s == null)
			return;
		if (bod != null) {
			s.setPosition(bod.getPosition().x * 60 - s.getWidth() / 2 + bX, bod.getPosition().y * 60 - s.getHeight() / 2 + bY);
			s.setRotation((float) Math.toDegrees(bod.getAngle()));
		}
		s.draw(rm.spriteBatch);
	}

	@Override
	public RenderType getRenderType() {
		return RenderType.sprite;
	}

	@Override
	public int getLayer() {
		return layer;
	}

	@Override
	public void bindToBody(Body b) {
		this.bod = b;
	}
	
	public void offsetBody(int x, int y){
		bX = x;
		bY = y;
	}
}
