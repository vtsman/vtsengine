package com.vtsman.engine.core.misc;

import com.vtsman.engine.core.utils.IBoolExpr;

public abstract class Sensor implements IBoolExpr{

	@Override
	public int getNumArgs() {
		return 0;
	}
}
