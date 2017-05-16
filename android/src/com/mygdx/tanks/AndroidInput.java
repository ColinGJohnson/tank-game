package com.mygdx.tanks;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by colin on 15-May-17.
 */
public class AndroidInput implements PlatformInput {

    @Override
    public boolean quit() {
        return false;
    }

    @Override
    public boolean forward() {
        return false;
    }

    @Override
    public boolean back() {
        return false;
    }

    @Override
    public boolean left() {
        return false;
    }

    @Override
    public boolean right() {
        return false;
    }

    @Override
    public boolean shoot() {
        return false;
    }

    @Override
    public Vector2 target() {
        return null;
    }
}
