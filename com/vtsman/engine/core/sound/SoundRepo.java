package com.vtsman.engine.core.sound;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class SoundRepo {
	private static HashMap<String, Sound> sounds = new HashMap<String, Sound>();
	public static void scan(FileHandle dir){
		for(FileHandle f : dir.list()){
			sounds.put(f.nameWithoutExtension(), Gdx.audio.newSound(f));
		}
	}
	
	public static Sound getSound(String s){
		return sounds.get(s);
	}
	
	public static void flush(){
		sounds = new HashMap<String, Sound>();
	}
}
