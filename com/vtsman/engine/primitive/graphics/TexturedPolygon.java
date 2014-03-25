package com.vtsman.engine.primitive.graphics;

import java.util.Arrays;

import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.vtsman.engine.core.graphics.IRenderer;
import com.vtsman.engine.core.graphics.RenderManager;
import com.vtsman.engine.core.graphics.RenderType;
import com.vtsman.engine.core.graphics.TextureRepo;

public class TexturedPolygon implements IRenderer {
	PolygonSprite ps;

	public TexturedPolygon(float[] verts) {
		short[] ind = new short[(verts.length / 2 - 2) * 3];
		for(int i = 0; i < ind.length / 3; i++){
			ind[i * 3] = 0;
			ind[i*3 + 1] = (short) (i + 1);
			ind[i*3 + 2] = (short) (i + 2);
		}
		System.out.println(Arrays.toString(ind));
		PolygonRegion polyReg = new PolygonRegion(new TextureRegion(
				TextureRepo.getTexture("rainbow")), verts, ind);
		ps = new PolygonSprite(polyReg);
		ps.setOrigin(0, 0);
	}

	@Override
	public void render(RenderManager rm) {
		ps.draw(rm.polySpriteBatch);
	}

	@Override
	public RenderType getRenderType() {
		// TODO Auto-generated method stub
		return RenderType.polySprite;
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPosition(float x, float y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRotation(float rot) {
		// TODO Auto-generated method stub

	}

	@Override
	public void bindToBody(Body b) {
		// TODO Auto-generated method stub

	}

}
