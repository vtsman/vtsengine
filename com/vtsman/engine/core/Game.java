package com.vtsman.engine.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.vtsman.engine.core.graphics.RenderManager;
import com.vtsman.engine.core.graphics.TextureRepo;
import com.vtsman.engine.core.map.Map;
import com.vtsman.engine.core.map.MapParser;
import com.vtsman.engine.core.misc.KeyHandler;
import com.vtsman.engine.core.misc.ScreenshotHandler;
import com.vtsman.engine.core.misc.TestKey;
import com.vtsman.engine.core.misc.Ticker;
import com.vtsman.engine.core.sound.SoundRepo;
import com.vtsman.engine.primitive.graphics.PolyWire;
import com.vtsman.engine.primitive.graphics.RenderTexture;

public class Game implements ApplicationListener {
	private static Ticker t = new Ticker();
	private static RenderManager rm;
	private static Game game;
	public static Map m;
	public static RenderTexture te;
	public static Body b;
	public static final KeyHandler keyHandler = new KeyHandler();
	@Override
	public void create() {
		Gdx.gl.glEnable(GL10.GL_BLEND);
		TextureRepo.scan(Gdx.files.internal("./bin/textures/"));
		SoundRepo.scan(Gdx.files.internal("./bin/sounds/"));
		rm = new RenderManager();
		t.add(keyHandler);
		
		m = new MapParser().decodeMap(Gdx.files.internal("./bin/maps/test.map").readString());
		t.add(m);
		
		game = this;
		new Thread(t).start();
		
	}

	@Override
	public void dispose() {

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		rm.render();
		
		if(Gdx.input.isKeyPressed(Keys.S)){
			ScreenshotHandler.saveScreenshot(Gdx.files.external("/Users/Spencer/Documents/screenshot.png"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
	}

	public RenderManager getRenderManager() {
		return rm;
	}

	@Override
	public void resize(int width, int height) {
		//TODO: Move camera to seperate class
		
		Gdx.gl10.glViewport(0, 0, width, height);
		//RenderManager.resize(width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	public static Game getInstance() {
		return game;
	}

	public static Ticker getTicker() {
		return t;
	}

	public static RenderManager getRenderer() {
		return rm;
	}
}
