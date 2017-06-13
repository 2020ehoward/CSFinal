package Towers;

import Main.Bullet;
import Main.Enemy;
import Main.Gameboard;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * Created by evanphoward on 6/6/17.
 */
/*
See map1 tower for comments, this tower is almost
a duplicate besides its fields (speed, texture, bullet,
etc.)
 */
public class SuperTower extends Tower {
    private LinkedList<Bullet> bullets;
    private LinkedList<Enemy> enemies;
    private int cooldown;

    public SuperTower(Gameboard g, int x, int y) {
        super(g,new Bullet(g,new ImageIcon("Textures/Bullets/Bullet5.png"),1,12,45,false),new ImageIcon("Textures/Towers/Tower5.png"),x,y,15,5* Gameboard.SQUARESIZE);
        this.bullets = new LinkedList<>();
        this.cooldown=0;
    }

    public void tick() {
        for(int i=0;i<bullets.size();i++) {
            Bullet b = bullets.get(i);
            b.tick();
            if(b.isGone())
                bullets.remove(b);
        }
        enemies = getParentBoard().getEnemies();
        if(cooldown==0) {
            int closestEnemy = getClosestEnemy(enemies);
            if(getClosestEnemy(enemies)!=-1  && !enemies.get(closestEnemy).isFrozen()) {
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

    public void endRound() {
        bullets.clear();
        cooldown=getSpeed();
    }

    public void draw(Graphics g) {
        super.setBullets(bullets);
        super.draw(g);
    }
}
