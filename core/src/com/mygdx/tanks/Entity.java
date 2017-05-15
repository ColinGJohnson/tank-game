package com.mygdx.tanks;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.UUID;

/**
 * Created by colin on 14-May-17.
 */
public abstract class Entity {
    private float rotation; // rotation of entity
    private float x; // X-Position of entity
    private float y; // Y-Position of entity
    private float vx; // max speed of entity
    private Body body; // box2D body for collisions
    private GameMap gameMap; // map this entity is defined on
    private UUID uuid; // unique id for entity identification

    /**
     * Entity constructor.
     * @param x X-Position of Entity.
     * @param y Y-Position of Entity.
     * @param vx Maximum velocity of Entity.
     */
    public Entity (float x, float y, float vx, GameMap gameMap){
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.gameMap = gameMap;

        // initialize variables
        rotation = 0;

        // assign random UUID
        this.setUuid(UUID.randomUUID());
    }

    /**
     * Alternate Entity constructor. Sets all values to defaults.
      */
    public Entity(GameMap gameMap){
        this.gameMap = gameMap;

        // set fields to default values
        this.x = 0;
        this.y = 0;
        this.vx = 1;
    }

    /**
     * Every Entity must define its collision bounds.
     */
    public abstract void defineBody();

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

    public float getVx() {
        return vx;
    }

    public void setVx(float vx) {
        this.vx = vx;
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
        this.rotation = rotation;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }
}
