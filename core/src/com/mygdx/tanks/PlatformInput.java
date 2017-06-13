package com.mygdx.tanks;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by colin on 15-May-17.
 */
public interface PlatformInput {
    public boolean quit(PlayScreen playScreen);
    public boolean forward(PlayScreen playScreen);
    public boolean back(PlayScreen playScreen);
    public boolean left(PlayScreen playScreen);
    public boolean right(PlayScreen playScreen);
    public boolean shoot(PlayScreen playScreen);
    public Vector2 target(PlayScreen playScreen);
}
