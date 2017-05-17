package com.mygdx.tanks;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class TankGame extends Game {

    // cross-platform
    public static PlatformResolver platformResolver = null;

    // graphics
    private SpriteBatch batch;
    private OrthographicCamera camera; // orthographic (2D) camera to follow player
    private OrthogonalTiledMapRenderer tmxRenderer;
    private Box2DDebugRenderer box2DDebugRenderer;

    // assets
    private Texture dirtTile;
    private Texture grassTile;
    private Texture sandTile;
    private Texture greenTankBody;
    private Texture greenTankBarrel;

    // logic
    private TiledMap tileMap;
    private GameMap gameMap;

    // debug
    public static boolean debug = true;

    @Override
    public void create () {

        // define game map
        gameMap = new GameMap();

        // 2D camera to follow the player's tank
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = 1f;

        // renderers to draw .tmx map files and Box2D world
        tmxRenderer = new OrthogonalTiledMapRenderer(gameMap.getTiledMap());
        tmxRenderer.setView(camera);
        box2DDebugRenderer = new Box2DDebugRenderer();

        // sprite batch for drawing textures
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        // load textures from 'android/assets' folder
        dirtTile = new Texture("Kenney/Environment/dirt.png");
        grassTile = new Texture("Kenney/Environment/grass.png");
        sandTile = new Texture("Kenney/Environment/sand.png");
        greenTankBody = new Texture("Kenney/Tanks/tankGreen.png");
        greenTankBarrel = new Texture("Kenney/Tanks/tankGreen.png");
    }

    /**
     * Update all game logic.
     */
    public void update(float deltaT){

        // update map
        gameMap.update();

        // update player
        gameMap.playerTank.update();

        // process user input
        updateInput();

        // update camera
        camera.position.set(gameMap.playerTank.getX(), gameMap.playerTank.getY(), 0);
        camera.update();
    } // update

    private void updateInput(){
        final PlatformInput input = platformResolver.getPlatformInput();

        // exit if requested
        if (input.quit()){
            Gdx.app.exit();
            System.exit(0);
        }

        // player input
        Vector3 targetUnproject = camera.unproject(new Vector3(input.target().x, input.target().y, 0));
        gameMap.playerTank.moveTank(input.forward(), input.back(), input.left(), input.right(), input.shoot(), targetUnproject);
    } // updateInput

    /**
     * Render loop called 60 times per second by LibGDX. Calls update() then
     * renders all game grapics.
     */
    @Override
    public void render () {

        // update game logic
        update(Gdx.graphics.getDeltaTime());

        // clear entire screen with flat 50% grey
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw map tiles
        tmxRenderer.setView(camera);
        tmxRenderer.render();

        // begin drawing with SpriteBatch
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // draw player tank
        gameMap.playerTank.getSprite().draw(batch);
        gameMap.playerTank.getGunSprite().draw(batch);
        batch.end();

        // draw bot tanks

        // draw projectiles

        // draw effects

        // draw GUI

        // render box2d debug graphics
        if(debug)box2DDebugRenderer.render(gameMap.getWorld(), camera.combined.scl(Constants.PPM));
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

    @Override
    public void resize(int width, int height){
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public static PlatformResolver getPlatformResolver() {
        return platformResolver;
    }

    public static void setPlatformResolver(PlatformResolver platformResolver) {
        platformResolver = platformResolver;
    }
}
