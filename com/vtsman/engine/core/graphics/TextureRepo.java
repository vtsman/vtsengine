package com.vtsman.engine.core.graphics;

import java.util.HashMap;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class TextureRepo {
	private static HashMap<String, Texture> textures = new HashMap<String, Texture>();
	public static void scan(FileHandle dir){
		for(FileHandle f : dir.list()){
			textures.put(f.nameWithoutExtension(), new Texture(f));
		}
	}
	
	public static Texture getTexture(String s){
		return textures.get(s);
	}
	
	public static void flush(){
		textures = new HashMap<String, Texture>();
	}
}
