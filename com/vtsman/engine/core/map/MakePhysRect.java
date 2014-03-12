package com.vtsman.engine.core.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.vtsman.engine.core.graphics.TextureRepo;
import com.vtsman.engine.primitive.graphics.RenderTexture;
import com.vtsman.engine.primitive.physics.BodyMaker;

public class MakePhysRect implements ObjectConstructer {
	BodyType bt;

	public MakePhysRect(BodyType b) {
		bt = b;
	}

	int x = 0;
	int y = 0;
	int width = 1;
	int height = 1;
	int layer = 0;
	float a = 1;

	int r = 0;
	int g = 0;
	int b = 0;

	Color c = null;
	float rot = 0;
	float fric = 0.1f;
	float density = 1f;
	float bounce = 0f;
	String texture;
	boolean fixedRot = false;

	private void reset() {
		x = 0;
		y = 0;
		width = 1;
		height = 1;
		layer = 0;
		a = 1;

		r = 0;
		g = 0;
		b = 0;

		c = null;
		rot = 0;
		fric = 0;
		texture = null;
	}

	@Override
	public void addArg(String varName, Object object) {

		if (varName.equals("x")) {
			x = (Integer) object;
		}
		if (varName.equals("y")) {
			y = (Integer) object;
		}
		if (varName.equals("height")) {
			height = (Integer) object;
		}
		if (varName.equals("width")) {
			width = (Integer) object;
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
		if (varName.equals("rotation")) {
			rot = ((Integer) object).floatValue();
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
		Body b = BodyMaker.makeRectangle(width, height, x, y, fric, bounce, density, bt)
				.getBody(m.world);
		if (fixedRot) {
			System.out.println("Here");
			b.setFixedRotation(true);
		}
		b.setTransform(b.getPosition(), rot);
		RenderTexture out = new RenderTexture(TextureRepo.getTexture(texture),
				layer);
		out.setDimensions(width, height);
		out.setAlpha(a);
		out.bindToBody(b);
		if (c != null)
			out.setColor(c);
		reset();
		return out;
	}

}
