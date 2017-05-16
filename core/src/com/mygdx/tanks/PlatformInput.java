package com.mygdx.tanks;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by colin on 15-May-17.
 */
public interface PlatformInput {
    public boolean quit();
    public boolean forward();
    public boolean back();
    public boolean left();
    public boolean right();
    public boolean shoot();
    public Vector2 target();
}
