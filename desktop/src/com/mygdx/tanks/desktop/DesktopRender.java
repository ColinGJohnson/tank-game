package com.mygdx.tanks.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.mygdx.tanks.PlatformRender;

/**
 * Created by Colin on 2017-05-17.
 */

public class DesktopRender implements PlatformRender {

    private OrthographicCamera camera;
    private Stage stage;
    private SpriteBatch batch;
    private Touchpad touchpad;
    private Skin skin;
    private float blockSpeed;

    public DesktopRender() {
        batch = new SpriteBatch();
        //Create camera
        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 10f*aspectRatio, 10f);

        // load user interface skin
        skin = new Skin(Gdx.files.internal("kenneyUISkin.json"));

        //Create new TouchPad with the created style
        touchpad = new Touchpad(10, skin);
        //setBounds(x,y,width,height)
        touchpad.setBounds(15, 15, 200, 200);

        //Create a Stage and add TouchPad
        stage = new Stage();
        stage.addActor(touchpad);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void renderPlatformHUD() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void renderPlatformMenu() {

    }

    @Override
    public void renderPlatformPause() {

    }
}
