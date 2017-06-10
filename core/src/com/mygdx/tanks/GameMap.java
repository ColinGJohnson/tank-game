package com.mygdx.tanks;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
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
    private final float mapPPT = 128; // pixels per tile for the tiled map
    private Vector2 spawn; // starting position for players on this map
    private ArrayList<Vector2> botSpawns; // possible spawn points for bot tanks

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

        // create collision bounds for map
        addCollisions(tiledMap, world);

        // get bot spawn points for map
        addSpawns(tiledMap);
    } // GameMap Constructor

    public void update(float deltaT){

        // update Box2D physics
        world.step(deltaT, 6, 2);

        // update bots and remove dead ones
        ArrayList<Object> garbage = new ArrayList<Object>();
        for (BotTank bot : bots){
            bot.update();
            if (bot.isDestroyed()){

                // remove this bot's box2D body
                world.destroyBody(bot.getBody());

                // add the bot to the garbage list to be removed
                garbage.add(bot);
            }
        }
        bots.removeAll(garbage);

        // update projectiles and remove spent ones
        for (ProjectileEntity projectile : projectiles) {
            projectile.update();
            if (projectile.isUsed()){

                // remove this projectile's box2D body
                world.destroyBody(projectile.getBody());

                // add the projectile to the garbage list to be removed
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
                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();

                if (a.getUserData() instanceof ProjectileEntity){
                    ((ProjectileEntity) a.getUserData()).setContact((TankEntity)b.getUserData());
                }

                if (b.getUserData() instanceof ProjectileEntity){
                    ((ProjectileEntity) b.getUserData()).setContact((TankEntity)a.getUserData());
                }
            }

            @Override
            public void endContact(Contact contact) {}

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        };
        world.setContactListener(contactListener);
    } // contactListener

    /**
     * Adds collision bounds as defined on the .tmx tiled map file in the "Collisions" layer
     * @param map the tiled map to be processed
     * @param world The box2D world to add bodies to
     */
    public void addCollisions(Map map, World world) {

        // add box2D bodies to represent map walls
        MapObjects objects = map.getLayers().get("Collisions").getObjects();
        for(MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                Shape  shape = getRectangle((RectangleMapObject) object);
                BodyDef def = new BodyDef();
                def.type = BodyDef.BodyType.StaticBody;
                float xPos = ((RectangleMapObject) object).getRectangle().getX();
                float yPos = ((RectangleMapObject) object).getRectangle().getY();
                float width = ((RectangleMapObject) object).getRectangle().getWidth();
                float height = ((RectangleMapObject) object).getRectangle().getHeight();
                def.position.set((xPos + width * 0.5f) / Constants.PPM, (yPos + height * 0.5f) / Constants.PPM);
                Body body = world.createBody(def);
                body.createFixture(shape, 1);
                shape.dispose();
            }
        }
    } // addCollisions

    private PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        polygon.setAsBox(rectangle.width / 2 / Constants.PPM, rectangle.height / 2 / Constants.PPM);
        return polygon;
    } // PolygonShape

    private void addSpawns(Map map){
        System.out.println("spawn!");
        MapObjects objects = map.getLayers().get("SpawnPoints").getObjects();
        System.out.println(objects.getCount());
        for(MapObject object : objects) {
            System.out.println("spawn");
            if (object instanceof RectangleMapObject) {
            }
        }
    } // addSpawns

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
