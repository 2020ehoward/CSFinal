package Towers;

import Main.*;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * Created by evanphoward on 5/30/17.
 */
public class BasicTower extends Tower {
    //list of bullets it has shot and enemies on the screen
    private LinkedList<Bullet> bullets;
    private LinkedList<Enemy> enemies;
    //ticks left until it shoots
    private int cooldown;

    //makes a new map1 tower with a parent gameboard at the specified x and y
    public BasicTower(Gameboard g, int x, int y) {
        //sends the corresponding fields (damage, speed of bullet, speed of tower, range, etc.) to the super constructor
        super(g,new Bullet(g,new ImageIcon("Textures/Bullets/Bullet0.png"),1,10,45),new ImageIcon("Textures/Towers/Tower0.png"),x,y,33,3* Gameboard.SQUARESIZE);
        //bullets is an empty linked list and cooldown starts at 0 so it fires as soon as it can
        this.bullets = new LinkedList<>();
        this.cooldown=0;
    }

    public void tick() {
        //ticks every bullet, if it is gone, removes it from the list
        for(int i=0;i<bullets.size();i++) {
            Bullet b = bullets.get(i);
            b.tick();
            if(b.isGone())
                bullets.remove(b);
        }
        //the list of enemies is gotten from the parent gameboard
        enemies = getParentBoard().getEnemies();
        //if it is allowed to shoot
        if(cooldown==0) {
            //if an enemy is in range
            int closestEnemy = getClosestEnemy(enemies);
            if(closestEnemy!=-1 && !enemies.get(closestEnemy).isFrozen()) {
                //add a new bullet at the center of the tower and fire it at the closest enemy, with the distance of the enemy and the range of the tower specified
                bullets.add(new Bullet(getMyBullet(),(getX())+(getMyTexture().getIconWidth()/4),(getY())+(getMyTexture().getIconHeight()/4)));
                bullets.getLast().fireAt(enemies.get(getClosestEnemy(enemies)),getDistance(),getRange());
                //if it is gone (in the case of it is out of range once time passes) remove it
                if(bullets.getLast().isGone())
                    bullets.removeLast();
                //reset cooldown
                cooldown = getSpeed();
            }
        }
        //if cooldown hasn't reached 0, subtract it by 1
        if(cooldown>0)
            cooldown--;
    }

    //end the current round by removing all bullets and resetting cooldown
    public void endRound() {
        bullets.clear();
        cooldown=getSpeed();
    }

    //calls the parent draw method, once updating the list of bullets
    public void draw(Graphics g) {
        super.setBullets(bullets);
        super.draw(g);
    }
}
