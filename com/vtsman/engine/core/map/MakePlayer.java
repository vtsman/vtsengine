package com.vtsman.engine.core.map;

import com.badlogic.gdx.math.Vector2;
import com.vtsman.engine.core.utils.ArrayWrapper;
import com.vtsman.engine.gameObjects.entities.Player;

public class MakePlayer implements ObjectConstructor{
	Vector2 pos;
	String tex;
	@Override
	public void addArg(String varName, Object object) {
		if(varName.matches("position")){
			Object[] g = ((ArrayWrapper)object).getArr();
			pos = new Vector2((Float)g[0], (Float)g[1]);
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
