package com.vtsman.engine.core.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.World;
import com.vtsman.engine.core.graphics.TextureRepo;
import com.vtsman.engine.primitive.graphics.RenderTexture;

public class MakeDecor implements ObjectConstructor {
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
	String texture;
	
	private void reset(){
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
		if (varName.equals("texture")) {
			
			texture = (String) object;
		}
	}

	@Override
	public Object make(Map m) {
		RenderTexture out = new RenderTexture(TextureRepo.getTexture(texture),
				layer);
		out.setRotation(rot);
		out.setDimensions(width, height);
		out.setPosition(x, y);
		out.setAlpha(a);
		if (c != null)
			out.setColor(c);
		reset();
		return out;
	}

}
