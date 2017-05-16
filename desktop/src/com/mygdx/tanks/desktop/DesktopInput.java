package com.mygdx.tanks.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.tanks.PlatformInput;

/**
 * Created by colin on 15-May-17.
 */
public class DesktopInput implements PlatformInput {
    @Override
    public boolean quit() {
        return (Gdx.input.isKeyPressed(Input.Keys.ESCAPE));
    }

    @Override
    public boolean forward() {
        return (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP));
    }

    @Override
    public boolean back() {
        return (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN));
    }

    @Override
    public boolean left() {
        return (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT));
    }

    @Override
    public boolean right() {
        return (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT));
    }

    @Override
    public boolean shoot() {
        return (Gdx.input.isButtonPressed(Input.Buttons.LEFT) || Gdx.input.isKeyPressed(Input.Keys.SPACE));
    }

    @Override
    public Vector2 target() {
        return new Vector2(Gdx.input.getX(), Gdx.input.getY());
    }
}
