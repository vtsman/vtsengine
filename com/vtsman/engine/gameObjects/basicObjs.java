package com.vtsman.engine.gameObjects;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.vtsman.engine.core.graphics.TextureRepo;
import com.vtsman.engine.core.map.Map;
import com.vtsman.engine.core.physics.BodyPackage;
import com.vtsman.engine.primitive.graphics.RenderTexture;
import com.vtsman.engine.primitive.physics.BodyMaker;

public class BasicObjs {
	public static RenderTexture makeCollidableRectangle(int width, int height, int x, int y, float angle, float friction, float bounce, float dens, BodyType type, String texture, int layer, Map map){
		BodyPackage p = BodyMaker.makeRectangle(width, height, x, y, friction, bounce, dens, type);
		RenderTexture t = new RenderTexture(TextureRepo.getTexture(texture), layer);
		t.setDimensions(width, height);
		t.setPosition(x, y);
		t.setRotation(angle);
		t.bindToBody(p.createBody(map.world));
		return t;
	}
}
