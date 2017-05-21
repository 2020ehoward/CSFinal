import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.LinkedList;

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

    public Bullet(Gameboard g, ImageIcon myTexture, int damage, int speed) {
        this.speed = speed;
        this.g = g;
        this.myTexture = myTexture;
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
                e.setDead(true);
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

    public void fireAt(Enemy e) {
        float dx,dy;
        dx = e.getX()-x;
        dy = e.getY()-y;
        float length = (float)(Math.sqrt(dx*dx+dy*dy));
        dx/=length;
        dy/=length;
        this.dx=(int)(dx*speed);
        this.dy=(int)(dy*speed);
    }

    public Rectangle getBounds() {
        return new Rectangle(x,y,myTexture.getIconWidth(),myTexture.getIconHeight());
    }

    public void draw(Graphics g) {
        g.drawImage(myTexture.getImage(),x,y,null);
    }
}
