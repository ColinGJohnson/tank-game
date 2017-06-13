package com.mygdx.tanks;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Colin on 2017-05-17.
 */

public interface PlatformRender {
    public void initPlatformHUD(Stage hudStage, Skin hudSkin);
    public void renderPlatformHUD(Stage hudStage, int Score);
    public void renderPlatformGameOver(Stage endStage, int Score);
    public void renderPlatformMenu();
    public void renderPlatformPause();
    public float getPlatformZoom();
}
