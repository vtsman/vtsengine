package com.vtsman.engine.core.map;

import com.vtsman.engine.core.Game;
import com.vtsman.engine.core.misc.TickedCommand;
import com.vtsman.engine.core.utils.ArrayWrapper;
import com.vtsman.engine.core.utils.StringLogic;

public class MakeCommandRunner implements ObjectConstructor {
	String[] commands;
	String condition;
	int delay;
	boolean runOnce = false;
	int indel;

	@Override
	public void addArg(String varName, Object object) {
		if (varName.equals("runOnce")) {
			runOnce = (Boolean) object;
		}
		if (varName.equals("initalDelay")) {
			indel = (Integer) object;
		}
		if (varName.equals("condition")) {
			condition = (String) object;
		}
		if (varName.equals("delay")) {
			delay = (Integer) object;
		}
		if (varName.equals("commands")) {
			if (object instanceof ArrayWrapper) {
				Object[] objs = (Object[]) ((ArrayWrapper) object).getArr();
				commands = new String[objs.length];
				for (int i = 0; i < objs.length; i++) {
					commands[i] = (String) objs[i];
				}
			}
			else{
				commands = new String[1];
				commands[0] = (String) object;
			}
		}
	}

	@Override
	public Object make(Map m) {
		TickedCommand cr = new TickedCommand(commands, new StringLogic(
				condition, m.sensorsIBool), delay, indel, m);
		if (runOnce) {
			cr.tick();
		} else {
			Game.getTicker().add(cr);
		}
		return cr;
	}

}
