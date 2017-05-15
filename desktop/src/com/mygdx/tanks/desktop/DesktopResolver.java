package com.mygdx.tanks.desktop;

import com.mygdx.tanks.PlatformInput;
import com.mygdx.tanks.PlatformResolver;

/**
 * Created by colin on 15-May-17.
 */
public class DesktopResolver implements PlatformResolver {
    PlatformInput desktopInput;

    public DesktopResolver(){
        desktopInput = new DesktopInput();
    }

    @Override
    public PlatformInput getPlatformInput() {
        return desktopInput;
    }
}
