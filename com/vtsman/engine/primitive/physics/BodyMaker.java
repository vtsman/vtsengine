package com.vtsman.engine.primitive.physics;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.vtsman.engine.core.physics.BodyPackage;

public class BodyMaker {
	public static final int PIXLESPERMETER = 60;
	public static BodyPackage makeRectangle(float width, float height, float x, float y, float friction, float bounce, float density, BodyType bt){
		//System.out.println(width + "," + height);
		if(width * height == 0){
			return null;
		}
		float w = (float)width / (float) PIXLESPERMETER;
		float h = (float)height / (float) PIXLESPERMETER;
		BodyDef bod = new BodyDef();
		
		bod.position.set(w / 2 + x / (float) PIXLESPERMETER , h / 2 + y / (float) PIXLESPERMETER);
		bod.type = bt;
		
		
		FixtureDef fix = new FixtureDef();
		fix.friction = friction;
		fix.density = density;
		fix.restitution = bounce;

		
		PolygonShape s = new PolygonShape();
		s.setAsBox(w / 2, h / 2);
		fix.shape = s;
		return new BodyPackage(bod, fix);
	}
	
	public static BodyPackage makeCircle(float radius, float x, float y, float friction, float bounce, float density, BodyType bt){
		//System.out.println(width + "," + height);
		if(radius == 0){
			return null;
		}
		BodyDef bod = new BodyDef();
		float r = radius / PIXLESPERMETER;
		bod.position.set(r / 2 + x / (float) PIXLESPERMETER , r / 2 + y / (float) PIXLESPERMETER);
		bod.type = bt;
		
		
		FixtureDef fix = new FixtureDef();
		fix.friction = friction;
		fix.density = density;
		fix.restitution = bounce;

		
		CircleShape s = new CircleShape();
		s.setRadius(r);
		fix.shape = s;
		return new BodyPackage(bod, fix);
	}
}
