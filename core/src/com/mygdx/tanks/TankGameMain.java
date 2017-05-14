package com.mygdx.tanks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TankGameMain extends ApplicationAdapter {
	SpriteBatch batch;
	Texture dirtTile;
	Texture grassTile;
    Texture sandTile;
    Texture greenTankBody;
    Texture greenTankBarrel;
	
	@Override
	public void create () {

        // sprite batch for drawing textures
		batch = new SpriteBatch();

        // load textures from 'android/assets' folder
        dirtTile = new Texture("Kenny/Environment/dirt.png");
        grassTile = new Texture("Kenny/Environment/grass.png");
        sandTile = new Texture("Kenny/Environment/sand.png");

        greenTankBody = new Texture("Kenny/Tanks/tankGreen.png");
        greenTankBarrel = new Texture("Kenny/Tanks/tankGreen.png");
	}

	@Override
	public void render () {

        // clear entire screen with flat 50% grey
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // begin drawing images
		batch.begin();
		batch.draw(dirtTile, 0, 0);
        batch.draw(greenTankBody, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		dirtTile.dispose();
        grassTile.dispose();
        sandTile.dispose();
        greenTankBody.dispose();
        greenTankBarrel.dispose();
	}
}
