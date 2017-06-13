package com.mygdx.tanks.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.tanks.PlatformInput;
import com.mygdx.tanks.PlayScreen;

/**
 * Created by colin on 15-May-17.
 */
public class DesktopInput implements PlatformInput {
    @Override
    public boolean quit(PlayScreen playScreen) {
        return (Gdx.input.isKeyPressed(Input.Keys.ESCAPE));
    }

    @Override
    public boolean forward(PlayScreen playScreen) {
        return (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP));
    }

    @Override
    public boolean back(PlayScreen playScreen) {
        return (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN));
    }

    @Override
    public boolean left(PlayScreen playScreen) {
        return (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT));
    }

    @Override
    public boolean right(PlayScreen playScreen) {
        return (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT));
    }

    @Override
    public boolean shoot(PlayScreen playScreen) {
        return (Gdx.input.isButtonPressed(Input.Buttons.LEFT) || Gdx.input.isKeyPressed(Input.Keys.SPACE));
    }

    @Override
    public Vector2 target(PlayScreen playScreen) {
        return new Vector2(Gdx.input.getX(), Gdx.input.getY());
    }
}
