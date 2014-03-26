package com.vtsman.engine.primitive.graphics;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.Body;
import com.vtsman.engine.core.graphics.IRenderer;
import com.vtsman.engine.core.graphics.RenderManager;
import com.vtsman.engine.core.graphics.RenderType;
import com.vtsman.engine.core.graphics.TypeHandler;

public class CombinedRenderer implements IRenderer {
	ArrayList<IRenderer> renderList = new ArrayList<IRenderer>();
	int l;

	public CombinedRenderer(int layer) {
		l = layer;
	}

	@Override
	public void render(RenderManager rm) {
		for (IRenderer r : renderList) {
			if (r == null)
				continue;
			TypeHandler.initalizeType(r.getRenderType(), rm);
			r.render(rm);
			TypeHandler.releaseType(r.getRenderType(), rm);
		}
	}

	@Override
	public RenderType getRenderType() {
		return RenderType.other;
	}

	@Override
	public int getLayer() {
		return l;
	}

	@Override
	public void setPosition(float x, float y) {
		for (IRenderer r : renderList) {
			if (r == null)
				continue;
			r.setPosition(x, y);
		}
	}

	@Override
	public void setRotation(float rot) {
		for (IRenderer r : renderList) {
			if (r == null)
				continue;
			r.setRotation(rot);
		}
	}

	@Override
	public void bindToBody(Body b) {
		for (IRenderer r : renderList) {
			if (r == null)
				continue;
			r.bindToBody(b);
		}
	}

	public void addRenderer(IRenderer r) {
		renderList.add(r);
	}

	public void removeRenderer(IRenderer r) {
		renderList.remove(r);
	}
}
