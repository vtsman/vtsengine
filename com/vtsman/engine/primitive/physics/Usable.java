package com.vtsman.engine.primitive.physics;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.vtsman.engine.core.map.Map;
import com.vtsman.engine.core.misc.ISubscriber;
import com.vtsman.engine.core.misc.ITickable;
import com.vtsman.engine.core.physics.BodyPackage;
import com.vtsman.engine.gameObjects.entities.Player;

public abstract class Usable implements ISubscriber, ITickable{
	public Usable (int x, int y, int height, int width, Map m){
		BodyPackage b = BodyMaker.makeRectangle(width, height, x, y, 0, 0, 1, BodyType.StaticBody);
		b.fix.isSensor = true;
		b.createBody(m.world);
		b.f.setUserData(this);
		m.subscribeToFixtureEvent("player", b.f, this);
		m.addFixtureEvent(b.f, "usable");
	}
	Player p;
	public void onEvent(String event, Object... args) {
		if(args == null){
			return;
		}
		if(args.length < 2){
			return;
		}
		if(event.equals("startplayer"))
			p = (Player) args[1];
		else
			p = null;
	}
	boolean doNext = false;
	@Override
	public void tick(){
		if(p != null){
			if(p.isUsing)
				doNext = true;
			else if(doNext){
				use(p);
				doNext = false;
			}
		}
	}
	
	protected abstract void use(Player p);
}
