package com.mygdx.tanks;

/**
 * Created by colin on 14-May-17.
 * A tank controlled by a player.
 */
public class PlayerTank extends TankEntity{
    public static final TankEntity.TankColor PLAYER_COLOR = TankColor.green;

    public PlayerTank(float x, float y, GameMap gameMap){
        super(x, y, gameMap, PLAYER_COLOR);
    }

    public void update(){

    }
} // PlayerTank
