package com.vtsman.engine.core.map;

import com.vtsman.engine.gameObjects.PlayerDetector;

public class MakePlayerDetector implements ObjectConstructor {
	int x;
	int y;
	int width;
	int height;

	@Override
	public void addArg(String varName, Object object) {
		if (varName.equals("x")) {
			x = (Integer)object;
		}
		if (varName.equals("y")) {
			y = (Integer)object;
		}
		if (varName.equals("width")) {
			width = (Integer)object;
		}
		if (varName.equals("height")) {
			height = (Integer)object;
		}
	}

	@Override
	public Object make(Map m) {
		return new PlayerDetector(x, y, width, height, m);
	}

}
