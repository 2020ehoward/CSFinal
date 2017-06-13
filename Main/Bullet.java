package Main;

import javax.swing.*;
import java.awt.*;

/**
 * Created by evanphoward on 5/20/17.
 */
public class Bullet {
    //isGone is true when it either hits an enemy or leaves the screen
    private boolean isGone,canHitFrozen;
    //the parent gameboard object
    private Gameboard g;
    private ImageIcon myTexture;
    private int x,y,dx,dy,damage,speed;

    //used to create an identical bullet at a new x and y value with no movement speed
    public Bullet(Bullet b,int x,int y) {
        this.speed = b.getSpeed();
        this.canHitFrozen = b.canHitFrozen();
        this.isGone = false;
        this.g = b.getG();
        this.myTexture = b.getTexture();
        this.x = x;
        this.y = y;
        this.dx = 0;
        this.dy = 0;
        this.damage = b.getDamage();
    }

    //used to create a new bullet, used as a texture for new bullets
    public Bullet(Gameboard g, ImageIcon myTexture, int damage, int speed,int size,boolean canHitFrozen) {
        this.canHitFrozen=canHitFrozen;
        this.speed = speed;
        this.g = g;
        this.myTexture = new ImageIcon(myTexture.getImage().getScaledInstance(size,size,Image.SCALE_SMOOTH));
        this.damage = damage;
    }

    //next four getters used to copy the bullet, used when it is a template
    public ImageIcon getTexture() {
        return myTexture;
    }

    public Gameboard getG() {
        return g;
    }

    public int getDamage() {
        return damage;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean canHitFrozen() { return canHitFrozen; }

    //checks if it has hit a enemy, if it has it is gone and it damages the enemy
    public void hasHit() {
        for(Enemy e : g.getEnemies()) {
            //if the enemy is frozen, it does not hit it unless the bullet can hit frozen enemies
            if(getBounds().intersects(e.getBounds()) && !isGone && ((e.isFrozen() && canHitFrozen) || !e.isFrozen())) {
                e.removeHealth(damage);
                isGone = true;
            }
        }
    }

    //checks if it is outside of the gameboard, and then returns isGone
    public boolean isGone() {
        if(x<0 || x>Gameboard.IMAGEWIDTH || y<0 || y>Gameboard.IMAGEWIDTH)
            isGone = true;
        return isGone;
    }

    //standard tick method, changes x and y and checks if it has hit an enemy
    public void tick() {
        x+=dx;
        y+=dy;
        hasHit();
    }

    //setters for horizontal and vertical velocity
    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    //used to fire at a specified enemy at a specified distance with a certain range
    public void fireAt(Enemy e, int distance, int range) {
        //only fire at the enemy if it isn't frozen or if it is frozen and can hit frozen enemies
        if((e.isFrozen() && canHitFrozen) || (!e.isFrozen())) {
            //creates a fake enemy
            Enemy fakeEnemy = new Enemy(e);
            //figures out how many ticks it would take to reach the enemy
            int time = distance / speed;
            //figures out where the enemy will be when the bullet reaches it
            for (int i = 0; i < time; i++)
                fakeEnemy.tick();
            //the distance is now where the enemy will be
            distance = (int) (Math.sqrt(Math.pow(fakeEnemy.getX() - x, 2) + Math.pow(fakeEnemy.getY() - y, 2)));
            //if the updated position is still within range
            if (distance < range) {
                //uses a formula to set velocity to hit the enemy at the specified place and time
                float dx, dy;
                dx = fakeEnemy.getX() - x;
                dy = fakeEnemy.getY() - y;
                float length = (float) (Math.sqrt(dx * dx + dy * dy));
                dx /= length;
                dy /= length;
                this.dx = (int) (dx * speed);
                this.dy = (int) (dy * speed);
            }
            //if it can't reach the enemy, it is deleted
            else
                isGone = true;
        }
        else
            isGone = true;
    }

    //used to check if the bullet is intersecting something
    public Rectangle getBounds() {
        return new Rectangle(x,y,myTexture.getIconWidth(),myTexture.getIconHeight());
    }

    //standard draw method
    public void draw(Graphics g) {
        g.drawImage(myTexture.getImage(),x,y,null);
    }
}
