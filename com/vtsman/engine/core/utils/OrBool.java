package com.vtsman.engine.core.utils;

public class OrBool implements IBoolExpr{
	@Override
	public boolean evaluate(IBoolExpr[] args) {
		return args[0].evaluate(null) || args[1].evaluate(null);
	}
	@Override
	public int getNumArgs() {
		return 2;
	}
}