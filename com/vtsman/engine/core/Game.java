package com.vtsman.engine.core;

import java.util.HashMap;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.vtsman.engine.core.graphics.RenderManager;
import com.vtsman.engine.core.graphics.TextureRepo;
import com.vtsman.engine.core.map.Map;
import com.vtsman.engine.core.misc.DevConsole;
import com.vtsman.engine.core.misc.KeyHandler;
import com.vtsman.engine.core.misc.ScreenshotHandler;
import com.vtsman.engine.core.misc.Ticker;
import com.vtsman.engine.core.sound.SoundRepo;
import com.vtsman.engine.core.utils.IBoolExpr;
import com.vtsman.engine.core.utils.MathUtils;
import com.vtsman.engine.core.utils.StringLogic;
import com.vtsman.engine.primitive.graphics.RenderTexture;
import com.vtsman.engine.primitive.graphics.TexturedPolygon;

public class Game implements ApplicationListener {
	private static Ticker t = new Ticker();
	private static RenderManager rm;
	private static Game game;
	public static RenderTexture te;
	public static Body b;
	public static final KeyHandler keyHandler = new KeyHandler();
	public static Map loadedMap;
	Box2DDebugRenderer debugRenderer;
	Matrix4 debugMatrix;
	public static boolean devMode = false;
	@Override
	public void create() {
		Gdx.gl.glEnable(GL10.GL_BLEND);
		TextureRepo.scan(Gdx.files.internal("./bin/textures/"));
		SoundRepo.scan(Gdx.files.internal("./bin/sounds/"));
		rm = new RenderManager();
		t.addEssentials();
		
		game = this;
		new Thread(t).start();
		if(Gdx.files.internal("./bin/auto.conf").exists()){
			System.out.println("Found auto-config file");
			DevConsole.evaluate(Gdx.files.internal("./bin/auto.conf").readString().split("\n"));
		}
		debugMatrix = RenderManager.getCamera().combined;
		debugMatrix.scale(1f/8f, 1f/8f, 1f);
		debugMatrix.translate(-4f, -8f/3f, 0f);
		debugRenderer=new Box2DDebugRenderer();
		keyHandler.subscribe(new DevConsole(), "dev");
		System.out.println(new StringLogic("true && ! (false || ! false)", new HashMap<String, IBoolExpr>()).evaluate(null));
	}

	@Override
	public void dispose() {

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		if(devMode && Gdx.input.isKeyPressed(Keys.V) && loadedMap != null){
			rm.spriteBatch.begin();
			debugRenderer.render(loadedMap.world, debugMatrix);
			rm.spriteBatch.end();
		}
		else if(devMode && Gdx.input.isKeyPressed(Keys.C) && loadedMap != null){
			rm.render();
			rm.spriteBatch.begin();
			debugRenderer.render(loadedMap.world, debugMatrix);
			rm.spriteBatch.end();
		}
		else{
			rm.render();
		}
		
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
