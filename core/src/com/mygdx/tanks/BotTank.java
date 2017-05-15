package com.mygdx.tanks;

/**
 * Created by colin on 14-May-17.
 * A computer-controlled bot tank.
 */
public class BotTank extends TankEntity{
    private BotDifficulty difficulty;

    // possible types of bot tanks
    enum BotDifficulty{
        stationary,
        easy,
        medium,
        hard
    }

    public BotTank(float x, float y, GameMap gameMap, BotDifficulty difficulty){
        super(x, y, gameMap, TankColor.blue);

        // init variables
        this.difficulty = difficulty;

        // set correct color according to bot difficulty
        switch (difficulty){
            case stationary:
                setTankColor(TankColor.blue);
                break;
            case easy:
                setTankColor(TankColor.beige);
                break;
            case medium:
                setTankColor(TankColor.black);
                break;
            case hard:
                setTankColor(TankColor.red);
                break;
            default:
                difficulty = BotDifficulty.easy;
                setTankColor(TankColor.blue);
        }
    } // BotTank Constructor
} // BotTank
