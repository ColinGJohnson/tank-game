package com.mygdx.tanks;

/**
 * Created by colin on 15-May-17.
 */
public class Constants {

    // physics
    public static final int PPM = 32; // pixels in screen space per meter in Box2D world (4m/tile)

    // game behaviors
    public static final int BOT_RANGE = 20; // how far bots can shoot from (m)
    public static final int BOT_DISTANCE = 10; // how close bots will try to get to the player (m)
    public static final int MAX_BOTS = 0; // the maximum number of bots allowed to be in play
    public static final int SPAWN_DELAY = 2000; // delay between spawn attempts (ms)

    // debug
    public static final boolean DEBUG = true; // show debug information
} // constants
