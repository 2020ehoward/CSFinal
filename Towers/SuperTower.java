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
public class SuperTower extends Tower {
    private LinkedList<Bullet> bullets;
    private LinkedList<Enemy> enemies;
    private int cooldown;

    public SuperTower(Gameboard g, int x, int y) {
        super(g,new Bullet(g,new ImageIcon("Textures/Bullets/Bullet5.png"),1,15,45),new ImageIcon("Textures/Towers/Tower5.png"),x,y,5,5* Gameboard.SQUARESIZE);
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
            if(getClosestEnemy(enemies)!=-1) {
                bullets.add(new Bullet(getMyBullet(),(getX())+(getMyTexture().getIconWidth()/4),(getY())+(getMyTexture().getIconHeight()/4)));
                bullets.getLast().fireAt(enemies.get(getClosestEnemy(enemies)),getDistance(),getRange());
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
