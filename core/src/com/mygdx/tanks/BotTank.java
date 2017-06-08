package com.mygdx.tanks;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by colin on 14-May-17.
 * A computer-controlled bot tank.
 */
public class BotTank extends TankEntity {
    private BotDifficulty difficulty;

    // possible types of bot tanks
    public enum BotDifficulty{
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
        boolean forward = false;
        boolean backward = false;
        boolean left = false;
        boolean right = false;

        // call update method in TankEntity class
        super.update();

        // create reference to target tank
        TankEntity target = getGameMap().getPlayerTank();

        // set target position to later shoot at
        Vector2 targetPosition = new Vector2(target.getX() + target.getSprite().getWidth() / 2, target.getY() + target.getSprite().getHeight() / 2);

        // calculate bot -> target distance
        float d = (float)(Math.sqrt(Math.pow(target.getBody().getPosition().x - getBody().getPosition().x, 2) + Math.pow(target.getBody().getPosition().y - getBody().getPosition().y, 2)));

        // calculate angle to target


        // actions dependant on bot difficulty
        switch (difficulty){
            case stationary: // stationary tanks do not move
                // do nothing, these tanks are dumb
                break;
            case easy: // easy tanks move towards the player

                // move towards player if not already too close
                if (d > Constants.BOT_DISTANCE) {

                    // rotate tank to face player
                    if(target.getRotation() > getRotation()){
                        right = true;
                    } else {
                        left = true;
                    }

                    // drive forward/backwards towards player
                    if(target.getRotation()/360 - getRotation()/360 > 180){
                        backward = true;
                    } else {
                        forward = true;
                    }
                }

                break;
            case medium: // medium tanks circle the player

                // move towards player if not already too close
                if (d < Constants.BOT_DISTANCE) {

                    // rotate tank to face ahead of player

                    // drive towards player in a circle
                }

                break;
            case hard: // hard tanks circle the player and lead their shots

                // move towards player if not already too close
                if (d < Constants.BOT_DISTANCE) {

                    // rotate tank to face ahead of player

                    // drive towards player in a circle
                }

                // aim ahead if target tank is moving
                if (target.getBody().getLinearVelocity().x > 0 || target.getBody().getLinearVelocity().y > 0){

                    float t = d / (getV() * getBody().getLinearDamping());
                    targetPosition.x += target.getBody().getLinearVelocity().x * t * Constants.PPM;
                    targetPosition.y += target.getBody().getLinearVelocity().y * t * Constants.PPM;
                }
                break;
        }

        moveTank(forward, backward, left, right, false, targetPosition);

        // rotate gun to face target tankEntity
        rotateGunToPosition(targetPosition.x, targetPosition.y);
    } // update
} // BotTank
