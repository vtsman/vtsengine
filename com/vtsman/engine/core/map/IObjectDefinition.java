package com.vtsman.engine.core.map;

import java.util.ArrayList;
import java.util.HashMap;

public interface IObjectDefinition {
	public void make(HashMap<String, Object> args);
	public void put(ArrayList<Object> args);
	public IObjectDefinition makeNew();
}
