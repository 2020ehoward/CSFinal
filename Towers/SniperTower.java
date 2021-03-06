package Towers;

import Main.*;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * Created by evanphoward on 5/30/17.
 */
public class SniperTower extends Tower {
   // Same fields and constructor as map1 tower, changed stats (speed, range, texture, bullet, etc.)
    private LinkedList<Bullet> bullets;
    private LinkedList<Enemy> enemies;
    private int cooldown;

    public SniperTower(Gameboard g, int x, int y) {
        super(g,new Bullet(g,new ImageIcon("Textures/Bullets/Bullet2.png"),1,75,30,false),new ImageIcon("Textures/Towers/Tower2.png"),x,y,80, Gameboard.IMAGEWIDTH);
        this.bullets = new LinkedList<>();
        this.cooldown=0;
    }

    //same tick method as basic tower, just with a different range
    public void tick() {
        for(Bullet b : bullets) {
            b.tick();
            if(b.isGone())
                bullets.remove(b);
        }
        enemies = getParentBoard().getEnemies();
        if(cooldown==0) {
            int closestEnemy = getClosestEnemy(enemies);
            if(getClosestEnemy(enemies)!=-1 && !enemies.get(closestEnemy).isFrozen()) {
                bullets.add(new Bullet(getMyBullet(),(getX())+(getMyTexture().getIconWidth()/4),(getY())+(getMyTexture().getIconHeight()/4)));
                bullets.getLast().fireAt(enemies.get(closestEnemy),getDistance(),getRange());
                if(bullets.getLast().isGone())
                    bullets.removeLast();
                cooldown = getSpeed();
            }
        }
        if(cooldown>0)
            cooldown--;
    }

    //clear all bullets on screen and reset cooldown
    public void endRound() {
        bullets.clear();
        cooldown=getSpeed();
    }

    //update bullets and use the tower draw method
    public void draw(Graphics g) {
        super.setBullets(bullets);
        super.draw(g);
    }
}
