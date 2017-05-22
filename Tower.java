
import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by evanphoward on 5/16/17.
 */
public class Tower {
    private LinkedList<Enemy> enemies;
    private Gameboard parentBoard;
    private Bullet myBullet;
    private LinkedList<Bullet> bullets;
    private ImageIcon myTexture;
    private int x,y,speed,range,cooldown,distance;

    public Tower(Gameboard g, Bullet myBullet, ImageIcon myTexture, int x, int y, int speed,int range) {
        this.bullets = new LinkedList<>();
        this.range = range;
        this.speed = this.cooldown = speed;
        this.parentBoard = g;
        this.myBullet = myBullet;
        this.myTexture = new ImageIcon(new ImageIcon(myTexture.getImage()).getImage().getScaledInstance(Gameboard.SQUARESIZE,Gameboard.SQUARESIZE,java.awt.Image.SCALE_SMOOTH));
        this.x = x;
        this.y = y;
    }

    public int getClosestEnemy() {
        if(enemies.size()!=0) {
            this.distance = Integer.MAX_VALUE;
            int closest = 0;
            for (int i=0;i<enemies.size();i++) {
                double dist = Math.sqrt(Math.pow(x*Gameboard.SQUARESIZE - enemies.get(i).getX(),2)+Math.pow( y*Gameboard.SQUARESIZE - enemies.get(i).getY(),2));
                if (dist < this.distance && dist<range) {
                    this.distance = (int)dist;
                    closest = i;
                }
            }
            if(this.distance<range)
            return closest;
            else
                return -1;
        }
        else
            return -1;
    }



    public void tick() {
        for(Bullet b : bullets) {
            b.tick();
            if(b.isGone())
                bullets.remove(b);
        }
        enemies = parentBoard.getEnemies();
        if(cooldown==0) {
            if(getClosestEnemy()!=-1) {
                bullets.add(new Bullet(myBullet,(x*Gameboard.SQUARESIZE)+(myTexture.getIconWidth()/4),(y*Gameboard.SQUARESIZE)+(myTexture.getIconHeight()/4)));
                bullets.getLast().fireAt(enemies.get(getClosestEnemy()),distance,range);
                cooldown = speed;
            }
        }
        if(cooldown>0)
            cooldown--;
    }

    public void draw(Graphics g) {
        g.drawImage(myTexture.getImage(),x*Gameboard.SQUARESIZE,y*Gameboard.SQUARESIZE,null);
        for(Bullet b : bullets)
        b.draw(g);
    }
}
