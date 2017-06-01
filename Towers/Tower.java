package Towers;

import Main.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by evanphoward on 5/16/17.
 */
public abstract class Tower {
    private LinkedList<Bullet> bullets;
    private Gameboard parentBoard;
    private Bullet myBullet;
    private ImageIcon myTexture;
    private int x,y,speed,range,distance;

    public Tower(Gameboard g, Bullet myBullet, ImageIcon myTexture, int x, int y, int speed, int range) {
        this.range = range;
        this.speed = speed;
        this.parentBoard = g;
        this.myBullet = myBullet;
        this.myTexture = new ImageIcon(new ImageIcon(myTexture.getImage()).getImage().getScaledInstance(Gameboard.SQUARESIZE, Gameboard.SQUARESIZE,java.awt.Image.SCALE_SMOOTH));
        this.x = x;
        this.y = y;
        bullets= new LinkedList<>();
    }

    public int getClosestEnemy(LinkedList<Enemy> enemies) {
        if(enemies.size()!=0) {
            this.distance = Integer.MAX_VALUE;
            int closest = 0;
            for (int i=0;i<enemies.size();i++) {
                double dist = Math.sqrt(Math.pow(x - enemies.get(i).getX(),2)+Math.pow( y - enemies.get(i).getY(),2));
                if (dist < this.distance && dist < range+ Gameboard.SQUARESIZE) {
                    this.distance = (int)dist;
                    closest = i;
                }
            }
            if(distance<range+ Gameboard.SQUARESIZE)
            return closest;
            else
                return -1;
        }
        else
            return -1;
    }

    public abstract void tick();

    public abstract void endRound();

    public Rectangle getBounds() {
        return new Rectangle(x,y,myTexture.getIconWidth(),myTexture.getIconHeight());
    }

    public Gameboard getParentBoard() {
        return parentBoard;
    }

    public Bullet getMyBullet() {
        return myBullet;
    }

    public ImageIcon getMyTexture() {
        return myTexture;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDistance() {
        return distance;
    }

    public int getSpeed() {
        return speed;
    }

    public int getRange() {
        return range;
    }

    public void setBullets(LinkedList<Bullet> bullets) {
        this.bullets = bullets;
    }

    public void draw(Graphics g) {
        g.drawImage(myTexture.getImage(),x,y,null);
        for(Bullet b : bullets)
        b.draw(g);
    }
}
