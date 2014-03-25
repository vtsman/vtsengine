package com.vtsman.engine.core.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.vtsman.engine.core.graphics.TextureRepo;
import com.vtsman.engine.primitive.graphics.RenderTexture;
import com.vtsman.engine.primitive.physics.BodyMaker;

public class MakePhysCircle implements ObjectConstructer {
	BodyType bt;

	public MakePhysCircle(BodyType b) {
		bt = b;
	}

	int radius = 0;
	int y = 0;
	int x = 0;
	int layer = 0;
	float a = 1;

	int r = 0;
	int g = 0;
	int b = 0;

	Color c = null;
	float fric = 0.1f;
	float density = 1f;
	float bounce = 0f;
	String texture;
	boolean fixedRot = false;

	private void reset() {
		radius = 1;
		x = 0;
		y = 0;
		layer = 0;
		a = 1;

		r = 0;
		g = 0;
		b = 0;

		c = null;
		fric = 0;
		texture = null;
	}

	@Override
	public void addArg(String varName, Object object) {

		if (varName.equals("radius")) {
			radius = (Integer) object;
		}
		if (varName.equals("diameter")) {
			radius = (Integer) object / 2;
		}
		if (varName.equals("x")) {
			x = (Integer) object;
		}
		if (varName.equals("y")) {
			y = (Integer) object;
		}
		if (varName.equals("layer")) {
			layer = (Integer) object;
		}
		if (varName.equals("alpha")) {
			a = (Float) object;
			if (c != null) {
				c.a = a;
			}
		}
		if (varName.equals("r")) {
			if (c == null) {
				c = new Color();
				c.a = a;
			}
			c.r = (Float) object;
		}
		if (varName.equals("g")) {
			if (c == null) {
				c = new Color();
				c.a = a;
			}
			c.g = (Float) object;
		}
		if (varName.equals("b")) {
			if (c == null) {
				c = new Color();
				c.a = a;
			}
			c.b = (Float) object;
		}
		if (varName.equals("friction")) {
			fric = ((Float) object).floatValue();
		}
		if (varName.equals("density")) {
			density = ((Float) object).floatValue();
		}
		if (varName.equals("bounce")) {
			bounce = ((Float) object).floatValue();
		}
		if (varName.equals("texture")) {

			texture = (String) object;
		}
		if (varName.equals("fixedRotation")) {

			fixedRot = (Boolean) object;
		}
	}

	@Override
	public Object make(Map m) {
		Body b = BodyMaker.makeCircle(radius, x, y, fric, bounce, density, bt)
				.getBody(m.world);
		if (fixedRot) {
			b.setFixedRotation(true);
		}
		b.setTransform(b.getPosition(), 0);
		RenderTexture out = new RenderTexture(TextureRepo.getTexture(texture),
				layer);
		out.setDimensions(radius * 2, radius * 2);
		out.setAlpha(a);
		out.bindToBody(b);
		if (c != null)
			out.setColor(c);
		reset();
		return out;
	}

}
