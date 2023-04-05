package game.model.entities;

import game.model.utils.Coordinate;
import game.model.utils.Vector;
import game.model.world.WorldState;
import game.utils.Info;
import game.utils.Observer;
import java.awt.*;

public class Ship extends Observer<Info> implements Entity {

    private static final double MAXIMUM_VELOCITY = 6;

    private WorldState worldState;
    private Rectangle hitbox;
    private Coordinate c;
    private int hp;

    private Vector velocity;
    private double a_decay = 0.94;
    private double b_decay = 0.99;
    private double epsilon_decay = 0.2;


    public Ship(WorldState worldState, Coordinate c) {
        this.worldState = worldState;
        this.hitbox = new Rectangle((int)c.x() - 16, (int)c.y() - 16, 32, 32);
        this.c = c;
        this.hp = 100;
        this.velocity = new Vector(0, 0);
    }

    @Override
    public Rectangle getHBox() {
        return hitbox;
    }

    public Coordinate getCoordinate() {
        return c;
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
        Rectangle transBoundsX = new Rectangle(hitbox);
        Rectangle transBoundsY = new Rectangle(hitbox);
        transBoundsX.translate((int) (velocity.x()*(MAXIMUM_VELOCITY)/2), 0);
        transBoundsY.translate(0, (int) (velocity.y()*(MAXIMUM_VELOCITY)/2));
        boolean xColliding = worldState.checkEntityColliding(this, transBoundsX);
        boolean yColliding = worldState.checkEntityColliding(this, transBoundsY);
        double newX = c.x() + velocity.x();
        double newY = c.y() + velocity.y();
        if (xColliding) {
            newX = c.x() - velocity.x();
            velocity = new Vector(velocity.x()/2, velocity.y());
        }
        if (yColliding) {
            newY = c.y() - velocity.y();
            velocity = new Vector(velocity.x(), velocity.y()/2);
        }
        Coordinate newC = new Coordinate(newX, newY);
        hitbox = new Rectangle((int)(newC.x() - 16), (int)(newC.y() - 16), 32, 32);
        updateState(newC);
    }

    public void place(Coordinate c) {
        hitbox = new Rectangle((int)(c.x() - 16), (int)(c.y() - 16), 32, 32);
        updateState(c);
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

    private void updateState(Coordinate c) {
        Info oldInfo = new Info(this.c, this.hitbox);
        Info newInfo = new Info(c, this.hitbox);
        this.c = c;
        notifyListeners(oldInfo, newInfo);
    }

}
