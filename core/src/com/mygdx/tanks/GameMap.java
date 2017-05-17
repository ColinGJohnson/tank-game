package com.mygdx.tanks;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

/**
 * Created by colin on 15-May-17.
 * The game map.
 */
public class GameMap {
    private World world; // Box2D world to manage collisions for entities on this map
    private TmxMapLoader mapLoader;
    private TiledMap tiledMap; // tiledMap to define map bounds and graphics locations
    private Vector2 spawn; // starting position for players on this map

    private ArrayList<BotTank> bots = new ArrayList<BotTank>(); // computer controlled tanks on map
    private ArrayList<ProjectileEntity> projectiles = new ArrayList<ProjectileEntity>(); // projectiles
    public PlayerTank playerTank; // reference to player tank object

    /**
     * GameMap constructor.
     */
    public GameMap(){

        // set spawn point for map
        spawn = new Vector2(1000, 1000);

        // define Box2D world with no gravity in either direction
        world = new World(new Vector2(0, 0), false);

        // create player tank on map
        playerTank = new PlayerTank(this);

        // load map file
        mapLoader = new TmxMapLoader();
        tiledMap = new TiledMap();
        tiledMap = mapLoader.load("TankGameMap.tmx");
    } // GameMap Constructor

    public void update(){

        // update Box2D physics
        world.step(1/60f, 6, 2);

        // update bots and remove dead ones
        ArrayList<Object> garbage = new ArrayList<Object>();
        for (BotTank bot : bots){
            bot.update();
            if (bot.isDestroyed()){
                garbage.add(bot);
            }
        }
        bots.removeAll(garbage);

        // update projectiles and remove spent ones
        for (ProjectileEntity projectile : projectiles) {
            projectile.update();
            if (projectile.isUsed()){
                garbage.add(projectile);
            }
        }
        projectiles.removeAll(garbage);
    }

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

    public ArrayList<BotTank> getBots() {
        return bots;
    }

    public void setBots(ArrayList<BotTank> bots) {
        this.bots = bots;
    }

    public ArrayList<ProjectileEntity> getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(ArrayList<ProjectileEntity> projectiles) {
        this.projectiles = projectiles;
    }
} // GameMap
