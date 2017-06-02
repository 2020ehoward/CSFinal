package Main;

import javax.swing.*;
import java.awt.*;

/**
 * Created by evanphoward on 5/20/17.
 */
public class Bullet {
    private boolean isGone;
    private Gameboard g;
    private ImageIcon myTexture;
    private int x,y,dx,dy,damage,speed;

    public Bullet(Bullet b,int x,int y) {
        this.speed = b.getSpeed();
        this.isGone = false;
        this.g = b.getG();
        this.myTexture = b.getTexture();
        this.x = x;
        this.y = y;
        this.dx = 0;
        this.dy = 0;
        this.damage = b.getDamage();
    }

    public Bullet(Gameboard g, ImageIcon myTexture, int damage, int speed,int size) {
        this.speed = speed;
        this.g = g;
        this.myTexture = new ImageIcon(myTexture.getImage().getScaledInstance(size,size,Image.SCALE_SMOOTH));
        this.damage = damage;
    }

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

    public void hasHit() {
        for(Enemy e : g.getEnemies()) {
            if(getBounds().intersects(e.getBounds())) {
                e.removeHealth(damage);
                isGone = true;
            }
        }
    }

    public boolean isGone() {
        if(x<0 || x>Gameboard.IMAGEWIDTH || y<0 || y>Gameboard.IMAGEWIDTH)
            isGone = true;
        return isGone;
    }

    public void tick() {
        x+=dx;
        y+=dy;
        hasHit();
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void fireAt(Enemy e, int distance, int range) {
        Enemy fakeEnemy = new Enemy(e);
        int time = distance / speed;
        for (int i = 0; i < time; i++)
            fakeEnemy.tick();
        distance = (int)(Math.sqrt(Math.pow(fakeEnemy.getX()-x,2)+Math.pow(fakeEnemy.getY()-y,2)));
        if(distance<range) {
            float dx, dy;
            dx = fakeEnemy.getX() - x;
            dy = fakeEnemy.getY() - y;
            float length = (float) (Math.sqrt(dx * dx + dy * dy));
            dx /= length;
            dy /= length;
            this.dx = (int) (dx * speed);
            this.dy = (int) (dy * speed);
        }
        else
            isGone=true;
    }

    public Rectangle getBounds() {
        return new Rectangle(x,y,myTexture.getIconWidth(),myTexture.getIconHeight());
    }

    public void draw(Graphics g) {
        g.drawImage(myTexture.getImage(),x,y,null);
    }
}
