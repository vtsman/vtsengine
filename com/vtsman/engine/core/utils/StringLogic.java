package com.vtsman.engine.core.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class StringLogic implements IBoolExpr {
	String[] words;
	IBoolExpr[] expressions;
	boolean[] marked;

	ArrayList<IBoolExpr> inst = new ArrayList<IBoolExpr>();
	IBoolExpr op = null;

	public StringLogic(String arg, HashMap<String, IBoolExpr> vars) {
		if (arg.startsWith("(")) {
			arg = arg.substring(1);
		}
		if (arg.startsWith(")")) {
			arg = arg.substring(0, arg.length() - 1);
		}
		words = arg.split(" ");
		expressions = new IBoolExpr[words.length];
		marked = new boolean[words.length];
		evalParen(vars);
		evalTF();
		evalExpr();
		for (int i = 0; i < words.length; i++) {
			if (!marked[i]) {
				if (vars.containsKey(words[i])) {
					expressions[i] = vars.get(words[i]);
				} else {
					expressions[i] = new BoolWrapper(false);
				}
			}
		}
		if (expressions.length == 1) {
			op = expressions[0];
		} else {
			condense();
			op = inst.get(0);
			for (int i = 0; i < inst.size(); i++) {
				if (inst.get(i).getNumArgs() > 0) {
					op = new Statement(inst.get(i), new IBoolExpr[] { op,
							inst.get(i + 1) });
				}
			}
		}
	}

	@Override
	public boolean evaluate(IBoolExpr[] args) {
		return op.evaluate(null);
	}

	@Override
	public int getNumArgs() {
		return 0;
	}

	private void condense() {
		ArrayList<IBoolExpr> temp = new ArrayList<IBoolExpr>();
		for (int i = 0; i < expressions.length; i++) {
			IBoolExpr b = expressions[i];
			if (b != null) {
				temp.add(b);
			}
		}
		for (int i = 0; i < temp.size(); i++) {
			IBoolExpr b = temp.get(i);
			if (b instanceof NotBool) {
				inst.add(new Inverted(temp.get(i + 1)));
				i++;
			}
		}
	}

	private void evalExpr() {
		for (int i = 0; i < words.length; i++) {
			if (!marked[i]) {
				if (words[i].equals("!")) {
					expressions[i] = new NotBool();
					marked[i] = true;
				}
				if (words[i].equals("&&")) {
					expressions[i] = new AndBool();
					marked[i] = true;
				}
				if (words[i].equals("||")) {
					expressions[i] = new OrBool();
					marked[i] = true;
				}
			}
		}
	}

	private void evalTF() {
		for (int i = 0; i < words.length; i++) {
			if (!marked[i]) {
				if (words[i].toLowerCase().equals("true")) {
					expressions[i] = new BoolWrapper(true);
					marked[i] = true;
				}
				if (words[i].toLowerCase().equals("false")) {
					expressions[i] = new BoolWrapper(false);
					marked[i] = true;
				}
			}
		}
	}

	private void evalParen(HashMap<String, IBoolExpr> vars) {
		int openParen = 0;
		int parenStart = 0;
		int parenEnd = 0;
		for (int i = 0; i < words.length; i++) {
			if (words[i].startsWith("(")) {
				openParen++;
				if (openParen == 1) {
					parenStart = i;
				}
			}
			if (words[i].endsWith(")")) {
				openParen--;
				if (openParen == 0) {
					parenEnd = i;
					String parenArgs = "";
					for (int j = parenStart; j <= parenEnd; j++) {
						marked[j] = true;
						if (j != parenStart) {
							parenArgs = parenArgs + " ";
						}
						parenArgs = parenArgs + words[j];
					}
					parenArgs = parenArgs.substring(1, parenArgs.length() - 1);
					expressions[i] = new StringLogic(parenArgs, vars);
				}
			}
		}
	}

	private class Statement implements IBoolExpr {
		IBoolExpr o;
		IBoolExpr[] a;

		public Statement(IBoolExpr op, IBoolExpr[] args) {
			o = op;
			a = args;
		}

		@Override
		public boolean evaluate(IBoolExpr[] args) {
			return o.evaluate(a);
		}

		@Override
		public int getNumArgs() {
			return 0;
		}

	}

	private class Inverted implements IBoolExpr {
		IBoolExpr o;

		public Inverted(IBoolExpr op) {
			o = op;
		}

		@Override
		public boolean evaluate(IBoolExpr[] args) {
			return !o.evaluate(null);
		}

		@Override
		public int getNumArgs() {
			return 0;
		}

	}
}
