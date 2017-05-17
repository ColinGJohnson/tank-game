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
                setSprite(new Sprite(new Texture("Kenny/Tanks/tankBlue.png")));
                break;
            case easy:
                setTankColor(TankColor.beige);
                setSprite(new Sprite(new Texture("Kenny/Tanks/tankBeige.png")));
                break;
            case medium:
                setTankColor(TankColor.black);
                setSprite(new Sprite(new Texture("Kenny/Tanks/tankBlack.png")));
                break;
            case hard:
                setTankColor(TankColor.red);
                setSprite(new Sprite(new Texture("Kenny/Tanks/tankRed.png")));
                break;
            default:
                difficulty = BotDifficulty.easy;
                setTankColor(TankColor.blue);
                setSprite(new Sprite(new Texture("Kenny/Tanks/tankBlue.png")));
        }
    } // BotTank Constructor

    /**
     *  Makes this bot do tank-ey stuff: move, shoot and look around.
     */
    public void update(){

    }
} // BotTank
