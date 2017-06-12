package Towers;

import Main.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by evanphoward on 5/16/17.
 */
//abstract class, the parent of all other towers
public abstract class Tower {
    //list of all the bullets it has fired
    private LinkedList<Bullet> bullets;
    //parent gameboard
    private Gameboard parentBoard;
    //the "template" bullet to create new bullets from
    private Bullet myBullet;
    //texture of the tower
    private ImageIcon myTexture;
    //speed is the delay between shots
    //distance is the distance away the closest enemy is
    private int x,y,speed,range,distance;

    public Tower(Gameboard g, Bullet myBullet, ImageIcon myTexture, int x, int y, int speed, int range) {
        //creates a tower using the specified fields
        this.range = range;
        this.speed = speed;
        this.parentBoard = g;
        this.myBullet = myBullet;
        //scales the texture to the size of a square in the gameboard
        this.myTexture = new ImageIcon(new ImageIcon(myTexture.getImage()).getImage().getScaledInstance(Gameboard.SQUARESIZE, Gameboard.SQUARESIZE,java.awt.Image.SCALE_SMOOTH));
        this.x = x;
        this.y = y;
        bullets= new LinkedList<>();
    }

    //returns a -1 if the closest enemy is outside of range, otherwise returns the position in the linked list of that enemy
    public int getClosestEnemy(LinkedList<Enemy> enemies) {
        //if there are enemies on the screen
        if(enemies.size()!=0) {
            //sets the distance to a large number
            this.distance = Integer.MAX_VALUE;
            //starts at the first enemy
            int closest = 0;
            for (int i=0;i<enemies.size();i++) {
                //dist is the distance from the tower to the enemy
                double dist = Math.sqrt(Math.pow(x - enemies.get(i).getX(),2)+Math.pow( y - enemies.get(i).getY(),2));
                //if dist is less than the current closest distance and the range plus a square to account for the texture of the enemy
                if (dist < this.distance && dist < range+ Gameboard.SQUARESIZE) {
                    //the closest distance is dist and the closest enemy position is i
                    this.distance = (int)dist;
                    closest = i;
                }
            }
            //if the closest distance is less than the range, return that enemy
            if(distance<range+ Gameboard.SQUARESIZE)
            return closest;
            //if it is not close enough, or there are no enemies on the screen, return -1
            else
                return -1;
        }
        else
            return -1;
    }

    //tick and endRound methods are abstract
    public abstract void tick();

    public abstract void endRound();

    //used to find if it is intersecting something when it is being placed down
    public Rectangle getBounds() {
        return new Rectangle(x,y,myTexture.getIconWidth(),myTexture.getIconHeight());
    }

    //used in child towers to reference private fields
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

    //updates the list of bullets, used by a child so that the draw method has bullets to draw
    public void setBullets(LinkedList<Bullet> bullets) {
        this.bullets = bullets;
    }

    //draws the tower and all bullets it has shot
    public void draw(Graphics g) {
        g.drawImage(myTexture.getImage(),x,y,null);
        for(Bullet b : bullets)
        b.draw(g);
    }
}
