package com.vtsman.engine.core.graphics;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.vtsman.engine.primitive.graphics.RenderTexture;

public class RenderManager {
	private static OrthographicCamera camera;
	public final SpriteBatch spriteBatch = new SpriteBatch();
	public final PolygonSpriteBatch polySpriteBatch = new PolygonSpriteBatch();
	public final TypeHandler th = new TypeHandler();
	public final ShapeRenderer sr = new ShapeRenderer();

	private RenderTexture bg;
	private RenderTexture bg2;
	private RenderTexture fg;

	private ArrayList<RenderLayer> renderList = new ArrayList<RenderLayer>();

	public RenderManager() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(1, h / w);
	}

	public synchronized void render() {
		// render background
		spriteBatch.begin();
		if (bg != null) {
			bg.render(this);
		}
		if(bg2 != null){
			bg2.render(this);
		}
		spriteBatch.end();
		// render everything in renderList and output fps if f is pressed
		if (Gdx.input.isKeyPressed(Keys.F)) {
			System.out.println(Gdx.graphics.getFramesPerSecond());
		}
		for (RenderLayer layer : renderList) {
			layer.render();
		}
		spriteBatch.begin();
		if (fg != null) {
			fg.render(this);
		}
		spriteBatch.end();
	}

	public synchronized void addRenderer(IRenderer r) {
		if (renderList.size() <= r.getLayer()) {
			if (renderList.size() < r.getLayer()) {
				for (int i = renderList.size(); i < r.getLayer(); i++) {
					renderList.add(i, new RenderLayer(this));
				}
			}
			renderList.add(r.getLayer(), new RenderLayer(this));
		}
		renderList.get(r.getLayer()).addRenderer(r);
	}

	public synchronized void removeRenderer(IRenderer r) {
		if (renderList.size() > r.getLayer()) {
			if (renderList.get(r.getLayer()) != null) {
				renderList.get(r.getLayer()).removeRenderer(r);
			}
		}
	}

	public static Camera getCamera() {
		return camera;
	}

	public synchronized void flush() {

		renderList = new ArrayList<RenderLayer>();
	}

	public synchronized void setBg(RenderTexture tex) {
		bg = tex;
		bg.setDimensions(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		bg.setRotation(0);
	}
	public synchronized void setBg2(RenderTexture tex) {
		bg2 = tex;
		bg2.setDimensions(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		bg2.setRotation(0);
	}
	public synchronized void setFg(RenderTexture tex) {
		fg = tex;
		fg.setDimensions(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		fg.setRotation(0);
	}

	public void resize(int width, int height) {
		bg.setDimensions(width, height);
		camera.viewportWidth = 1;
		camera.viewportHeight = height / width;
		camera.update();
	}
}
