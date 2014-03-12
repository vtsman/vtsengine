package com.vtsman.engine.core.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RenderLayer {
	HashMap<RenderType, ArrayList<IRenderer>> renderMap = new HashMap<RenderType, ArrayList<IRenderer>>();
	RenderManager r;

	public RenderLayer(RenderManager rm) {
		r = rm;
		for (RenderType type : RenderType.values()) {
			renderMap.put(type, new ArrayList<IRenderer>());
		}
	}

	public void render() {
		for (Map.Entry<RenderType, ArrayList<IRenderer>> a : renderMap
				.entrySet()) {
			TypeHandler.initalizeType(a.getKey(), r);
			for (IRenderer r : a.getValue()) {
				if (r != null)
					synchronized (r) {
						r.render(this.r);
					}
			}
			TypeHandler.releaseType(a.getKey(), r);
		}
	}

	public void addRenderer(IRenderer r) {
		if (!renderMap.containsKey(r.getRenderType())) {
			renderMap.put(r.getRenderType(), new ArrayList<IRenderer>());
		}
		renderMap.get(r.getRenderType()).add(r);
	}

	public void removeRenderer(IRenderer r) {
		if (renderMap.containsKey(r.getRenderType())) {
			if (renderMap.get(r.getRenderType()).contains(r)) {
				renderMap.get(r.getRenderType()).remove(r);
			}
		}
	}
}
