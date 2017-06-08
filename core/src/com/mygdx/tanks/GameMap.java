package com.mygdx.tanks;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

/**
 * Created by colin on 15-May-17.
 * The game map.
 */
public class GameMap {
    private World world; // Box2D world to manage collisions for entities on this map
    private ContactListener contactListener; // listener which will be called during collisions

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
        spawn = new Vector2(2000, 2000);

        // define Box2D world with no gravity in either direction
        world = new World(new Vector2(0, 0), false);

        // define contact listener
        contactListener();

        // create player tank on map
        playerTank = new PlayerTank(this);

        // load map file
        mapLoader = new TmxMapLoader();
        tiledMap = new TiledMap();
        tiledMap = mapLoader.load("TankGameMap.tmx");
    } // GameMap Constructor

    public void update(float deltaT){

        // update Box2D physics
        world.step(deltaT, 6, 2);

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

        // update player
        playerTank.update();

        projectiles.removeAll(garbage);
    }

    private void contactListener(){
        contactListener = new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                System.out.println("collision");
                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();

                if (a.getUserData() instanceof TankEntity && b.getUserData() instanceof ProjectileEntity
                        || b.getUserData() instanceof TankEntity && a.getUserData() instanceof ProjectileEntity){
                    if (a.getUserData() instanceof ProjectileEntity){
                        ((ProjectileEntity) a.getUserData()).setContact((TankEntity)b.getUserData());
                    } else {
                        ((ProjectileEntity) b.getUserData()).setContact((TankEntity)a.getUserData());
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {}

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        };
    } // contactListener

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

    public PlayerTank getPlayerTank() {
        return playerTank;
    }

    public void setPlayerTank(PlayerTank playerTank) {
        this.playerTank = playerTank;
    }
} // GameMap
