package com.mygdx.tanks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.util.ArrayList;

/**
 * Created by colin on 14-May-17.
 * An entity representing a tank
 */
public class TankEntity extends Entity {
    private static final float TANK_SPEED = 2; // speed for tanks
    private static final int FIRING_DELAY = 400; // delay between shots (ms)

    private TankColor tankColor; // enum representing the color of this tank
    private Sprite gunSprite;
    private float gunRotation;

    private long lastShot = 0; // system time in milliseconds that this tank last shot
    private boolean destroyed = false; // has this tank been shot?

    private ArrayList<Sprite> treadSprites = new ArrayList<Sprite>(); // tread marks from this tank

    // allowable tank colors
    public enum TankColor {
        beige,
        black,
        blue,
        green,
        red
    } // TankColor

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

        // load appropriate gun sprite and tank sprite
        setTankColor(tankColor);

        // define the tank's collision bounds
        defineBody();
    } // TankEntity Constructor

    public void update(){

        // update screen coordinates based on Box2D world
        setX(getBody().getPosition().x * Constants.PPM - getSprite().getWidth() / 2);
        setY(getBody().getPosition().y * Constants.PPM - getSprite().getHeight() / 2);

        // update sprite rotation based on Box2D world
        setRotation((float)(getBody().getAngle() * 180 / Math.PI));

        // update sprite location based on screen coordinates
        getSprite().setX(getX());
        getSprite().setY(getY());

        // update gun sprite location and rotation origin
        getGunSprite().setX(getX() + getSprite().getWidth() / 2 - getGunSprite().getWidth() / 2);
        getGunSprite().setY(getY() + getSprite().getHeight() / 2 + 10);
        getGunSprite().setOrigin(getGunSprite().getWidth() / 2, - 10);
    } // update

    /**
     * Moves tank according to specified inputs.
     * @param forward Should the tank move up?
     * @param backwards Should the tank move down?
     * @param left Should the tank rotate left?
     * @param right Should the tank rotate right?
     * @param target Point the tank's gun should be facing.
     */
    public void moveTank(boolean forward, boolean backwards, boolean left, boolean right, boolean shoot, Vector2 target){
        float horizontal = 0;
        float vertical = 0;

        // move tank forwards
        if (forward){
            horizontal += getV() * MathUtils.cosDeg(getRotation());
            vertical += getV() * MathUtils.sinDeg(getRotation());
        }

        // move tank backwards
        if (backwards){
            horizontal -= getV() * MathUtils.cosDeg(getRotation());
            vertical -= getV() * MathUtils.sinDeg(getRotation());
        }

        // apply tank movement to physics body
        getBody().setLinearVelocity(getBody().getLinearVelocity().x + horizontal, getBody().getLinearVelocity().y + vertical);

        float rotation = 0;

        // rotate tank CCW
        if (left){
            rotation += 0.25;
        }

        // rotate tank CW
        if (right){
            rotation -= 0.25;
        }

        // apply tank rotation to physics body
        getBody().setAngularVelocity(getBody().getAngularVelocity() + rotation);

        // shoot
        if (shoot && lastShot + FIRING_DELAY < System.currentTimeMillis()){
            lastShot = System.currentTimeMillis();
            getGameMap().getProjectiles().add(new ProjectileEntity(this, getGameMap()));
        }

        // aim tank gun
        rotateGunToPosition(target.x, target.y);
    } // moveTank

    /**
     * Rotates this player to face the given world point.
     * @param targetX The x-coordinate in the world to face.
     * @param targetY The y-coordinate in the world to face.
     */
    public void rotateGunToPosition(float targetX, float targetY){
        setGunRotation((float)( -Math.toDegrees(Math.atan2((getX() + getSprite().getWidth() / 2) - targetX, (getY() + getSprite().getHeight() / 2) - targetY))) + 180);
    } // rotateGunToPosition

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

        // tank is allowed to rotate on its own
        def.fixedRotation = false;

        // set friction with ground so tank will not slide forever
        def.linearDamping = 10f;

        // set angular dampening so tank will not spin forever
        def.angularDamping = 10f;

        // tank collisions determined by rectangular hit box
        // NOTE: width & height measured from center
        shape.setAsBox(getSprite().getHeight() / 2 / Constants.PPM, getSprite().getWidth() / 2 / Constants.PPM);

        // add new tank body definition to game map
        tankBody = getGameMap().getWorld().createBody(def);

        // add rectangular shape to body
        tankBody.createFixture(shape, 1.0f);

        // attach this entity's unique id to body so it can be identified during collisions
        tankBody.setUserData(this);

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

        switch (tankColor) {
            case beige:
                setGunSprite(new Sprite(new Texture("Kenney/Tanks/barrelBeige.png")));
                setSprite(new Sprite(new Texture("Kenney/Tanks/tankBeige.png")));
                break;
            case black:
                setGunSprite(new Sprite(new Texture("Kenney/Tanks/barrelBlack.png")));
                setSprite(new Sprite(new Texture("Kenney/Tanks/tankBlack.png")));
                break;
            case blue:
                setGunSprite(new Sprite(new Texture("Kenney/Tanks/barrelBlue.png")));
                setSprite(new Sprite(new Texture("Kenney/Tanks/tankBlue.png")));
                break;
            case green:
                setGunSprite(new Sprite(new Texture("Kenney/Tanks/barrelGreen.png")));
                setSprite(new Sprite(new Texture("Kenney/Tanks/tankGreen.png")));
                break;
            case red:
                setGunSprite(new Sprite(new Texture("Kenney/Tanks/barrelRed.png")));
                setSprite(new Sprite(new Texture("Kenney/Tanks/tankRed.png")));
                break;
            default:
                setGunSprite(new Sprite(new Texture("Kenney/Tanks/barrelBeige.png")));
                setSprite(new Sprite(new Texture("Kenney/Tanks/tankBeige.png")));
                break;
        }
    } // setTankColor

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
    } // setGunRotation

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
} // TankEntity