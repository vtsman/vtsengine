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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
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
	float w;
	float h;
	
	int frameW;
	int frameH;
	
	public RenderManager() {
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(w, h);
		camera.position.set(w / 2, h / 2, 0);
	}
	//TODO camera smoothing
	public void setCamera(int x, int y){
		if(x < 0){
			x = 0;
		}
		if(y < 0){
			y = 0;
		}
		if(x + w > frameW){
			x = (int) (frameW - w);
		}
		
		if(y + h > frameH){
			y = (int) (frameH - h);
		}
		camera.position.set(x + w / 2, y + h / 2, 0);
		camera.update();
        camera.apply(Gdx.gl10);
		
		spriteBatch.setProjectionMatrix(camera.combined);
		polySpriteBatch.setProjectionMatrix(camera.combined);
	}
	Body bound;
	public void bindToBody(Body b){
		bound = b;
	}
	
	public void setFrameDimensions(int w, int h){
		frameW = w;
		frameH = h;
	}

	public synchronized void render() {
		// render background
		
		//Gdx.gl10.glViewport(0, 0, (int)w, (int)h);
		setCamera(0, 0);
		spriteBatch.begin();
		if (bg != null) {
			bg.render(this);
		}
		if(bg2 != null){
			bg2.render(this);
		}
		spriteBatch.end();
		//Gdx.gl10.glViewport(cX, cY, (int)w, (int)h);
		camera.update();
        camera.apply(Gdx.gl10);
		// render everything in renderList and output fps if f is pressed
		/*if (Gdx.input.isKeyPressed(Keys.F)) {
			System.out.println(Gdx.graphics.getFramesPerSecond());
		}*/
		if(bound != null){
			setCamera((int) (bound.getPosition().x * 60 - w / 2), (int) (bound.getPosition().y * 60 - 3*h/4));
		}
		for (RenderLayer layer : renderList) {
			layer.render();
		}
		//Gdx.gl10.glViewport(0, 0, (int)w, (int)h);
		setCamera(0, 0);
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
