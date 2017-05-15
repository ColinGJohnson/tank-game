package com.mygdx.tanks;

/**
 * Created by colin on 15-May-17.
 */
public class AndroidResolver implements PlatformResolver{
    PlatformInput androidInput;

    public AndroidResolver(){
        androidInput = new AndroidInput();
    }

    @Override
    public PlatformInput getPlatformInput() {
        return androidInput;
    }
}
