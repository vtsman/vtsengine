package com.vtsman.engine.core.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.vtsman.engine.core.graphics.TextureRepo;
import com.vtsman.engine.core.utils.*;
import com.vtsman.engine.primitive.graphics.TexturedPolygon;

public class MakeDecorPoly implements ObjectConstructor {
	float[] verts;
	int layer;
	int x;
	int y;
	int rot;
	Texture t;
	float r = 1.0f;
	float g = 1.0f;
	float b = 1.0f;
	float a = 1.0f;

	private void reset(){
		verts = null;
		layer = 0;
		x = 0;
		y = 0;
		rot = 0;
		t = null;
		r = 1.0f;
		g = 1.0f;
		b = 1.0f;
		a = 1.0f;
	}
	
	@Override
	public void addArg(String varName, Object object) {
		if (varName.equals("verts")) {
			Object[] objArr = ((ArrayWrapper)object).getArr();
			verts = new float[objArr.length];
			for(int i = 0; i < verts.length; i++){
				verts[i] = ((Float)objArr[i]).floatValue();
			}
		}
		if (varName.equals("x")) {
			x = ((Integer)object).intValue();
		}
		if (varName.equals("y")) {
			y = ((Integer)object).intValue();
		}
		if(varName.equals("layer")){
			layer = ((Integer)object).intValue();
		}
		if (varName.equals("angle")) {
			rot = ((Integer)object).intValue();
		}
		if (varName.equals("texture")) {
			t = TextureRepo.getTexture((String)object);
		}
		if (varName.equals("r")) {
			r = ((Float)object).floatValue();
		}
		if (varName.equals("g")) {
			g = ((Float)object).floatValue();
		}
		if (varName.equals("b")) {
			b = ((Float)object).floatValue();
		}
		if (varName.equals("alpha")) {
			a = ((Float)object).floatValue();
		}
	}

	@Override
	public Object make(Map m) {
		Vector2[] bounds = MathUtils.getBounds(verts);
		float xScale = t.getWidth() / Math.abs(bounds[0].x - bounds[1].x);
		float yScale = t.getHeight() / Math.abs(bounds[0].y - bounds[1].y);
		if(x < 1 || y < 1){
			if(xScale > yScale){
				for(int i = 0; i < verts.length; i++){
					verts[i] *= yScale;
				}
			}
			else{
				for(int i = 0; i < verts.length; i++){
					verts[i] *= xScale;
				}
			}
		}
		TexturedPolygon out = new TexturedPolygon(verts, t, layer);
		out.setPosition(x, y);
		out.setColor(new Color(r, g, b, a));
		out.setRotation(rot);
		reset();
		return out;
	}

}
