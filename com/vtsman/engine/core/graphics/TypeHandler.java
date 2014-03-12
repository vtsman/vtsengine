package com.vtsman.engine.core.graphics;
public class TypeHandler {
	public static void initalizeType(RenderType type, RenderManager rm){
		if(type == null)
			return;
		switch(type){
		case sprite: rm.spriteBatch.begin(); 
		break;
		case polySprite: rm.polySpriteBatch.begin();
		break;
		default:
			break;
		}
	}
	
	public static void releaseType(RenderType type, RenderManager rm){
		if(type == null)
			return;
		switch(type){
		case sprite: rm.spriteBatch.end();
		break;
		case polySprite: rm.polySpriteBatch.end(); 
		break;
		default:
			break;
		}
	}
}
