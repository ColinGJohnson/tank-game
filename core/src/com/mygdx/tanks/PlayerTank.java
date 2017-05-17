package com.mygdx.tanks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by colin on 14-May-17.
 * A tank controlled by a player.
 */
public class PlayerTank extends TankEntity{
    public static final TankEntity.TankColor PLAYER_COLOR = TankColor.green;

    public PlayerTank(GameMap gameMap){
        super(gameMap.getSpawn().x, gameMap.getSpawn().y, gameMap, PLAYER_COLOR);

        // set sprite
        setSprite(new Sprite(new Texture("Kenney/Tanks/tankGreen.png")));
    }

    public void update(){

        // update screen coordinates based on Box2D world
        setX(getBody().getPosition().x * Constants.PPM - getSprite().getWidth() / 2);
        setY(getBody().getPosition().y * Constants.PPM - getSprite().getHeight() / 2);

        // update sprite location based on screen coordinates
        getSprite().setX(getX());
        getSprite().setY(getY());

        // update gun sprite location and rotation origin
        getGunSprite().setX(getX() + getSprite().getWidth() / 2 - getGunSprite().getWidth() / 2);
        getGunSprite().setY(getY() + getSprite().getHeight() / 2 + 10);
        getGunSprite().setOrigin(getGunSprite().getWidth() / 2, - 10);
    }
} // PlayerTank
