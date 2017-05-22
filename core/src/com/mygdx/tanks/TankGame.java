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

public class TankGame extends Game {

    // cross-platform
    public static PlatformResolver platformResolver = null;

    // graphics
    private SpriteBatch batch;
    private OrthographicCamera camera; // orthographic (2D) camera to follow player
    private OrthogonalTiledMapRenderer tmxRenderer;
    private Box2DDebugRenderer box2DDebugRenderer;

    // assets
    AssetManager assets = new AssetManager();

    // logic
    private Game game;
    private GameMap gameMap;

    // debug
    public static boolean debug = true;

    @Override
    public void create () {

        // define game and game map
        game = this;
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

        // add bot tanks
        gameMap.getBots().add(new BotTank(gameMap.getSpawn().x, gameMap.getSpawn().y, gameMap, BotTank.BotDifficulty.easy));
        //gameMap.getBots().add(new BotTank(gameMap.getSpawn().x, gameMap.getSpawn().y, gameMap, BotTank.BotDifficulty.medium));
        //gameMap.getBots().add(new BotTank(gameMap.getSpawn().x, gameMap.getSpawn().y, gameMap, BotTank.BotDifficulty.stationary));
        //gameMap.getBots().add(new BotTank(gameMap.getSpawn().x, gameMap.getSpawn().y, gameMap, BotTank.BotDifficulty.hard));

        // start on menu screen
        setScreen(new PlayScreen(this));
    } // create

    /**
     * Update all game logic. Always called at the start of the render method.
     */
    public void update(float deltaT){

        // update map
        gameMap.update(deltaT);

        // create reference to an input handler appropriate for the current platform
        final PlatformInput input = platformResolver.getPlatformInput();

        // exit if requested
        if (input.quit()){
            Gdx.app.exit();
            System.exit(0);
        }

        // player input
        Vector3 playerTarget = camera.unproject(new Vector3(input.target().x, input.target().y, 0));
        gameMap.playerTank.moveTank(input.forward(), input.back(), input.left(), input.right(), input.shoot(), new Vector2(playerTarget.x, playerTarget.y));

        // update camera
        camera.position.set(gameMap.playerTank.getX(), gameMap.playerTank.getY(), 0);
        camera.update();
    } // update

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

        // draw projectiles
        for(ProjectileEntity projectileEntity: gameMap.getProjectiles()){
            projectileEntity.getSprite().draw(batch);
        }

        // draw player tank
        gameMap.playerTank.getSprite().draw(batch);
        gameMap.playerTank.getGunSprite().draw(batch);

        // draw bot tanks
        for (BotTank botTank : gameMap.getBots()){
            botTank.getSprite().draw(batch);
            botTank.getGunSprite().draw(batch);
        }

        // end drawing with SpriteBatch
        batch.end();

        // render box2d debug graphics
        if(debug)box2DDebugRenderer.render(gameMap.getWorld(), camera.combined.scl(com.mygdx.tanks.Utils.Constants.PPM));

        // render all screens
        super.render();
    } // render

    @Override
    public void dispose () {
        //TODO: Dispose sprites
        batch.dispose();
        gameMap.getWorld().dispose();
    } // dispose

    @Override
    public void resize(int width, int height){
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    } // resize

    public static PlatformResolver getPlatformResolver() {
        return platformResolver;
    } // getPlatformResolver

    public static void setPlatformResolver(PlatformResolver platformResolver) {
        platformResolver = platformResolver;
    } // setPlatformResolver
} // TankGame
