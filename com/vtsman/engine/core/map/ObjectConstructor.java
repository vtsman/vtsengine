package com.vtsman.engine.core.map;

import com.badlogic.gdx.physics.box2d.World;

public interface ObjectConstructor {
	public void addArg(String varName, Object object);
	public Object make(Map m);
}
