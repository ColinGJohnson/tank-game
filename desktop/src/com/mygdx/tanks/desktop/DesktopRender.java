package com.mygdx.tanks.desktop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Colin on 2017-05-17.
 */

public class DesktopRender {
    public Stage stage;
    private Viewport viewPort;

    private int score;
    Label scoreLabel;

    public DesktopRender(SpriteBatch spriteBatch){
        score = 0;

        viewPort = new ScreenViewport();
        stage = new Stage(viewPort);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label(String.valueOf(score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(scoreLabel).padTop(30);

        stage.addActor(table);
    }
}
