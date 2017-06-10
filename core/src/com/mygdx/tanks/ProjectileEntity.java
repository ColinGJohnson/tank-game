package com.mygdx.tanks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by colin on 16-May-17.
 */
public class ProjectileEntity extends Entity {
    public static final float PROJECTILE_SPEED = 20; // speed for projectiles (m/s)

    private TankEntity source;
    private boolean used = false; // true if this projectile has been used already
    private Entity contact = null; // set to the entity this projectile comes in contact with

    public ProjectileEntity(TankEntity source, GameMap gameMap) {
        super(0, 0, PROJECTILE_SPEED, gameMap);
        this.source = source;

        // set sprite for this projectile
        setSprite(new Sprite(new Texture("Kenney/Bullets/bulletSilverSilver.png")));

        // set rotation of sprite and body to match gun barrel
        setRotation(source.getGunRotation() + 90);

        // define collision bounds for this projectile
        defineBody();
    } // ProjectileEntity Constructor

    private Vector2 getStartPos(){
        Vector2 startPos = new Vector2(source.getX() - 20, source.getY() - 20);
        float spawnDistance = source.getSprite().getWidth() / Constants.PPM;
        startPos.x = (source.getBody().getPosition().x + spawnDistance * MathUtils.cosDeg(source.getGunRotation() + 90));
        startPos.y = (source.getBody().getPosition().y + spawnDistance * MathUtils.sinDeg(source.getGunRotation() + 90));
        return startPos;
    } // getStartPos

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

        // remove if too far away
        if (getX() > 10000 || getX() < - 10000 || getY() > 10000 || getY() < - 10000){
            used  = true;
        }

        // update pixel position variables
        setX(getBody().getPosition().x * Constants.PPM);
        setY(getBody().getPosition().y * Constants.PPM);

        // update sprite position
        getSprite().setX(getX() - getSprite().getWidth() / 2);
        getSprite().setY(getY() - getSprite().getHeight() / 2);
    } // update

    /**
     * Define projectile collision bounds.
     */
    public void defineBody(){
        Body projectileBody;
        BodyDef def = new BodyDef();
        PolygonShape shape = new PolygonShape();

        // projectiles are dynamic (as opposed to static, like walls or bushes)
        def.type = BodyDef.BodyType.DynamicBody;

        // set body position to tank position
        def.position.set(getStartPos());

        // projectile is allowed to rotate
        def.fixedRotation = true;

        // projectile collisions determined by rectangular hit box
        // NOTE: width & height measured from center
        shape.setAsBox(getSprite().getWidth() / 2 / Constants.PPM, getSprite().getHeight() / 2 / Constants.PPM);

        // add new projectile body definition to game map
        projectileBody = getGameMap().getWorld().createBody(def);

        // fix rectangular shape to body
        projectileBody.createFixture(shape, 10.0f);

        // attach this entity's unique id to body so it can be identified during collisions
        projectileBody.setUserData(this);

        // associate Box2D body reference in Entity class
        setBody(projectileBody);

        // rotate box to match sprite
        getBody().setTransform(getBody().getWorldCenter(), (float) Math.toRadians(getRotation() + 90));

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

        // set contact tracking variable
        this.contact = contact;

        // if the projectile has hit a tank
        if (contact instanceof TankEntity){
            ((TankEntity) contact).setDestroyed(true);
        }

        // this projectile has been used, mark it to be removed
        setUsed(true);
    } // setContact
} // ProjectileEntity
