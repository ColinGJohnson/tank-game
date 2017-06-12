package com.mygdx.tanks;

/**
 * Created by colin on 14-May-17.
 * A tank controlled by a player.
 */
public class PlayerTank extends TankEntity {
    public static final TankColor PLAYER_COLOR = TankColor.green;
    private int score = 0;

    public PlayerTank(GameMap gameMap){
        super(gameMap.getSpawn().x, gameMap.getSpawn().y, gameMap, PLAYER_COLOR);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int amount) {
        this.score += amount;
    }
} // PlayerTank
