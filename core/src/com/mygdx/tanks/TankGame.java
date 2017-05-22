package com.mygdx.tanks;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mygdx.tanks.Utils.Constants;

public class TankGame extends Game {

    // cross-platform capabilities
    public static PlatformResolver platformResolver = null;

    // assets
    AssetManager assets = new AssetManager();

    @Override
    public void create () {

        // load assets

        // create new ScreenManager to handle switching between the menu and actual game
        ScreenManager screenManager = new ScreenManager(this);

        // start on menu screen
        //setScreen(screenManager.getScreens().get(ScreenManager.GameState.Menu));

        setScreen(screenManager.getScreens().get(ScreenManager.GameState.Play));
    } // create

    /**
     * Render loop called 60 times per second by LibGDX. Calls update() then
     * renders all game grapics.
     */
    @Override
    public void render () {

        // render all screens
        super.render();
    } // render

    @Override
    public void dispose () {
        //TODO: Dispose assets
    } // dispose

    @Override
    public void resize(int width, int height){

        // call current screen's resize method
        super.resize(width, height);
    } // resize

    public static PlatformResolver getPlatformResolver() {
        return platformResolver;
    } // getPlatformResolver

    public static void setPlatformResolver(PlatformResolver platformResolver) {
        platformResolver = platformResolver;
    } // setPlatformResolver
} // TankGame
