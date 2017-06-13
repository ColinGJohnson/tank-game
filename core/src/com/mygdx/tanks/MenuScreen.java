package com.mygdx.tanks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by colin on 22-May-17.
 */

public class MenuScreen implements Screen, InputProcessor {

    // logic
    private TankGame game;

    // ui
    Stage stage;
    Skin skin;
    private Table table;
    private TextButton startButton;
    private TextButton quitButton;
    private ImageButton settings;
    private ImageButton audioToggle;
    private ImageButton leaderBoards;
    private Label title;
    private Label credit;
    private Label credit2;

    public MenuScreen(TankGame game) {

        // initialize reference to main tank game class
        this.game = game;

        // load user interface skin
        skin = new Skin(Gdx.files.internal("kenneyUISkin.json"));

        // define the Stage that will be used to handle the user interface
        stage = new Stage(new ScreenViewport());

        // create a new table for layout
        table = new Table();

        // set the width of the layout table to the width of the screen
        table.setWidth(stage.getWidth());

        // align the table's contents in the center
        table.align(Align.center|Align.top);

        // position table relative to top left corner of screen
        table.setPosition(0, Gdx.graphics.getHeight());

        // initialize the ui elements
        title = new Label("Endless Tanks", skin, "title");
        startButton = new TextButton("Play", skin, "playButton");
        quitButton = new TextButton("Quit", skin);
        settings = new ImageButton(skin, "settings");
        leaderBoards = new ImageButton(skin, "leaderBoards");
        audioToggle = new ImageButton(skin, "audioToggle");
        credit = new Label("Code by colin johnson, 2017", skin, "credits");
        credit2 = new Label("Graphics and music by Kenney Vleugels and Kevin Macleod", skin, "credits");

        // tankGame reference for inner click event class below
        final TankGame tankGame = game;

        // add event listener to the start button so it can transition to the in-game state
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                // update the game stage
                tankGame.setScreen(tankGame.getScreenManager().getScreens().get(ScreenManager.GameState.Play));
            }
        });

        // add event listener to the quit button so it exits the app
        quitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                // exit the application
                Gdx.app.exit();
            }
        });

        table.padTop(200);

        // add the title label to the table layout and add spacing below it
        table.add(title).padBottom(10);

        // separate with a new row
        table.row();

        // add the 'play' button to the layout and add spacing below it
        table.add(startButton).padBottom(10);

        // separate the two buttons with a new row
        table.row();

        // add the 'quit' button to the layout
        table.add(quitButton).padBottom(10);

        // separate with a new row
        table.row();

        // add sound toggle, settings button, and leaderBoards button
        HorizontalGroup smallButtons = new HorizontalGroup();
        smallButtons.addActor(settings);
        smallButtons.addActor(audioToggle);
        smallButtons.addActor(leaderBoards);

        table.add(smallButtons);

        // separate with a new row
        table.row();

        // add the credits label to the table layout
        table.add(credit).padTop(15);

        // scale font down so credits are smaller
        skin.getFont("gameFontThin").getData().setScale(0.5f);

        // separate with a new row
        table.row();

        // add the second credits label to the table layout
        table.add(credit2);

        float width = Gdx.graphics.getWidth()*0.5f;
        float height = width / table.getWidth() * table.getHeight();
        table.setWidth(width);
        table.setHeight(height);

        // add the table layout to the scene
        stage.addActor(table);

        // use an InputMultiplexer to accept user input from both the stage and other sources
        InputMultiplexer inputMultiplexer = new InputMultiplexer(stage, this);
        Gdx.input.setInputProcessor(inputMultiplexer);
    } // MenuScreen Constructor

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        // clear entire screen with flat grey
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // update menu logic
        stage.act(Gdx.graphics.getDeltaTime());

        // draw menu elements
        stage.draw();
    } // render
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    } // resize

    @Override
    public void pause() {

    } // pause

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

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
} // MenuScreen
