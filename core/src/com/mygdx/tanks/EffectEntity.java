package com.mygdx.tanks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by colin on 11-Jun-17.
 */

public class EffectEntity extends Entity{
    private long timeCreated;
    private int duration;

    enum EffectType {
        smoke,
        treadMark
    }

    public EffectEntity(float x, float y, GameMap gameMap, EffectType effectType, int duration) {

        // call Entity constructor
        super(x, y, 0, gameMap);

        // init instance variables
        this.duration = duration;

        // record time created
        timeCreated = System.currentTimeMillis();

        // select correct sprite
        switch (effectType) {
            case smoke:
                setSprite(new Sprite(new Texture("Kenney/Smoke/smokeGrey0.png")));
                break;
            case treadMark:
                setSprite(new Sprite(new Texture("Kenney/Tanks/tracksSmallSingle.png")));
        }
    }

    @Override
    public void defineBody() {
        /* NO PHYSICS NEEDED */
    }
}
