package com.mygdx.tanks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

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

    /**
     * Constructs a new BotTank.
     * @param x The X-Position of this bot.
     * @param y The Y-Position of this bot.
     * @param gameMap The map this bot is on.
     * @param difficulty The difficulty rating of this bot.
     */
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

    public void update() {

        // call update method in TankEntity class
        super.update();

        // create reference to target tank
        TankEntity target = getGameMap().getPlayerTank();

        // rotate gun to face target tankEntity
        rotateGunToPosition(target.getX() + target.getSprite().getWidth() / 2, target.getY() + target.getSprite().getHeight() / 2);
    }
} // BotTank
