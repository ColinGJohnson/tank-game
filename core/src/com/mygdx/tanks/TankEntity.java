package com.mygdx.tanks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by colin on 14-May-17.
 * An entity representing a tank
 */
public class TankEntity extends Entity{
    private static final float TANK_SPEED = 10; // speed for tanks (m/s)
    private TankColor tankColor; // enum representing the color of this tank
    private Sprite gunSprite;
    private float gunRotation;

    private boolean destroyed = false; // has this tank been shot?

    // allowable tank colors
    public enum TankColor {
        beige,
        black,
        blue,
        green,
        red
    }

    /**
     * TankEntity Constructor.
     * @param x
     * @param y
     * @param tankColor The color of this tank.
     */
    public TankEntity(float x, float y, GameMap gameMap, TankColor tankColor){

        // call superclass constructor in Entity
        super(x,y, TANK_SPEED, gameMap);

        // set tank color
        this.tankColor = tankColor;

        // define the tank's collision bounds
        defineBody();

        // load appropriate gun sprite
        switch (tankColor) {
            case beige:
                setGunSprite(new Sprite(new Texture("Kenney/Tanks/barrelBeige.png")));
                break;
            case black:
                setGunSprite(new Sprite(new Texture("Kenney/Tanks/barrelBlack.png")));
                break;
            case blue:
                setGunSprite(new Sprite(new Texture("Kenney/Tanks/barrelBlue.png")));
                break;
            case green:
                setGunSprite(new Sprite(new Texture("Kenney/Tanks/barrelGreen.png")));
                break;
            case red:
                setGunSprite(new Sprite(new Texture("Kenney/Tanks/barrelRed.png")));
                break;
        }
    } // TankEntity Constructor

    /**
     * Moves tank according to specified inputs.
     * @param forward Should the tank move up?
     * @param back Should the tank move down?
     * @param left Should the tank rotate left?
     * @param right Should the tank rotate right?
     * @param target Point the tank's gun should be facing.
     */
    public void moveTank(boolean forward, boolean back, boolean left, boolean right, boolean shoot, Vector3 target){
        float horizontal = 0;
        float vertical = 0;

        // move tank forwards
        if (forward){
            horizontal += getV() * MathUtils.cosDeg(getRotation());
            vertical += getV() * MathUtils.sinDeg(getRotation());
        }

        // move tank backwards
        if (back){
            horizontal -= getV() * MathUtils.cosDeg(getRotation());
            vertical -= getV() * MathUtils.sinDeg(getRotation());
        }

        // rotate tank CCW
        if (left){
            setRotation(getRotation() + 1);
        }

        // rotate tank CW
        if (right){
            setRotation(getRotation() - 1);
        }

        // shoot
        if (shoot){
            getGameMap().getProjectiles().add(new ProjectileEntity(getX(), getY(), this, getGameMap()));
        }

        // aim tank gun
        rotateGunToPosition(target.x, target.y);
        getBody().setLinearVelocity(horizontal, vertical);
    } // moveTank

    /**
     * Rotates this player to face the given world point.
     * @param targetX The x-coordinate in the world to face.
     * @param targetY The y-coordinate in the world to face.
     */
    public void rotateGunToPosition(float targetX, float targetY){
        setGunRotation((float)(-Math.toDegrees(Math.atan2((getX() + getSprite().getWidth() / 2) - targetX, (getY() + getSprite().getHeight() / 2) - targetY))) + 180);
    } // rotateToPosition

    /**
     * Defines a Box2D body to handle collisions with this tank.
     */
    @Override
    public void defineBody(){
        Body tankBody;
        BodyDef def = new BodyDef();
        PolygonShape shape = new PolygonShape();

        // tanks are dynamic (as opposed to static, like walls or bushes)
        def.type = BodyDef.BodyType.DynamicBody;

        // set body position to spawn point
        def.position.set(getGameMap().getSpawn().x / Constants.PPM, getGameMap().getSpawn().y / Constants.PPM);

        // tank is allowed to rotate
        def.fixedRotation = true;

        // tank collisions determined by rectangular hit box
        // NOTE: width & height measured from center
        shape.setAsBox(75 / 2 / Constants.PPM, 70 / 2 / Constants.PPM);

        // add new tank body definition to game map
        tankBody = getGameMap().getWorld().createBody(def);

        // add rectangular shape to body
        tankBody.createFixture(shape, 1.0f);

        // attach this entity's unique id to body so it can be identified during collisions
        tankBody.setUserData(this.getUuid());

        // associate Box2D body reference in Entity class
        setBody(tankBody);

        // dispose of shape definition object to avoid memory leak
        shape.dispose();
    } // DefineBody

    public TankColor getTankColor() {
        return tankColor;
    }

    public void setTankColor(TankColor tankColor) {
        this.tankColor = tankColor;
    }

    public Sprite getGunSprite() {
        return gunSprite;
    }

    public void setGunSprite(Sprite gunSprite) {
        this.gunSprite = gunSprite;
    }

    public float getGunRotation() {
        return gunRotation;
    }

    public void setGunRotation(float gunRotation) {
        this.gunRotation = gunRotation;
        gunSprite.setRotation(gunRotation);
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
} // TankEntity
