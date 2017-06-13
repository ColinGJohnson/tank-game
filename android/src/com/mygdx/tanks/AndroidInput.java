package com.mygdx.tanks;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by colin on 15-May-17.
 */
public class AndroidInput implements PlatformInput {

    @Override
    public boolean quit(PlayScreen playScreen) {
        return false;
    }

    @Override
    public boolean forward(PlayScreen playScreen) {
        if (((AndroidRender)playScreen.getGame().platformResolver.getPlatformRenderer()).getMovementPad().getKnobPercentY() > 0.3){
            return true;
        }
        return false;
    }

    @Override
    public boolean back(PlayScreen playScreen) {
        if (((AndroidRender)playScreen.getGame().platformResolver.getPlatformRenderer()).getMovementPad().getKnobPercentY() < -0.3){
            return true;
        }
        return false;
    }

    @Override
    public boolean left(PlayScreen playScreen) {
        if (((AndroidRender)playScreen.getGame().platformResolver.getPlatformRenderer()).getMovementPad().getKnobPercentX() < -0.2){
            return true;
        }
        return false;
    }

    @Override
    public boolean right(PlayScreen playScreen) {
        if (((AndroidRender)playScreen.getGame().platformResolver.getPlatformRenderer()).getMovementPad().getKnobPercentX() > 0.2){
            return true;
        }
        return false;
    }

    @Override
    public boolean shoot(PlayScreen playScreen) {

        // tank is continually shooting on mobile
        return true;
    }

    @Override
    public Vector2 target(PlayScreen playScreen) {
        return new Vector2((Gdx.graphics.getWidth()/2f) + (200*((AndroidRender)playScreen.getGame().platformResolver.getPlatformRenderer()).getGunPad().getKnobPercentX()),
                (Gdx.graphics.getHeight()/2f) - (200*((AndroidRender)playScreen.getGame().platformResolver.getPlatformRenderer()).getGunPad().getKnobPercentY()));
    }
}
