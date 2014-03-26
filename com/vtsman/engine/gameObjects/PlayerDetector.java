package com.vtsman.engine.gameObjects;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.vtsman.engine.core.map.Map;
import com.vtsman.engine.core.misc.ISubscriber;
import com.vtsman.engine.core.misc.Sensor;
import com.vtsman.engine.core.physics.BodyPackage;
import com.vtsman.engine.core.utils.IBoolExpr;
import com.vtsman.engine.primitive.physics.BodyMaker;

public class PlayerDetector extends Sensor implements ISubscriber{
	boolean ret = false;
	public PlayerDetector(int x, int y, int height, int width, Map m){
		BodyPackage bp = BodyMaker.makeRectangle(width, height, x, y, 0, 0, 0, BodyType.StaticBody);
		bp.fix.isSensor = true;
		bp.createBody(m.world);
		m.subscribeToFixtureEvent("player", bp.f, this);
	}
	
	@Override
	public boolean evaluate(IBoolExpr[] args) {
		return ret;
	}

	@Override
	public void onEvent(String event, Object ... args) {
		if(event.equals("startplayer")){
			ret = true;
		}
		else{
			ret = false;
		}
	}
}
