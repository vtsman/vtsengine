package com.vtsman.engine.core.map;

import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.vtsman.engine.core.Game;
import com.vtsman.engine.primitive.graphics.RenderTexture;

public class MapParser {
	private HashMap<String, Object> madeObjects = new HashMap<String, Object>();
	public static final HashMap<String, ObjectConstructer> objConst = new HashMap<String, ObjectConstructer>();
	private boolean inBraces = false;
	private String objName = null;
	private ObjectConstructer currentConst = null;
	
	static {
		objConst.put("decoration", new MakeDecor());
		objConst.put("platform", new MakePhysRect(BodyType.StaticBody));
		objConst.put("box", new MakePhysRect(BodyType.DynamicBody));
		objConst.put("player", new MakePlayer());
	}
	
	public Map decodeMap(String s){
		Map out = new Map();
		
		String[] lines = s.split("\n");
		for(String l : lines){
			executeString(l, out);
		}
		
		if(madeObjects.containsKey("gravity")){
			out.world.setGravity((Vector2) madeObjects.get("gravity"));
		}
		if(madeObjects.containsKey("background")){
			Game.getRenderer().setBg((RenderTexture) madeObjects.get("background"));
			madeObjects.remove("background");
		}
		if(madeObjects.containsKey("background2")){
			Game.getRenderer().setBg((RenderTexture) madeObjects.get("background2"));
			madeObjects.remove("background2");
		}
		if(madeObjects.containsKey("foreground")){
			Game.getRenderer().setBg((RenderTexture) madeObjects.get("foreground"));
			madeObjects.remove("foreground");
		}
		
		for(java.util.Map.Entry<String, Object> a : madeObjects.entrySet()){
			out.addObject(a.getValue());
		}
		return out;
	}
	
	private void executeString(String s, Map m){
		if(! inBraces && s.endsWith("{")){
			String[] lines = s.split(" ");
			currentConst = objConst.get(lines[0]);
			objName = lines[1].substring(0, lines[1].length() - 1);
			inBraces = true;
		}
		else if(inBraces && s.equals("}")){
			madeObjects.put(objName, currentConst.make(m));
			objName = null;
			currentConst = null;
			inBraces = false;
		}
		else if(!inBraces && objConst.containsKey(s.split(" ")[0])){
			currentConst = objConst.get(s.split(" ")[0]);
		}
		else if(currentConst != null){
			if(!s.contains("="))
				return;
			String[] words = s.split("=");
			if(words.length != 2){
				System.out.println(s);
				try {
					throw(new Exception("You somehow screwed up making a variable"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			currentConst.addArg(words[0], parseArg(words[1]));
		}
		else if(s.contains("=")){
			String[] words = s.split("=");
			if(words.length != 2){
				try {
					throw(new Exception("You somehow screwed up making a variable"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			madeObjects.put(words[0], parseArg(words[1]));
		}
	}
	
	private Object parseArg(String s){
		if(isInteger(s)){
			return Integer.parseInt(s);
		}
		if(isFloat(s)){
			return Float.parseFloat(s);
		}
		if(s.startsWith("\"") && s.endsWith("\"")){
			return s.substring(1, s.length() - 1);
		}
		if(s.contains(",")){
			String[] ints = s.split(",");
			return new Vector2(Float.parseFloat(ints[0]), Float.parseFloat(ints[1]));
		}
		if(s.equals("true")){
			return true;
		}
		if(s.equals("false")){
			return false;
		}
		if(this.madeObjects.containsKey(s)){
			return this.madeObjects.get(s);
		}
		return null;
	}
	
	private static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}
	
	private static boolean isFloat(String s) {
	    try { 
	        Float.parseFloat(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}
}