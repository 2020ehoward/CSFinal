//Evan Howard, 11 May 2017

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.LinkedList;

public class Gameboard extends JPanel
{
    private static final int WIDTH = 13;
    public static final int IMAGEWIDTH = 780;
    public static final int SQUARESIZE = IMAGEWIDTH/WIDTH;
    public static final int[][] map = {
            {1,1,1,1,1,1,1,3,1,1,1,1,1,},
            {1,1,0,0,0,0,0,0,1,1,1,4,1,},
            {1,1,0,1,1,1,1,1,1,4,4,4,1,},
            {1,1,0,1,1,1,1,1,1,1,4,4,1,},
            {1,1,0,1,1,1,1,1,1,1,4,4,4,},
            {1,1,0,1,0,0,0,0,0,1,1,1,1,},
            {1,1,0,0,0,1,1,1,0,1,1,1,1,},
            {1,1,1,1,1,1,1,1,0,0,0,0,1,},
            {2,0,0,0,0,1,1,1,1,1,1,0,1,},
            {1,1,1,1,0,1,1,4,4,1,1,0,1,},
            {1,1,1,1,0,1,1,1,1,1,1,0,1,},
            {1,1,1,1,0,0,0,0,0,0,0,0,1,},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,}
    };

    private boolean isRound;
    private int bEnemyCounter;

    private Towerboard towerboard;
    private Scoreboard scoreboard;
    private BufferedImage myImage = new BufferedImage(IMAGEWIDTH, IMAGEWIDTH, 1);
    private Graphics myBuffer;
    private ImageIcon water,grass,dirt;
    private LinkedList<Enemy> enemies;
    private LinkedList<Tower> towers;
   public Gameboard(Scoreboard scoreboard,Towerboard towerboard)
   {
       this.towerboard = towerboard;
       this.scoreboard = scoreboard;
       isRound=false;
       bEnemyCounter=0;
       towers = new LinkedList<>();
       enemies = new LinkedList<>();

       myBuffer = myImage.getGraphics();

       water = new ImageIcon(new ImageIcon("Textures/water.jpg").getImage().getScaledInstance(Gameboard.SQUARESIZE,Gameboard.SQUARESIZE,java.awt.Image.SCALE_SMOOTH));
       grass = new ImageIcon(new ImageIcon("Textures/Grass.jpg").getImage().getScaledInstance(Gameboard.SQUARESIZE,Gameboard.SQUARESIZE,java.awt.Image.SCALE_SMOOTH));
       dirt = new ImageIcon(new ImageIcon("Textures/Dirt.jpg").getImage().getScaledInstance(Gameboard.SQUARESIZE,Gameboard.SQUARESIZE,java.awt.Image.SCALE_SMOOTH));

       spawnBasicTower(6,6);

       drawMap();
   }
    public void paintComponent(Graphics g) {
        g.drawImage(this.myImage, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    public Point getEntrance() {
       for(int y=0;y<WIDTH;y++)
           for(int x=0;x<WIDTH;x++)
               if(map[y][x]==2)
                   return new Point(x,y);
       return null;
    }

    public void drawMap() {
        for(int x=0;x<WIDTH;x++)
            for(int y=0;y<WIDTH;y++) {
                if(map[x][y]==1) {
                    myBuffer.drawImage(grass.getImage(),(Gameboard.SQUARESIZE)*y,(Gameboard.SQUARESIZE)*x,null);
                }
                else if(map[x][y]==4) {
                    myBuffer.drawImage(water.getImage(),(Gameboard.SQUARESIZE)*y,(Gameboard.SQUARESIZE)*x,null);
                }
                else if(map[x][y]==0 || map[x][y]==2 || map[x][y]==3){
                    myBuffer.drawImage(dirt.getImage(),(Gameboard.SQUARESIZE)*y,(Gameboard.SQUARESIZE)*x,null);
                }
            }
    }

    public void nextRound() {
        bEnemyCounter=scoreboard.getRound()*20;
    }

    public void spawnBasicEnemy() {
       enemies.add(new Enemy(this,1,5,new ImageIcon("Textures/Enemies/enemy1.png")));
    }

    public  void spawnBasicTower(int x,int y) {
        towers.add(new Tower(this,new Bullet(this,new ImageIcon("Textures/Bullets/basicBullet.jpg"),1,10),new ImageIcon("Textures/Towers/Tower0.png"),x,y,50,3*SQUARESIZE));
    }

    public LinkedList<Enemy> getEnemies() {
        return enemies;
    }

    public void update() {

       drawMap();

        if (!isRound) {
            nextRound();
            scoreboard.nextRound();
            isRound = true;
        }

       if(bEnemyCounter%20==0)
           spawnBasicEnemy();

       for (int i=0;i<enemies.size();i++) {
            if (enemies.get(i).isDead()) {
                towerboard.addCoins(enemies.get(i).getLevel()*25);
                enemies.remove(i);
            }
            else if (enemies.get(i).isEscaped()) {
                enemies.remove(i);
                scoreboard.loseLife();
            }
        }

        if (enemies.size() == 0 && isRound) {
            isRound = false;
            scoreboard.pause();
        }

        for (Enemy e : enemies) {
            e.draw(myBuffer);
            e.tick();
        }

        for (Tower t : towers) {
            t.draw(myBuffer);
            t.tick();
        }

         repaint();

         if(scoreboard.getLife()==0)
             scoreboard.endGame();

         if(bEnemyCounter>=0)
         bEnemyCounter--;

    }
   
}