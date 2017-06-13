package com.mygdx.tanks.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.tanks.PlatformRender;
import com.mygdx.tanks.TankGame;

/**
 * Created by Colin on 2017-05-17.
 */

public class DesktopRender implements PlatformRender {

    // hud
    Stage stage;
    Skin skin;
    private Table table;
    private Label score;

    public DesktopRender(Skin skin, ScreenViewport screenViewport) {

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

        score = new Label("Score: 0", skin, "title");

        table.add(score);

        stage.addActor(table);
    }

    @Override
    public void renderPlatformHUD() {
        // update logic
        stage.act(Gdx.graphics.getDeltaTime());

        // draw elements
        stage.draw();
    }

    @Override
    public void renderPlatformMenu() {

    }

    @Override
    public void renderPlatformPause() {

    }
}
