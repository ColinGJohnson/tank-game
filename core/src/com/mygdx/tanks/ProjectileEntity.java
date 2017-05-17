package com.mygdx.tanks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by colin on 16-May-17.
 */
public class ProjectileEntity extends Entity{
    public static final float PROJECTILE_SPEED = 15; // speed for projectiles (m/s)
    private boolean used = false; // true if this projectile has been used already
    private ContactListener contactListener;

    public ProjectileEntity(float x, float y, TankEntity source, GameMap gameMap) {
        super(x, y, PROJECTILE_SPEED, gameMap);

        // set sprite for this projectile and set its rotation to match the tank's gun
        setSprite(new Sprite(new Texture("Kenney/Bullets/bulletSilver.png")));
        getSprite().setRotation(source.getRotation());

        // define collision bounds for this projectile
        defineBody();
    }

    /**
     * Checks for collisions with tanks and inflict damage on impact. Also removes projectile if it
     * is too far away.
     */
    public void update(){

        // projectiles may only deal damage once, and may not damage their source tank
        if(!used){

        }

        // remove if too far away
        if (getX() > 10000 || getX() < - 10000 || getY() > 10000 || getY() < - 10000){
            used  = true;
        }
    }

    /**
     * Define projectile collision bounds.
     */
    public void defineBody(){
        Body projectileBody;
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
        shape.setAsBox(getSprite().getWidth() / 2 / Constants.PPM, getSprite().getHeight() / 2 / Constants.PPM);

        // add new projectile body definition to game map
        projectileBody = getGameMap().getWorld().createBody(def);

        // fix rectangular shape to body
        projectileBody.createFixture(shape, 1.0f);

        // attach this entity's unique id to body so it can be identified during collisions
        projectileBody.setUserData(this.getUuid());

        // associate Box2D body reference in Entity class
        setBody(projectileBody);

        // dispose of shape definition object to avoid memory leak
        shape.dispose();
    } // DefineBody

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
