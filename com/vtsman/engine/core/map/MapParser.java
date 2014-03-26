package com.vtsman.engine.core.map;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.vtsman.engine.core.Game;
import com.vtsman.engine.core.misc.Sensor;
import com.vtsman.engine.core.utils.ArrayWrapper;
import com.vtsman.engine.primitive.graphics.RenderTexture;

public class MapParser {
	private HashMap<String, Object> madeObjects = new HashMap<String, Object>();
	public static final HashMap<String, ObjectConstructor> objConst = new HashMap<String, ObjectConstructor>();
	private boolean inBraces = false;
	private String objName = null;
	private ObjectConstructor currentConst = null;
	
	static {
		objConst.put("decoration", new MakeDecor());
		objConst.put("platform", new MakePhysRect(BodyType.StaticBody));
		objConst.put("box", new MakePhysRect(BodyType.DynamicBody));
		objConst.put("circle_platform", new MakePhysCircle(BodyType.StaticBody));
		objConst.put("circle_box", new MakePhysCircle(BodyType.DynamicBody));
		objConst.put("player", new MakePlayer());
		objConst.put("poly_decoration", new MakeDecorPoly());
		objConst.put("poly_playform", new MakePhysPoly(BodyType.StaticBody));
		objConst.put("poly_box", new MakePhysPoly(BodyType.DynamicBody));
	}
	
	public Map decodeMap(String s){
		Map out = new Map();
		String[] lines = s.split("\n");
		for(String l : lines){
			executeString(l, out);
		}
		
		if(madeObjects.containsKey("gravity")){
			Object[] g = ((ArrayWrapper)madeObjects.get("gravity")).getArr();
			out.world.setGravity(new Vector2((Float)g[0], (Float)g[1]));
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
		if(madeObjects.containsKey("dimensions")){
			Object[] g = ((ArrayWrapper)madeObjects.get("dimensions")).getArr();
			out.height = (Integer) g[1];
			out.width = (Integer) g[0];
			Game.getRenderer().setFrameDimensions((Integer) g[0], (Integer) g[1]);
		}
		else{
			out.height = (Integer) Gdx.graphics.getWidth();
			out.width = (Integer) Gdx.graphics.getHeight();
			Game.getRenderer().setFrameDimensions(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
		
		for(java.util.Map.Entry<String, Object> a : madeObjects.entrySet()){
			out.addObject(a.getValue());
			if(a.getValue() instanceof Sensor){
				out.sensors.put(a.getKey(), (Sensor) a.getValue());
			}
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
			ArrayWrapper out = new ArrayWrapper();
			String[] entries = s.split(",");
			Object[] objs = new Object[entries.length];
			for(int i = 0; i < entries.length; i++){
				objs[i] = parseArg(entries[i]);
			}
			out.setArr(objs);
			return out;
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
