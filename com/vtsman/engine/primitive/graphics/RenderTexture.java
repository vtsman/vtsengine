package com.vtsman.engine.primitive.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.vtsman.engine.core.graphics.IRenderer;
import com.vtsman.engine.core.graphics.RenderManager;
import com.vtsman.engine.core.graphics.RenderType;

public class RenderTexture implements IRenderer {
	Sprite s;
	int layer;
	Body bod = null;
	public boolean hold = true;

	public RenderTexture(Texture t, int layer) {
		s = new Sprite(t);
		this.layer = layer;
	}

	public RenderTexture(TextureRegion r, int layer) {
		s = new Sprite(r);
		hold();
		this.layer = layer;
	}

	private void hold() {
		while (true) {
			synchronized (this) {
				if (!hold)
					return;
			}
		}
	}

	public synchronized void setDimensions(int width, int height) {
		if (s != null) {
			s.setSize(width, height);
			setOrigin(width / 2, height / 2);
		}
	}

	public synchronized void setColor(Color c) {
		if (s != null)
			s.setColor(c);
	}

	public synchronized void setPosition(float x, float y) {
		if (s != null)
			s.setPosition(x, y);
	}

	public synchronized void setOrigin(float x, float y) {
		if (s != null)
			s.setOrigin(x, y);
	}

	public synchronized void setRotation(float rot) {
		if (s != null)
			s.setRotation(rot);
	}

	public synchronized void setAlpha(float alpha) {
		if (s != null)
			s.setAlpha(alpha);
	}
	@Override
	public void render(RenderManager rm) {
		if (s == null)
			return;
		if (bod != null) {
			s.setPosition(bod.getPosition().x * 60 - s.getWidth() / 2, bod.getPosition().y * 60 - s.getHeight() / 2);
			s.setRotation(bod.getAngle());
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
}
