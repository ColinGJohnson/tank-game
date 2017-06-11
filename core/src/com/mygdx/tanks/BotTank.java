package com.mygdx.tanks;

import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by colin on 14-May-17.
 * A computer-controlled bot tank.
 */
public class BotTank extends TankEntity {
    private BotDifficulty difficulty;

    // possible types of bot tanks
    public enum BotDifficulty{
        random,
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

        // select a random difficulty if required
        if (this.difficulty == BotDifficulty.random){

            // assign random difficulty according to random integer 0, 1, 2, or 3
            Random rand = new Random();
            int randDifficulty = rand.nextInt(4);
            switch (randDifficulty){
                case 0:
                    this.difficulty = BotDifficulty.stationary;
                    break;
                case 1:
                    this.difficulty = BotDifficulty.easy;
                    break;
                case 2:
                    this.difficulty = BotDifficulty.medium;
                    break;
                case 3:
                    this.difficulty = BotDifficulty.hard;
                    break;
                default:
                    this.difficulty = BotDifficulty.stationary;
                    System.err.println("Invalid bot difficulty requested");
            }
        }

        // set correct color and speed according to bot difficulty
        switch (this.difficulty){
            case stationary:
                setTankColor(TankColor.blue);
                setV(0);
                break;
            case easy:
                setTankColor(TankColor.beige);
                setV(getV()/3f);
                break;
            case medium:
                setTankColor(TankColor.red);
                setV(getV()/2f);
                break;
            case hard:
                setTankColor(TankColor.black);
                break;
        }

        // spawn with random rotation
        Random rand = new Random();
        //setRotation(rand.nextInt(360));
    } // BotTank Constructor

    public void update() {
        boolean forward = false;
        boolean backward = false;
        boolean left = false;
        boolean right = false;
        boolean shoot = false;

        // call update method in TankEntity class
        super.update();

        // create reference to target tank
        TankEntity target = getGameMap().getPlayerTank();

        // set target position to later shoot at
        Vector2 targetPosition = new Vector2(target.getX() + target.getSprite().getWidth() / 2, target.getY() + target.getSprite().getHeight() / 2);

        // calculate bot -> target distance
        float d = (float)(Math.sqrt(Math.pow(target.getBody().getPosition().x - getBody().getPosition().x, 2) + Math.pow(target.getBody().getPosition().y - getBody().getPosition().y, 2)));

        // calculate angle needed to rotate to in order to face the player tank
        float rotateAngle = rotateTankToPosition(targetPosition.x, targetPosition.y);
        if(rotateAngle < 0){
            rotateAngle += 360;
        }

        // get adjusted tank rotation for AI cacluations (lowest coterminal angle)
        float rotation = getRotation();
        if (getRotation() > 360f){
            rotation %= 360f;
        } else if (getRotation() < 0){
            rotation = rotation + (360f * ((int)(-rotation / 360) + 1));
        }

        // move towards player if not already too close
        if (d > Constants.BOT_DISTANCE) {

            // drive forward/backwards towards player
            if ((rotation - rotateAngle > 90 && rotation - rotateAngle < 180) || (rotation - rotateAngle > -270 && rotation - rotateAngle < -90)) {
                backward = true;
            } else {
                forward = true;
            }
        }

        // actions dependant on bot difficulty
        switch (difficulty){
            case stationary: // stationary tanks do not move, they just shoot
                // do nothing, these tanks are dumb
                break;
            case easy: // easy tanks move towards the player slowly

                // rotate to face player if not already too close
                if (d > Constants.BOT_DISTANCE) {

                    // rotate to face player if needed
                    if (Math.abs(rotation - rotateAngle) > 1) {
                        if (rotation - rotateAngle > 0) {
                            right = true;
                        } else if (rotation - rotateAngle < 0) {
                            left = true;
                        }
                    }
                }
                break;
            case medium: // medium tanks move towards the player fast && at an angle

                // rotate to face player if not already too close
                if (d > Constants.BOT_DISTANCE) {

                    // rotate to face player if needed
                    if (Math.abs(rotation + 45 - rotateAngle) > 1) {
                        if (rotation + 45 - rotateAngle > 0) {
                            right = true;
                        } else if (rotation + 45 - rotateAngle < 0) {
                            left = true;
                        }
                    }
                }
                break;
            case hard: // hard tanks lead their shots + medium tank stuff

                // rotate to face player if not already too close
                if (d > Constants.BOT_DISTANCE) {

                    // rotate to face player if needed
                    if (Math.abs(rotation + 45 - rotateAngle) > 1) {
                        if (rotation + 45 - rotateAngle > 0) {
                            right = true;
                        } else if (rotation - 45 - rotateAngle < 0) {
                            left = true;
                        }
                    }
                }

                // aim ahead if target tank is moving
                if (target.getBody().getLinearVelocity().x > 0 || target.getBody().getLinearVelocity().y > 0){

                    float t = d / (ProjectileEntity.PROJECTILE_SPEED);
                    targetPosition.x += target.getBody().getLinearVelocity().x * t * Constants.PPM;
                    targetPosition.y += target.getBody().getLinearVelocity().y * t * Constants.PPM;
                }
                break;
        }

        // shoot only if the player tank is within range
        if (d < Constants.BOT_RANGE){
            shoot = true;
        }

        moveTank(forward, backward, left, right, shoot, targetPosition);

        // rotate gun to face target tankEntity
        rotateGunToPosition(targetPosition.x, targetPosition.y);
    } // update

    private float rotateTankToPosition(float targetX, float targetY){
        return (float)(-Math.toDegrees(Math.atan2((getX() + getSprite().getWidth() / 2) - targetX, (getY() + getSprite().getHeight() / 2) - targetY))) - 90;
    }
} // BotTank
