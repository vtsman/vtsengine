package com.vtsman.engine.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.vtsman.engine.core.graphics.RenderManager;
import com.vtsman.engine.core.graphics.TextureRepo;
import com.vtsman.engine.core.map.Map;
import com.vtsman.engine.core.misc.DevConsole;
import com.vtsman.engine.core.misc.KeyHandler;
import com.vtsman.engine.core.misc.Ticker;
import com.vtsman.engine.core.sound.SoundRepo;
import com.vtsman.engine.primitive.graphics.RenderTexture;

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
	public static final boolean singleThreaded = true;

	@Override
	public void create() {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		TextureRepo.scan(Gdx.files.internal("./bin/textures/"));
		SoundRepo.scan(Gdx.files.internal("./bin/sounds/"));
		rm = new RenderManager();
		t.addEssentials();

		game = this;
		if (!singleThreaded)
			;
		new Thread(t).start();
		if (Gdx.files.internal("./bin/auto.conf").exists()) {
			System.out.println("Found auto-config file");
			DevConsole.evaluate(Gdx.files.internal("./bin/auto.conf")
					.readString().split("\n"));
		}
		if (Gdx.files.internal("./bin/controls.conf").exists()) {
			System.out.println("Found control config file");
			keyHandler.readFromFile(Gdx.files.internal("./bin/controls.conf"));
		}
		debugRenderer = new Box2DDebugRenderer();
		keyHandler.subscribe(new DevConsole(), "dev");
	}

	@Override
	public void dispose() {

	}

	int count = 0;

	@Override
	public void render() {
		if (singleThreaded) {
			if (count == 3) {
				t.runOnce();
				count = 0;
			} else {
				count++;
			}
		}
		Gdx.graphics.setTitle("MyGame - fps: "
				+ Gdx.graphics.getFramesPerSecond());

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (devMode && Gdx.input.isKeyPressed(Keys.V) && loadedMap != null) {
			debugMatrix = RenderManager.getCamera().combined.cpy();
			debugMatrix.scale(60f, 60f, 1f);
			debugMatrix.translate(-4f, -8f / 3f, 0f);
			rm.spriteBatch.begin();
			debugRenderer.render(loadedMap.world, debugMatrix);
			rm.spriteBatch.end();
		} else if (devMode && Gdx.input.isKeyPressed(Keys.C)
				&& loadedMap != null) {
			debugMatrix = RenderManager.getCamera().combined.cpy();
			debugMatrix.scale(60f, 60f, 1f);
			debugMatrix.translate(-4f, -8f / 3f, 0f);
			rm.render();
			rm.spriteBatch.begin();
			debugRenderer.render(loadedMap.world, debugMatrix);
			rm.spriteBatch.end();
		} else {
			rm.render();
		}
	}

	public RenderManager getRenderManager() {
		return rm;
	}

	@Override
	public void resize(int width, int height) {

		Gdx.gl20.glViewport(0, 0, width, height);
		// RenderManager.resize(width, height);
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
