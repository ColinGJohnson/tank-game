package com.mygdx.tanks;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class TankGameMain extends Game {

    // Graphics
    private OrthographicCamera camera; // orthographic (2D) camera to follow player

    // Assets
	private SpriteBatch batch;
    private Texture dirtTile;
    private Texture grassTile;
    private Texture sandTile;
    private Texture greenTankBody;
    private Texture greenTankBarrel;

    // Logic
    private TiledMap tileMap;
    private GameMap gameMap;
    private PlayerTank playerTank;
	
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

        // define game map
        tileMap = new TiledMap();
        gameMap = new GameMap(tileMap);
	}

    /**
     * Update all game logic.
     */
    public void update(){

        // update entities
        playerTank.update();

        // process user input
        updateInput();
    }

    public void updateInput(){
        // NOTE: Input is platform-specific.
    }

    /**
     * Render loop called 60 times per second by LibGDX. Calls update() then
     * renders all game grapics.
     */
	@Override
	public void render () {

        // update game logic
        update();

        // clear entire screen with flat 50% grey
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw map tiles
		batch.begin();
		batch.draw(dirtTile, 0, 0);
        batch.draw(greenTankBody, 0, 0);
		batch.end();

        // draw tanks

        // draw projectiles

        // draw effects

        // draw GUI

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
