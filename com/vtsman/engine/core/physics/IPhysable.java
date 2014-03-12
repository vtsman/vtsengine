package com.vtsman.engine.core.physics;

import java.awt.Rectangle;

import com.badlogic.gdx.physics.box2d.Body;

public interface IPhysable {
	public Rectangle getBounds();
	public float getRot();
	/**
	 * Must NOT return new Body every time
	*/
	public Body getBody();
}
