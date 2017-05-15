package com.mygdx.tanks.desktop;

import com.mygdx.tanks.PlatformInput;

/**
 * Created by colin on 15-May-17.
 */
public class DesktopInput implements PlatformInput {
    @Override
    public boolean updateInput() {
        return false;
    }

    @Override
    public boolean up() {
        return false;
    }

    @Override
    public boolean down() {
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
    public boolean target() {
        return false;
    }
}
