package com.vtsman.engine.core.misc;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.vtsman.engine.core.Game;
import com.vtsman.engine.core.graphics.TextureRepo;
import com.vtsman.engine.gameObjects.basicObjs;
import com.vtsman.engine.primitive.graphics.RenderTexture;
import com.vtsman.engine.primitive.physics.BodyMaker;

public class TestKey implements ITickable {
	RenderTexture te;
	Body b;
	boolean created = false;
	@Override
	public void tick() {
		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			int x1, x2, y1, y2;
			if(!created){
			Game.m.world.dispose();
			Game.m.world = new World(new Vector2(0, -40), true);
			
			Game.getRenderer().flush();
			b = BodyMaker.makeRectangle(Gdx.graphics.getWidth(), 50, 0, 1, 1, 0, 1, BodyType.StaticBody).getBody(
					Game.m.world);
			te = new RenderTexture(TextureRepo.getTexture("rainbow"), 1);
			te.bindToBody(b);
			b.setActive(true);
			te.setDimensions(Gdx.graphics.getWidth(), 50);
			Game.getRenderer().addRenderer(te);
			created = true;
			}
			x1 = Gdx.input.getX();
			y1 = Gdx.input.getY();
			while(Gdx.input.isButtonPressed(Buttons.LEFT));
			x2 = Gdx.input.getX();
			y2 = Gdx.input.getY();
			
			int lowerX;
			int lowerY;
			int upperX;
			int upperY;
			
			if(x1 > x2){
				lowerX = x2;
				upperX = x1;
			}
			else{
				lowerX = x1;
				upperX = x2;
			}
			
			if(y1 > y2){
				lowerY = y2;
				upperY = y1;
			}
			else{
				lowerY = y1;
				upperY = y2;
			}
			int width = upperX - lowerX;
			int height = upperY - lowerY;
			RenderTexture t = basicObjs.makeCollidableRectangle(width, height, lowerX, Gdx.graphics.getHeight() - lowerY, 0, 1, 0, 1, BodyType.DynamicBody, "rainbow", 1, Game.m);
			Game.getRenderer().addRenderer(t);
		}
		
	}

}
