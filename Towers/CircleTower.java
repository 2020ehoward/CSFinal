package Towers;

import Main.*;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * Created by evanphoward on 5/30/17.
 */
public class CircleTower extends Tower {
    /*
    See basictower for comments, fields, constructor, and endRound are the same
     */
    private LinkedList<Bullet> bullets;
    private LinkedList<Enemy> enemies;
    private int cooldown;

    public CircleTower(Gameboard g, int x, int y) {
        super(g,new Bullet(g,new ImageIcon("Textures/Bullets/Bullet1.png"),1,5,20,false),new ImageIcon("Textures/Towers/Tower1.png"),x,y,70,(int)(1.6* Gameboard.SQUARESIZE));
        this.bullets = new LinkedList<>();
        this.cooldown=0;
    }

    public void endRound() {
        bullets.clear();
        cooldown=getSpeed();
    }
    public void tick() {
        //if 25 ticks have passed since it last shot, remove all bullets
        if(cooldown==getSpeed()-25)
            bullets.clear();
        //if the bullet is gone (it has hit an enemy) remove it from the game
        for(int i=0;i<bullets.size();i++) {
            bullets.get(i).tick();
            if(bullets.get(i).isGone())
                bullets.remove(i);
        }
        //enemies are received from the gameboard
        enemies = getParentBoard().getEnemies();
        if(cooldown==0) {
            //if the closest enemy is in range
            if(getClosestEnemy(enemies)!=-1)
                //fire 8 bullets in a circle around the tower
            for(int i=0;i<8;i++) {
                bullets.add(new Bullet(getMyBullet(), (getX()) + (getMyTexture().getIconWidth() / 4), (getY()) + (getMyTexture().getIconHeight() / 4)));
                switch(i) {
                    case 0: bullets.getLast().setDy(bullets.getLast().getSpeed()*-1);
                    break;
                    case 1: bullets.getLast().setDx((int)(bullets.getLast().getSpeed()*0.8));
                    bullets.getLast().setDy((int)(bullets.getLast().getSpeed()*-0.8));
                    break;
                    case 2: bullets.getLast().setDx(bullets.getLast().getSpeed());
                    break;
                    case 3: bullets.getLast().setDx((int)(bullets.getLast().getSpeed()*0.8));
                        bullets.getLast().setDy((int)(bullets.getLast().getSpeed()*0.8));
                        break;
                    case 4: bullets.getLast().setDy(bullets.getLast().getSpeed());
                    break;
                    case 5: bullets.getLast().setDx((int)(bullets.getLast().getSpeed()*-0.8));
                        bullets.getLast().setDy((int)(bullets.getLast().getSpeed()*0.8));
                        break;
                    case 6: bullets.getLast().setDx(bullets.getLast().getSpeed()*-1);
                      break;
                    case 7: bullets.getLast().setDx((int)(bullets.getLast().getSpeed()*-0.8));
                        bullets.getLast().setDy((int)(bullets.getLast().getSpeed()*-0.8));
                    break;
                }
            }
            //reset cooldown
            cooldown=getSpeed();
        }
        //if cooldown hasn't reached 0, decrease it by one
        if(cooldown>0)
            cooldown--;
    }

    //update the bullets and use the tower draw method.
    public void draw(Graphics g) {
        super.setBullets(bullets);
        super.draw(g);
    }
}
