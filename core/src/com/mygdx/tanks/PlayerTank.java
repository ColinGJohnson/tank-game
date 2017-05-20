package com.mygdx.tanks;

/**
 * Created by colin on 14-May-17.
 * A tank controlled by a player.
 */
public class PlayerTank extends TankEntity{
    public static final TankEntity.TankColor PLAYER_COLOR = TankColor.green;

    public PlayerTank(GameMap gameMap){
        super(gameMap.getSpawn().x, gameMap.getSpawn().y, gameMap, PLAYER_COLOR);
    }
} // PlayerTank
