package com.mygdx.tanks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Colin on 2017-05-17.
 */

public class AndroidRender implements PlatformRender {

    // hud
    private Table table;
    private Label scoreLabel;

    // On screen controls
    private Table leftHudTable;
    private Table rightHudTable;
    private Touchpad movementPad;
    private Touchpad gunPad;
    private Button fireButton;

    @Override
    public void initPlatformHUD(Stage hudStage, Skin hudSkin) {

        // create a new table for layout
        table = new Table();

        // set the width of the layout table to the width of the screen
        table.setWidth(hudStage.getWidth());

        // align the table's contents in the center
        table.align(Align.center|Align.top);

        // position table relative to top left corner of screen
        table.setPosition(0, Gdx.graphics.getHeight());

        // create and add label for game score
        scoreLabel = new Label("Score: 0", hudSkin, "title");
        table.add(scoreLabel);


        leftHudTable = new Table();
        leftHudTable.align(Align.bottomLeft);
        movementPad = new Touchpad(10, hudSkin);
        leftHudTable.add(movementPad).pad(30);

        // add table layouts to scene2d hud stage
        hudStage.addActor(table);
        hudStage.addActor(leftHudTable);
    } // initPlatformHUD

    @Override
    public void renderPlatformHUD(Stage hudStage, int score) {

        scoreLabel.setText("Score: " + score);

        // update logic
        hudStage.act(Gdx.graphics.getDeltaTime());

        // draw elements
        hudStage.draw();
    } // renderPlatformHUD

    @Override
    public void renderPlatformGameOver(Stage endStage, int score) {

    }

    @Override
    public void renderPlatformMenu() {

    } // renderPlatformMenu

    @Override
    public void renderPlatformPause() {

    } // renderPlatformPause

    @Override
    public float getPlatformZoom() {
        //return (1280/Gdx.graphics.getWidth() * 2f);
        return 1f;
    }
}
