package com.vtsman.engine.core.utils;

public class BoolWrapper implements IBoolExpr{
	boolean b;
	public BoolWrapper(boolean value){
		b = value;
	}
	@Override
	public boolean evaluate(IBoolExpr[] args) {
		return b;
	}
	@Override
	public int getNumArgs() {
		return 0;
	}
}
