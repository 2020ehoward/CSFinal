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

    private Scoreboard scoreboard;
    private BufferedImage myImage = new BufferedImage(IMAGEWIDTH, IMAGEWIDTH, 1);
    private Graphics myBuffer;
    private ImageIcon water,grass,dirt;
    private LinkedList<Enemy> enemies;
    private LinkedList<Tower> towers;
   public Gameboard(Scoreboard scoreboard)
   {
       this.scoreboard = scoreboard;
       isRound=true;
       towers = new LinkedList<>();
       enemies = new LinkedList<>();

       myBuffer = myImage.getGraphics();

       water = new ImageIcon(new ImageIcon("Textures/water.jpg").getImage().getScaledInstance(Gameboard.SQUARESIZE,Gameboard.SQUARESIZE,java.awt.Image.SCALE_SMOOTH));
       grass = new ImageIcon(new ImageIcon("Textures/Grass.jpg").getImage().getScaledInstance(Gameboard.SQUARESIZE,Gameboard.SQUARESIZE,java.awt.Image.SCALE_SMOOTH));
       dirt = new ImageIcon(new ImageIcon("Textures/Dirt.jpg").getImage().getScaledInstance(Gameboard.SQUARESIZE,Gameboard.SQUARESIZE,java.awt.Image.SCALE_SMOOTH));

       drawMap();

       add(new Enemy(this,100,5,new ImageIcon("Textures/Enemies/enemy1.png")));
   }
    public void paintComponent(Graphics g) {
        g.drawImage(this.myImage, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    public Point getEntrance() {
       for(int x=0;x<WIDTH;x++)
           for(int y=0;y<WIDTH;y++)
               if(map[x][y]==2)
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
        add(new Enemy(this,100,5,new ImageIcon("Textures/Enemies/enemy1.png")));
    }

    public void add(Enemy e) {
       enemies.add(e);
    }

    public void update() {

       drawMap();

       for (Enemy e : enemies) {
            if (e.isDead())
                enemies.remove(e);
            else if (e.isEscaped()) {
                enemies.remove(e);
                scoreboard.loseLife();
            }
        }

        if (!isRound) {
            nextRound();
            scoreboard.nextRound();
            isRound = true;
        }

        if (enemies.size() == 0 && isRound) {
            isRound = false;
            scoreboard.pause();
        }

        for (Enemy e : enemies) {
            e.draw(myBuffer);
            e.tick();
        }
        for (Tower t : towers)
            t.draw(myBuffer);

         repaint();

         if(scoreboard.getLife()==0)
             scoreboard.endGame();

    }
   
}