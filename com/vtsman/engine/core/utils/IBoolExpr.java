package com.vtsman.engine.core.utils;

public interface IBoolExpr {
	public boolean evaluate(IBoolExpr[] args);
	public int getNumArgs();
}
