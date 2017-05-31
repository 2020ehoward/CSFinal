import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * Created by evanphoward on 5/30/17.
 */
public class SniperTower extends Tower {
    private LinkedList<Bullet> bullets;
    private LinkedList<Enemy> enemies;
    private int cooldown;

    public SniperTower(Gameboard g, int x, int y) {
        super(g,new Bullet(g,new ImageIcon("Textures/Bullets/Bullet2.png"),1,15,30),new ImageIcon("Textures/Towers/Tower2.png"),x,y,200,Gameboard.IMAGEWIDTH);
        this.bullets = new LinkedList<>();
        this.cooldown=0;
    }

    public void tick() {
        for(Bullet b : bullets) {
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
        cooldown=0;
    }

    public void draw(Graphics g) {
        super.setBullets(bullets);
        super.draw(g);
    }
}
