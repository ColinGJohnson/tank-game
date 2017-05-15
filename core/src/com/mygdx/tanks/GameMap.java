package com.mygdx.tanks;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by colin on 15-May-17.
 * The game map.
 */
public class GameMap {
    private World world; // Box2D world to manage collisions for entities on this map
    private TiledMap tiledMap; // tiledMap to define map bounds and graphics locations
    private Vector2 spawn; // starting position for players on this map

    /**
     * GameMap constructor.
     * @param tiledMap
     */
    public GameMap(TiledMap tiledMap){
        this.tiledMap = tiledMap;

        // set spawn point for map
        spawn = new Vector2(100, 100);

        // define Box2D world with no gravity in either direction
        world = new World(new Vector2(0, 0), false);
    } // GameMap Constructor

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public void setTiledMap(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
    }

    public Vector2 getSpawn() {
        return spawn;
    }

    public void setSpawn(Vector2 spawn) {
        this.spawn = spawn;
    }
} // GameMap
