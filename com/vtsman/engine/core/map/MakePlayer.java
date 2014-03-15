package com.vtsman.engine.core.map;

import com.badlogic.gdx.math.Vector2;
import com.vtsman.engine.gameObjects.entities.Player;

public class MakePlayer implements ObjectConstructer{
	Vector2 pos;
	String tex;
	@Override
	public void addArg(String varName, Object object) {
		if(varName.matches("position")){
			pos = (Vector2) object;
		}
		if(varName.matches("texture")){
			tex = ((String) object);
		}
	}

	@Override
	public Object make(Map m) {
		return new Player(pos, tex);
	}
	
}
