package com.vtsman.engine.core.map;

import com.badlogic.gdx.physics.box2d.World;

public interface ObjectConstructer {
	public void addArg(String varName, Object object);
	public Object make(Map m);
}
