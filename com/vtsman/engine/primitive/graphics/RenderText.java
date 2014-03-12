package com.vtsman.engine.primitive.graphics;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.physics.box2d.Body;
import com.vtsman.engine.core.graphics.IRenderer;
import com.vtsman.engine.core.graphics.RenderManager;
import com.vtsman.engine.core.graphics.RenderType;

public class RenderText implements IRenderer{
	public RenderText(BitmapFont f, String s){
		
	}
	
	@Override
	public void render(RenderManager rm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RenderType getRenderType() {
		// TODO Auto-generated method stub
		return RenderType.sprite;
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
