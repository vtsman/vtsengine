package com.vtsman.engine.core.utils;

public class NotBool implements IBoolExpr{
	@Override
	public boolean evaluate(IBoolExpr[] args) {
		return !args[0].evaluate(null);
	}
	@Override
	public int getNumArgs() {
		return 1;
	}
}
