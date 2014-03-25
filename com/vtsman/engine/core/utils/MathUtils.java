package com.vtsman.engine.core.utils;

import com.badlogic.gdx.math.Vector2;

public class MathUtils {
	public static float dist(Vector2 p1, Vector2 p2){
		return (float)Math.pow((double)((p1.x - p2.x)*(p1.x - p2.x)+(p1.y - p2.y)*(p1.y - p2.y)), .5);
	}
	
	public static long factorial(long num){
		if(num == 1){
			return 1;
		}
		return factorial(num - 1) * num;
	}
	
	public static long triangular(long num){
		return num * (num + 1) / 2;
	}
	
	public static Vector2[] getBounds(float[] verts){
		Vector2[] vecs = new Vector2[2];
		vecs[0] = new Vector2(verts[0], verts[1]);
		vecs[1] = new Vector2(verts[0], verts[1]);
		for(int i = 0; i < verts.length; i+=2){
			if(vecs[0].x > verts[i]){
				vecs[0].x = verts[i];
			}
			if(vecs[1].x < verts[i]){
				vecs[1].x = verts[i];
			}
			if(vecs[0].y > verts[i+1]){
				vecs[0].y = verts[i+1];
			}
			if(vecs[1].y < verts[i+1]){
				vecs[1].y = verts[i+1];
			}
		}
		return vecs;
	}
}
