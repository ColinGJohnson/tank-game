package com.mygdx.tanks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.UUID;

/**
 * Created by colin on 14-May-17.
 */
public abstract class Entity {
    private Sprite sprite; // sprite for drawing this entity
    private float rotation; // rotation of entity
    private float x; // X-Position of entity
    private float y; // Y-Position of entity
    private float v; // speed of entity
    private Body body; // box2D body for collisions
    private GameMap gameMap; // map this entity is defined on
    private UUID uuid; // unique id for entity identification

    /**
     * Entity constructor.
     *
     * @param x  X-Position of Entity.
     * @param y  Y-Position of Entity.
     * @param v Maximum velocity of Entity.
     */
    public Entity(float x, float y, float v, GameMap gameMap) {
        this.x = x;
        this.y = y;
        this.v = v;
        this.gameMap = gameMap;

        // initialize variables
        rotation = 0;

        // assign random UUID
        this.setUuid(UUID.randomUUID());
    } // Entity Constructor

    /**
     * Alternate Entity constructor. Sets all values to defaults.
     */
    public Entity(GameMap gameMap) {
        this.gameMap = gameMap;

        // set fields to default values
        this.x = 0;
        this.y = 0;
        this.v = 1;
    } // Entity Constructor

    /**
     * Every Entity must define its collision bounds.
     */
    public abstract void defineBody();

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getV() {
        return v;
    }

    public void setV(float v) {
        this.v = v;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {

        // set rotation tracking variable
        this.rotation = rotation;

        // rotate this entity's sprites
        sprite.setRotation(rotation - 90);

        // rotate this entity's hit box
        //getBody().setTransform(getBody().getWorldCenter(), (float) Math.toRadians(getRotation() - 90));
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }
} // Entity
