package com.vtsman.engine.core.misc;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.vtsman.engine.core.Game;
import com.vtsman.engine.core.map.MapParser;
import com.vtsman.engine.gameObjects.entities.Player;

public class DevConsole implements ISubscriber {

	public void onEvent(String event, Object ... args) {
		String s = (String) JOptionPane
				.showInputDialog(
						new JFrame(),
						"Welcome to the vtsengine developer console!\nPlease enter a command.",
						JOptionPane.PLAIN_MESSAGE);
		if (s == null) {
			return;
		}
		String[] commands = s.split("; ");
		evaluate(commands);
	}
	public static void evaluate(String[] commands){
		for (String st : commands) {
			String[] args = st.split(" ");
			if (args[0].matches("load")) {
				Game.getRenderer().flush();
				Game.getTicker().flush();
				Game.loadedMap = new MapParser().decodeMap(Gdx.files.internal(
						"./bin/maps/" + args[1]).readString());
				Game.getTicker().add(Game.loadedMap);
				Game.getTicker().addEssentials();
			}
			if (args[0].equals("spawn")) {
				if (args[1].matches("player")) {
					Player p = new Player(new Vector2(
							Integer.parseInt(args[2]),
							Integer.parseInt(args[3])), args[4]);
					if (Game.loadedMap != null) {
						Game.loadedMap.addObject(p);
					}
				}
			}
			if (args[0].equals("setGravity")) {
				if (Game.loadedMap == null)
					return;
				Game.loadedMap.world.setGravity(new Vector2(Integer
						.parseInt(args[1]), Integer.parseInt(args[2])));
			}
			if(args[0].equals("setTickDelay")){
				Game.getTicker().ticktime = Integer.parseInt(args[1]);
			}
			if(args[0].equals("devMode")){
				if(args[1].equals("true")){
					Game.devMode = true;
				}
				else{
					Game.devMode = false;
				}
			}
			if(args[0].equals("lockCameraOn")){
				if(args[1].equals("player")){
					Game.getRenderer().bindToBody(Game.loadedMap.players.get(Integer.parseInt(args[2])).getBody());
				}
				if(isInteger(args[1])){
					Game.getRenderer().bindToBody(null);
					Game.getRenderer().setCamera(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
				}
			}
		}
	}
	private static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}
}
