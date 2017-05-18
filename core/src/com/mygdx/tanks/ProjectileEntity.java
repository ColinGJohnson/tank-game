package com.mygdx.tanks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.util.ArrayList;

/**
 * Created by colin on 16-May-17.
 */
public class ProjectileEntity extends Entity{
    public static final float PROJECTILE_SPEED = 15; // speed for projectiles (m/s)

    private TankEntity source;
    private boolean used = false; // true if this projectile has been used already
    private Entity contact = null; //

    private ArrayList<Sprite> treadSprites = new ArrayList<Sprite>(); // tread marks from this tank


    public ProjectileEntity(TankEntity source, GameMap gameMap) {
        super(source.getX() - 20, source.getY() - 20, PROJECTILE_SPEED, gameMap);
        this.source = source;

        // calculate starting position
        float tankCenterX = source.getX() + source.getSprite().getWidth()/2;
        float tankCenterY = source.getY() + source.getSprite().getHeight()/2;
        float shotDistance = 30; // px from tank center to start

        // set sprite for this projectile
        setSprite(new Sprite(new Texture("Kenney/Bullets/bulletSilver.png")));

        // define collision bounds for this projectile
        defineBody();

        // set rotation of sprite and body to match gun barrel
        setRotation(source.getGunRotation() + 90);
    } // ProjectileEntity Constructor

    private void collision(){
        System.out.println("collision");
    } // collision

    /**
     * Checks for collisions with tanks and inflict damage on impact. Also removes projectile if it
     * is too far away.
     */
    public void update(){

        // update movement
        float horizontal = 0;
        float vertical = 0;
        horizontal += getV() * MathUtils.cosDeg(getSprite().getRotation() + 90);
        vertical += getV() * MathUtils.sinDeg(getSprite().getRotation() + 90);
        getBody().setLinearVelocity(horizontal, vertical);

        // projectiles may only deal damage once and contact has to have occurred to deal damage
        if(!used && contact != null){

            // check that the contacted tank is not the tank that shot the projectile
            if (contact.getUuid() != getUuid()){

            }
        }

        // remove if too far away
        if (getX() > 10000 || getX() < - 10000 || getY() > 10000 || getY() < - 10000){
            used  = true;
        }

        // update pixel position variables
        setX(getBody().getPosition().x * Constants.PPM);
        setY(getBody().getPosition().y * Constants.PPM);

        // update sprite position
        getSprite().setX(getX());
        getSprite().setY(getY());
    } // update

    /**
     * Define projectile collision bounds.
     */
    public void defineBody(){
        Body projectileBody;
        BodyDef def = new BodyDef();
        PolygonShape shape = new PolygonShape();

        // tanks are dynamic (as opposed to static, like walls or bushes)
        def.type = BodyDef.BodyType.DynamicBody;

        // set body position to tank position
        def.position.set((source.getX() - 30) / Constants.PPM, (source.getY() - 30) / Constants.PPM);

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

        // rotate box to match sprite
        getBody().setTransform(getBody().getWorldCenter(), (float) Math.toRadians(getRotation()));

        // dispose of shape definition object to avoid memory leak
        shape.dispose();
    } // DefineBody

    public boolean isUsed() {
        return used;
    } // isUsed

    public void setUsed(boolean used) {
        this.used = used;
    } // setUsed

    public Entity getContact() {
        return contact;
    } // getContact

    public void setContact(Entity contact) {
        this.contact = contact;
    } // setContact
} // ProjectileEntity
