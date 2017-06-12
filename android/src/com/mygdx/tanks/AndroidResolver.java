package com.mygdx.tanks;

/**
 * Created by colin on 15-May-17.
 */
public class AndroidResolver implements PlatformResolver {
    PlatformInput androidInput;
    PlatformRender androidRenderer;

    public AndroidResolver(){
        androidInput = new AndroidInput();
        androidRenderer = new AndroidRender();
    }

    @Override
    public PlatformInput getPlatformInput() {
        return androidInput;
    }

    @Override
    public PlatformRender getPlatformRenderer() {
        return androidRenderer;
    }
}
