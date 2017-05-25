//Evan Howard, 11 May 2017

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
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
    private int bEnemyCounter,towerX,towerY,numEnemies;

    private Towerboard towerboard;
    private Scoreboard scoreboard;
    private JFrame parentFrame;
    private BufferedImage myImage = new BufferedImage(IMAGEWIDTH, IMAGEWIDTH, 1);
    private Graphics myBuffer;
    private ImageIcon water,grass,dirt;
    private LinkedList<Enemy> enemies;
    private LinkedList<Tower> towers;
   public Gameboard(JFrame parentFrame, Scoreboard scoreboard,Towerboard towerboard)
   {
       this.parentFrame = parentFrame;
       this.towerboard = towerboard;
       this.scoreboard = scoreboard;
       isRound=false;
       bEnemyCounter=numEnemies=0;
       towers = new LinkedList<>();
       enemies = new LinkedList<>();

       myBuffer = myImage.getGraphics();

       water = new ImageIcon(new ImageIcon("Textures/water.jpg").getImage().getScaledInstance(Gameboard.SQUARESIZE,Gameboard.SQUARESIZE,java.awt.Image.SCALE_SMOOTH));
       grass = new ImageIcon(new ImageIcon("Textures/Grass.jpg").getImage().getScaledInstance(Gameboard.SQUARESIZE,Gameboard.SQUARESIZE,java.awt.Image.SCALE_SMOOTH));
       dirt = new ImageIcon(new ImageIcon("Textures/Dirt.jpg").getImage().getScaledInstance(Gameboard.SQUARESIZE,Gameboard.SQUARESIZE,java.awt.Image.SCALE_SMOOTH));

       addMouseListener(new Mouse());
       addMouseMotionListener(new MouseMotion());

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
       //here we go bois // well maybe later
//        switch(scoreboard.getRound()){
//            case 0: bEnemyCounter=400;
//            numEnemies=20;
//            break;
//        }
        bEnemyCounter=scoreboard.getRound()*20;
        numEnemies=scoreboard.getRound()+1;

    }

    public void spawnBasicEnemy() {
       enemies.add(new Enemy(this,1,5,new ImageIcon(new ImageIcon("Textures/Enemies/enemy1.png").getImage().getScaledInstance((int)(Gameboard.SQUARESIZE*0.8),(int)(SQUARESIZE*0.8),Image.SCALE_SMOOTH))));
    }

    public  void spawnBasicTower(int x,int y) {
        towers.add(new Tower(this,new Bullet(this,new ImageIcon("Textures/Bullets/Bullet0.png"),1,10),new ImageIcon("Textures/Towers/Tower0.png"),x,y,50,3*SQUARESIZE));
    }

    public void spawnCircleTower(int x,int y) {
        towers.add(new Tower(this,new Bullet(this,new ImageIcon("Textures/Bullets/Bullet1.png"),1,10),new ImageIcon("Textures/Towers/Tower1.png"),x,y,50,3*SQUARESIZE));

    }

    public LinkedList<Enemy> getEnemies() {
        return enemies;
    }

    private class Mouse extends MouseAdapter {
       private Mouse() {
       }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
           boolean intersects = false;
           int x=towerX;
           int y=towerY;
            super.mouseClicked(mouseEvent);
            for(int i=0;i<6;i++)
                if(towerboard.getSpawn(i)) {
                    for (int r = 0; r < WIDTH; r++)
                        for (int c = 0; c < WIDTH; c++)
                            if (map[r][c] == 0 || map[r][c] == 4 || map[r][c] == 2 || map[r][c] == 3)
                                if (new Rectangle(x, y, SQUARESIZE, SQUARESIZE).intersects(new Rectangle(c * SQUARESIZE, r * SQUARESIZE, SQUARESIZE, SQUARESIZE)))
                                    intersects=true;
                    for(Tower t : towers)
                        if(t.getBounds().intersects(new Rectangle(x, y, SQUARESIZE, SQUARESIZE)))
                            intersects=true;
                    if(!intersects)
                                    switch (i) {
                                        case 0:
                                            towerboard.setCoin(towerboard.getCoin()-200);
                                            spawnBasicTower(x, y);
                                            towerboard.setSpawn(0);
                                            break;
                                    }
                }
        }
    }

    private class MouseMotion implements MouseMotionListener {


        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
            towerX=map(mouseEvent.getX(),SQUARESIZE/2,(parentFrame.getWidth()-84)-SQUARESIZE/2,0,800);
            towerY=map(mouseEvent.getY(),0,parentFrame.getHeight()-84,0,800)-SQUARESIZE/2;
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            towerX=map(mouseEvent.getX(),SQUARESIZE/2,(parentFrame.getWidth()-84)-SQUARESIZE/2,0,800);
            towerY=map(mouseEvent.getY(),0,parentFrame.getHeight()-84,0,800)-SQUARESIZE/2;
        }
    }

    public int map(int in, int in_min,int in_max,int out_min, int out_max) {
        return (in - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    public void update() {

       drawMap();

       if(!scoreboard.isPaused()) {

           if (!isRound) {
               nextRound();
               scoreboard.nextRound();
               isRound = true;
           }

           if (bEnemyCounter % 20 == 0)
               spawnBasicEnemy();

           for (int i = 0; i < enemies.size(); i++) {
               if (enemies.get(i).isDead()) {
                   towerboard.setCoin(towerboard.getCoin()+enemies.get(i).getLevel() * 25);
                   enemies.remove(i);
                   numEnemies--;
               } else if (enemies.get(i).isEscaped()) {
                   enemies.remove(i);
                   scoreboard.loseLife();
                   numEnemies--;
               }
           }

           if (numEnemies==0 && isRound) {
               isRound = false;
               scoreboard.pause();
               for (Tower t : towers)
                   t.endRound();
           }

           for (Enemy e : enemies) {
               e.tick();
           }

           for (Tower t : towers) {
               t.tick();
           }

           if(bEnemyCounter>=0)
               bEnemyCounter--;
       }

        for (Enemy e : enemies) {
            e.draw(myBuffer);
        }

        for (Tower t : towers) {
            t.draw(myBuffer);
        }

        for (int i = 0; i < 6; i++) {
            if (towerboard.getSpawn(i)) {
                Color rangeCircle = Color.GRAY;
                myBuffer.setColor(new Color(rangeCircle.getRed(), rangeCircle.getGreen(), rangeCircle.getBlue(), 150));
                switch (i) {
                    case 0:
                        myBuffer.fillOval((towerX + SQUARESIZE / 2) - 3 * SQUARESIZE, (towerY + SQUARESIZE / 2) - 3 * SQUARESIZE, 6 * SQUARESIZE, 6 * SQUARESIZE);
                        myBuffer.drawImage(new ImageIcon(new ImageIcon("Textures/Towers/Tower0.png").getImage().getScaledInstance(SQUARESIZE, SQUARESIZE, Image.SCALE_SMOOTH)).getImage(), towerX, towerY, null);
                        break;
                }
            }
        }

         repaint();

         if(scoreboard.getLife()==0)
             scoreboard.endGame();

    }
   
}