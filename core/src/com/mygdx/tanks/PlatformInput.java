package com.mygdx.tanks;

/**
 * Created by colin on 15-May-17.
 */
public interface PlatformInput {
    public boolean updateInput();
    public boolean up();
    public boolean down();
    public boolean left();
    public boolean right();
    public boolean target();
}
