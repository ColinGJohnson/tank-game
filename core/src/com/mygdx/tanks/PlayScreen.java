package com.mygdx.tanks;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by colin on 22-May-17.
 */

public class PlayScreen implements Screen, InputProcessor {

    // logic
    private TankGame game;
    private GameMap gameMap;

    // graphics
    private SpriteBatch batch;
    private OrthographicCamera camera; // orthographic (2D) camera to follow player
    private OrthogonalTiledMapRenderer tmxRenderer;
    private Box2DDebugRenderer box2DDebugRenderer;

    // hud graphics
    private SpriteBatch hudBatch;
    Stage hudStage;
    Skin hudSkin;

    public PlayScreen(TankGame game) {
        this.game = game;
    } // PlayScreen Constructor

    @Override
    public void show() {

        // define game and game map
        gameMap = new GameMap();

        // 2D camera to follow the player's tank
        //camera = new OrthographicCamera();
        //camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera = new OrthographicCamera(1920, 1080);
        camera.setToOrtho(false, 1920, 1080);
        camera.zoom = game.platformResolver.getPlatformRenderer().getPlatformZoom();

        // renderers to draw .tmx map files and Box2D world
        tmxRenderer = new OrthogonalTiledMapRenderer(gameMap.getTiledMap());
        tmxRenderer.setView(camera);
        box2DDebugRenderer = new Box2DDebugRenderer();

        // initialize sprite batch for drawing textures
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        // initialize sprite batch for drawing hud
        hudBatch = new SpriteBatch();

        // load user interface skin
        hudSkin = new Skin(Gdx.files.internal("kenneyUISkin.json"));

        // define the Stage that will be used to handle the user interface
        hudStage = new Stage(new ScreenViewport());
        hudStage = new Stage();

        // create reference to renderer appropriate for the current platform hud
        final PlatformRender render = game.platformResolver.getPlatformRenderer();

        // initialize platform hud stage
        render.initPlatformHUD(hudStage, hudSkin);

        // set the input processor to this stage to stop the menu buttons from still working
        InputMultiplexer inputMultiplexer = new InputMultiplexer(hudStage, this);
        Gdx.input.setInputProcessor(inputMultiplexer);
    } // show

    @Override
    /**
     * The render method for this game screen, contains all of the code to render the game while it
     * is being played.
     */
    public void render(float delta) {

        // update game logic
        update(Gdx.graphics.getDeltaTime());

        // clear entire screen with flat 50% grey
        Gdx.gl.glClearColor(0.74f, 0.53f, 0.34f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw map tiles
        tmxRenderer.setView(camera);
        tmxRenderer.render();

        // begin drawing with SpriteBatch
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // draw tank trail effects if on desktop (too slow for mobile)
        if(Gdx.app.getType() == Application.ApplicationType.Desktop){
            for (EffectEntity effect : gameMap.getEffects()){
                if (effect.getEffectType() == EffectEntity.EffectType.treadMark){
                    effect.getSprite().draw(batch);
                }
            }
        }

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

        // draw smoke effects
        for (EffectEntity effect : gameMap.getEffects()){
            if (effect.getEffectType() == EffectEntity.EffectType.smoke){
                effect.getSprite().draw(batch);
            }
        }

        // end drawing with SpriteBatch
        batch.end();

        // begin drawing hud
        hudBatch.begin();

        // create reference to renderer appropriate for the current platform hud
        final PlatformRender render = game.platformResolver.getPlatformRenderer();

        // render hud
        render.renderPlatformHUD(hudStage, gameMap.getPlayerTank().getScore());

        // end drawing hud
        hudBatch.end();

        // render box2d debug graphics
        if(Constants.DEBUG)box2DDebugRenderer.render(gameMap.getWorld(), camera.combined.scl(Constants.PPM));
    } // render

    /**
     * Update all in-game logic. Always called at the start of the render method.
     */
    public void update(float deltaT){

        // update map
        gameMap.update(deltaT);

        // create reference to an input handler appropriate for the current platform
        final PlatformInput input = game.platformResolver.getPlatformInput();

        // exit if requested
        if (input.quit(this)){
            Gdx.app.exit();
            System.exit(0);
        }

        // player input
        Vector3 playerTarget = camera.unproject(new Vector3(input.target(this).x, input.target(this).y, 0));

        // tank continually shoots if on mobile
        gameMap.playerTank.moveTank(input.forward(this), input.back(this), input.left(this), input.right(this), input.shoot(this), new Vector2(playerTarget.x, playerTarget.y));

        // update camera
        camera.position.set(gameMap.playerTank.getX(), gameMap.playerTank.getY(), 0);
        camera.update();
    } // update

    @Override
    public void resize(int width, int height) {

        // update camera with the application's new dimensions
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    } // resize

    @Override
    public void pause() {
        System.out.println("Pause");
    } // pause

    @Override
    public void resume() {
        System.out.println("Resume");
    } // resume

    @Override
    public void hide() {
        System.out.println("Hide");
        pause();
    } // hide

    @Override
    public void dispose() {
        batch.dispose();
        gameMap.getWorld().dispose();
    } // dispose

    public TankGame getGame() {
        return game;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

} // PlayScreen
