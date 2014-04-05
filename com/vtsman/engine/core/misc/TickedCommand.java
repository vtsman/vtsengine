package com.vtsman.engine.core.misc;

import java.util.Arrays;

import com.vtsman.engine.core.map.Map;
import com.vtsman.engine.core.utils.StringLogic;

public class TickedCommand implements ITickable{
	StringLogic condition;
	int delay;
	String[] commands;
	int c = 0;
	Map parent;
	int indel;
	public TickedCommand(String[] commands, StringLogic sl, int tickdelay, int initialDelay, Map parent){
		delay = tickdelay;
		this.commands = commands;
		condition = sl;
		this.parent = parent;
		indel = initialDelay;
	}
	
	@Override
	public void tick() {
		if(indel > 0){
			indel--;
			return;
		}
		if(c == delay){
			c = 0;
			if(condition.evaluate(null)){
				System.out.println("Evaluating the commands: " + Arrays.toString(commands));
				DevConsole.evaluate(commands, parent);
			}
		}
		else
			c++;
	}
	
}
