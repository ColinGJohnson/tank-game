package com.mygdx.tanks;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by colin on 14-May-17.
 * An entity representing a tank
 */
public class TankEntity extends Entity{
    private static final float TANK_SPEED = 1; // speed for tanks (m/s)
    private TankColor tankColor; //

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
    } // TankEntity Constructor

    /**
     * Moves tank according to specified inputs.
     * @param up Should the tank move up?
     * @param down Should the tank move down?
     * @param left Should the tank rotate left?
     * @param right Should the tank rotate right?
     * @param target Point the tank's gun should be facing.
     */
    public void moveTank(boolean up, boolean down, boolean left, boolean right, Vector2 target){

    } // moveTank

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
        shape.setAsBox(20, 20);

        // add new tank body definition to game map
        tankBody = getGameMap().getWorld().createBody(def);

        // add rectangular shape to body
        tankBody.createFixture(shape, 1.0f);

        // attach this entity's unique id to body so it can be identified dyrubg collisions
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
} // TankEntity
