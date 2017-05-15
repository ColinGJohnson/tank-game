package com.mygdx.tanks;

/**
 * Created by colin on 14-May-17.
 * A projectile to be fired by one of the tanks.
 */
public class Projectile extends Entity{
    public static final float PROJECTILE_SPEED = 3; // speed for projectiles (m/s)

    public Projectile(float x, float y, GameMap gameMap) {
        super(x, y, PROJECTILE_SPEED, gameMap);
    }

    /**
     *
     */
    public void collision(){

    }

    /**
     * Define projectile collision bounds.
     */
    public void defineBody(){

    } // DefineBody
} // Projectile
