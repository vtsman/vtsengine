package com.vtsman.engine.core.misc;

import com.badlogic.gdx.math.Vector2;
import com.vtsman.engine.core.Game;
import com.vtsman.engine.core.graphics.IRenderer;
import com.vtsman.engine.core.map.Map;
import com.vtsman.engine.core.physics.BodyPackage;
import com.vtsman.engine.core.physics.IPhysable;

public abstract class Entity implements ITickable, IPhysable{
	protected BodyPackage body;
	protected int health;
	protected int maxHealth;
	public Vector2 spawnPos;
	
	public Entity(Vector2 pos){
		spawnPos = pos;
	}
	
	public void setPosition(Vector2 pos){
		body.body.setTransform(pos, 0);
	}
	/**
	 * Must NOT return new IRenderer every time
	*/
	public abstract IRenderer getRenderer();
	
	protected abstract BodyPackage getBodyPackage();
	
	public void create(Map parent){
		parent.addEntity(this);
		body.createBody(parent.world);
		Game.getRenderer().addRenderer(this.getRenderer());
	}
	
	public void kill(Map parent){
		parent.removeEntity(this);
		body.destroyBody(parent.world);
		Game.getRenderer().removeRenderer(this.getRenderer());
	}
	
	public abstract void harm(DamageType dt, int intesity);
	
	public void heal(int amount){
		if(health + amount <= maxHealth){
			health += amount;
			return;
		}
		health = maxHealth;
	}
	
	public abstract Vector2 getPos();
}
