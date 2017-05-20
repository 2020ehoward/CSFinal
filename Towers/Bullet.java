package Towers;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Created by evanphoward on 5/20/17.
 */
public class Bullet {
    private ImageIcon myTexture;
    private int x,y,dx,dy,damage;

    public Bullet(ImageIcon myTexture, int x, int y, int dx, int dy, int damage) {
        this.myTexture = myTexture;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.damage = damage;
    }

    public void draw(Graphics g) {
        g.drawImage(myTexture.getImage(),x,y,null);
    }
}
