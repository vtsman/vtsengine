package com.vtsman.engine.primitive.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureSheet {
	TextureRegion[][] textures;
	public TextureSheet(Texture t, int xPieces, int yPieces){
		textures = TextureRegion.split(t, t.getWidth() / xPieces, t.getHeight() / yPieces);
	}
	
	public TextureRegion getTexture(int x, int y){
		return textures[x][y];
	}
}
