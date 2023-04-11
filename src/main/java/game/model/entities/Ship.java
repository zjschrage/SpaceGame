package game.model.entities;

import game.io.KeyboardHandler;
import game.model.utils.Coordinate;
import game.model.utils.Vector;
import game.model.world.WorldState;
import game.utils.EntityFactories;
import game.utils.Info;
import game.view.ViewField;
import game.view.gameview.GameViewField;

import java.awt.*;
import java.util.ResourceBundle;

public class Ship extends Entity<Info> {

    private static final double MAXIMUM_VELOCITY = 6;

    private ResourceBundle res = ResourceBundle.getBundle("game_properties");
    private double acceleration = Double.parseDouble(res.getString("ACCELERATION"));
    private int bulletSpeed = 1000000000 / Integer.parseInt(res.getString("BULLET_PER_SECOND"));

    private ResourceBundle resView = ResourceBundle.getBundle("view_properties");
    private int size = Integer.parseInt(resView.getString("DEFAULT_TILE_SIZE"));

    private WorldState worldState;
    private ViewField field;
    private KeyboardHandler kh;
    private int hp;
    private Vector velocity;

    private double a_decay = 0.94;
    private double b_decay = 0.99;
    private double epsilon_decay = 0.2;

    private long shootTimer = System.nanoTime();

    public Ship(WorldState worldState, ViewField field, Coordinate c, int xSize, int ySize) {
        super(c, xSize, ySize);
        this.worldState = worldState;
        this.field = field;
        this.hp = 100;
        this.velocity = new Vector(0, 0);
        this.kh = new KeyboardHandler();
        field.addKeyListener(kh);
    }

    @Override
    public void tick() {
        move();
        handleInput();
    }

    public int getHp() {
        return hp;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void accelerate(Vector accel) {
        double x = velocity.x() + accel.x();
        double y = velocity.y() + accel.y();

        if (x < -MAXIMUM_VELOCITY) x = -MAXIMUM_VELOCITY;
        else if (x > MAXIMUM_VELOCITY) x = MAXIMUM_VELOCITY;

        if (y < -MAXIMUM_VELOCITY) y = -MAXIMUM_VELOCITY;
        else if (y > MAXIMUM_VELOCITY) y = MAXIMUM_VELOCITY;

        velocity = new Vector(x, y);
    }

    public void move() {
        airResistance();
        Rectangle transBoundsX = new Rectangle(getHBox());
        Rectangle transBoundsY = new Rectangle(getHBox());
        transBoundsX.translate((int) (velocity.x()*(MAXIMUM_VELOCITY)/2), 0);
        transBoundsY.translate(0, (int) (velocity.y()*(MAXIMUM_VELOCITY)/2));
        boolean xColliding = worldState.checkEntityColliding(this, transBoundsX);
        boolean yColliding = worldState.checkEntityColliding(this, transBoundsY);
        double newX = getCoordinate().x() + velocity.x();
        double newY = getCoordinate().y() + velocity.y();
        if (xColliding) {
            newX = getCoordinate().x() - velocity.x();
            velocity = new Vector(velocity.x()/2, velocity.y());
        }
        if (yColliding) {
            newY = getCoordinate().y() - velocity.y();
            velocity = new Vector(velocity.x(), velocity.y()/2);
        }
        Coordinate newC = new Coordinate(newX, newY);
        Rectangle newHitbox = new Rectangle((int)(newC.x() - size/2), (int)(newC.y() - size/2), size, size);
        updateState(newC, newHitbox);
    }

    private void airResistance() {
        double vx = 0;
        double vy = 0;
        if (Math.abs(velocity.x()) > epsilon_decay) {
            double x_factor = (MAXIMUM_VELOCITY - Math.abs(velocity.x()))/MAXIMUM_VELOCITY;
            double x_decay = (1-x_factor)*a_decay + x_factor*(b_decay);
            vx = velocity.x() * x_decay;
        }
        if (Math.abs(velocity.y()) > epsilon_decay) {
            double y_factor = (MAXIMUM_VELOCITY - Math.abs(velocity.y()))/MAXIMUM_VELOCITY;
            double y_decay = (1-y_factor)*a_decay + y_factor*(b_decay);
            vy = velocity.y() * y_decay;
        }
        velocity = new Vector(vx, vy);
    }

    private void updateState(Coordinate c, Rectangle hitbox) {
        Info oldInfo = new Info(this.getCoordinate(), this.getHBox());
        Info newInfo = new Info(c, getHBox());
        setCoordinate(c);
        setHBox(hitbox);
        notifyListeners(oldInfo, newInfo);
    }

    public void place(Coordinate c) {
        Rectangle newHitbox = new Rectangle((int)(c.x() - size/2), (int)(c.y() - size/2), size, size);
        updateState(c, newHitbox);
    }

    private void handleInput() {
        if (kh.getKeys().size() > 0) {
            for (Character c : kh.getKeys()) {
                switch(c) {
                    case 'w' -> accelerate(new Vector(0, -acceleration));
                    case 'a' -> accelerate(new Vector(-acceleration, 0));
                    case 's' -> accelerate(new Vector(0, acceleration));
                    case 'd' -> accelerate(new Vector(acceleration, 0));
                    case ' ' -> {
                        if ((System.nanoTime() - shootTimer) < bulletSpeed) continue;
                        else shootTimer = System.nanoTime();
                        double dx = getVelocity().x();
                        double dy = getVelocity().y();
                        double f = (double)size / Math.max(Math.abs(dx), Math.abs(dy));
                        dx *= f;
                        dy *= f;
                        Coordinate cord = new Coordinate(getCoordinate().x() + dx, getCoordinate().y() + dy);
                        Vector velocity = new Vector(getVelocity().x()*2,getVelocity().y()*2);
                        EntityFactories.createBullet(worldState, (GameViewField)field, cord, velocity, true);
                    }
                }
            }
        }
    }

}
