package com.vtsman.engine.primitive.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.vtsman.engine.core.graphics.IRenderer;
import com.vtsman.engine.core.graphics.RenderManager;
import com.vtsman.engine.core.graphics.RenderType;

public class PolyWire implements IRenderer {
	float[] verts;
	Color c;
	float x;
	float y;
	float rot;
	float minTrans;
	float maxTrans;
	int width;
	int innerWidth;
	int grad;
	int layer;

	public PolyWire(Vector2[] vertices, int gradientResolution, int width,
			int inWidth, float minTrans, float maxTrans, Color c, int layer) {
		verts = new float[vertices.length * 2];
		for (int i = 0; i < vertices.length; i++) {
			verts[i * 2] = vertices[i].x;
			verts[i *2 + 1] = vertices[i].y;
		}
		this.width = width;
		innerWidth = inWidth;
		this.maxTrans = maxTrans;
		this.minTrans = minTrans;
		this.c = c.cpy();
		this.grad = gradientResolution;
		this.layer = layer;
	}
	
	int w;
	@Override
	public void render(RenderManager rm) {
		ShapeRenderer r = rm.sr;
		for (int i = grad; i > 0; i--) {
			c.a = 0.2f;//minTrans + (float)(maxTrans - minTrans) / (float)grad * (float)(grad - i);
			w = (int)(innerWidth + (float)(width - innerWidth) / (float)grad * i);
			System.out.println(c.a + ", " + w);
			Gdx.gl10.glLineWidth(w);
			r.begin(ShapeType.Line);
			r.translate(x, y, 0);
			r.rotate(0,0, 1, rot);
			r.setColor(c);
			r.polygon(verts);
			r.end();
		}
	}

	@Override
	public RenderType getRenderType() {
		return RenderType.other;
	}

	@Override
	public int getLayer() {
		return 0;
	}

	@Override
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void setRotation(float rot) {
		this.rot = rot;
	}

	@Override
	public void bindToBody(Body b) {
		// TODO Auto-generated method stub

	}

}
