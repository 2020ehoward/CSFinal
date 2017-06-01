package Main;//Evan Howard, 11 May 2017

import Towers.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.*;
import java.util.LinkedList;

public class Gameboard extends JPanel
{
    //final variables
    //WIDTH is the number of squares/tiles on the map
    private static final int WIDTH = 13;
    //IMAGEWIDTH is the width in pixels of the buffered image the gameboard uses
    public static final int IMAGEWIDTH = 780;
    //SQUARESIZE is the size of each square
    public static final int SQUARESIZE = IMAGEWIDTH/WIDTH;
    //the map corresponds to the ingame map
    /*
    1 = Grass
    2 = Entrance (Dirt Texture)
    3 = Exit (Dirt Texture)
    4 = Water
     */
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

    //This is true if it is currently a round, false if it is before the game or between rounds
    private boolean isRound;
    //the enemy counters are used to spawn the enemies, the bigger it is the more it spawns
    //tower x and y are the location of the "fake" tower when you are placing on in before buying it
    //numEnemies is the total number of enemies on the board
    private int bEnemyCounter,towerX,towerY,numEnemies;

    //Copies of the tower and scoreboard from the mainpanel, as well as the parent JFrame
    private Towerboard towerboard;
    private Scoreboard scoreboard;
    private JFrame parentFrame;
    //the bufferedimage and graphics object used to display everything
    private BufferedImage myImage = new BufferedImage(IMAGEWIDTH, IMAGEWIDTH, 1);
    private Graphics myBuffer;
    //the three textures of the map
    private ImageIcon water,grass,dirt;
    //the two lists of the enemies on the screen and the towers on the screen
    private LinkedList<Enemy> enemies;
    private LinkedList<Tower> towers;
    //Requires a copy of the parent JFrame, the scoreboard, and the towerboard so it can interact with them directly
   public Gameboard(JFrame parentFrame, Scoreboard scoreboard,Towerboard towerboard)
   {
       //instantiates all fields
       this.parentFrame = parentFrame;
       this.towerboard = towerboard;
       this.scoreboard = scoreboard;
       //the game starts not in a round
       isRound=false;
       //there are no enemies or towers on the screen it starts, so the counters are 0 and the lists are empty
       bEnemyCounter=numEnemies=0;
       towers = new LinkedList<>();
       enemies = new LinkedList<>();

       //sets the graphics object to the graphics of the buffered image
       myBuffer = myImage.getGraphics();

       //sets the imageicons to the corresponding textures, resizing them to the same size as a square on the map
       water = new ImageIcon(new ImageIcon("Textures/water.jpg").getImage().getScaledInstance(Gameboard.SQUARESIZE,Gameboard.SQUARESIZE,java.awt.Image.SCALE_SMOOTH));
       grass = new ImageIcon(new ImageIcon("Textures/Grass.jpg").getImage().getScaledInstance(Gameboard.SQUARESIZE,Gameboard.SQUARESIZE,java.awt.Image.SCALE_SMOOTH));
       dirt = new ImageIcon(new ImageIcon("Textures/Dirt.jpg").getImage().getScaledInstance(Gameboard.SQUARESIZE,Gameboard.SQUARESIZE,java.awt.Image.SCALE_SMOOTH));

       //adds the two mouselisteners used for updating the map when you are placing a tower
       addMouseListener(new Mouse());
       addMouseMotionListener(new MouseMotion());

       //calls the drawmap method, drawing all the textures in their corresponding places
       drawMap();
   }

   //paints the contents of the buffered image
    public void paintComponent(Graphics g) {
        g.drawImage(this.myImage, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    //returns the coordinates of the '2' in the map array
    public Point getEntrance() {
       for(int y=0;y<WIDTH;y++)
           for(int x=0;x<WIDTH;x++)
               //when it is equal to 2, it returns a point object with the correct coordinates
               if(map[y][x]==2)
                   return new Point(x,y);
       //if an entrance is not found, it returns a null point
       return null;
    }

    //draws the corresponding texturews
    public void drawMap() {
       //it goes through every cell of the map
        for(int x=0;x<WIDTH;x++)
            for(int y=0;y<WIDTH;y++) {
            //if it is a 1 it draws a grass texture at the x and y values multiplied by the size of the square
                if(map[x][y]==1) {
                    myBuffer.drawImage(grass.getImage(),(Gameboard.SQUARESIZE)*y,(Gameboard.SQUARESIZE)*x,null);
                }
                //if it is a 4 it draws water
                else if(map[x][y]==4) {
                    myBuffer.drawImage(water.getImage(),(Gameboard.SQUARESIZE)*y,(Gameboard.SQUARESIZE)*x,null);
                }
                //and 0, 2, and 3 draws dirt (the path)
                else if(map[x][y]==0 || map[x][y]==2 || map[x][y]==3){
                    myBuffer.drawImage(dirt.getImage(),(Gameboard.SQUARESIZE)*y,(Gameboard.SQUARESIZE)*x,null);
                }
            }
    }

    public void nextRound() {
       //TODO inplement actual round with balanced spawning. New class to store every round?
        bEnemyCounter=scoreboard.getRound()*20;
        numEnemies=scoreboard.getRound()+1;

    }

    public void spawnBasicEnemy() {
       //adds an enemy with the traits of a basic enemy to the list, spawning into the game
        //it has 1 healt, a speed of 5, and the basic enemy texture scaled to 8/10 of a square
       enemies.add(new Enemy(this,1,5,new ImageIcon(new ImageIcon("Textures/Enemies/enemy1.png").getImage().getScaledInstance((int)(Gameboard.SQUARESIZE*0.8),(int)(SQUARESIZE*0.8),Image.SCALE_SMOOTH))));
    }

    public  void spawnBasicTower(int x,int y) {
       //spawns in a basic tower, adding it to the list
        //it has to corresponding basic bullet, with a damage of 1 and speed of 10, the basic tower texture, the x and y values specified by wherever this method is called, a speed(delay between shots) of 50, and a range of 3 squares
        towers.add(new BasicTower(this,x,y));
    }

    public void spawnCircleTower(int x,int y) {
       //spawns in a circle tower
        //corresponding bullet, texture, x and y, delay between shots of 50, and a range of 1.2 squares
        towers.add(new CircleTower(this,x,y));
    }

    public void spawnFreezeTower(int x,int y) {
       towers.add(new FreezeTower(this,x,y));
    }

    public void spawnSniperTower(int x,int y) {
       towers.add(new SniperTower(this,x,y));
    }

    //returns the list of enemies, used by towers and bullets to know where to shoot
    public LinkedList<Enemy> getEnemies() {
        return enemies;
    }

    //used to find where the mouse wants to place a tower
    private class Mouse extends MouseAdapter {
       private Mouse() {
       }

       //when the player presses the mouse
        public void mouseClicked(MouseEvent mouseEvent) {
           //instantiates a boolean to be used to tell if it is being placed somewhere where a tower can be placed
           boolean intersects = false;
           //x and y are set to the x and y coordinates of the mouse (changed so the center of a tower is on the mouse)
           int x=towerX;
           int y=towerY;
           //runs 6 times, going through every kind of tower
            for(int i=0;i<6;i++)
                //if the player has pressed on the button and is spawning in a tower
                if(towerboard.getSpawn(i)) {
                //go through the entire map
                    for (int r = 0; r < WIDTH; r++)
                        for (int c = 0; c < WIDTH; c++)
                            //if where the mouse is intersects with the path or water, set the intersects boolean to true
                            if (map[r][c] == 0 || map[r][c] == 4 || map[r][c] == 2 || map[r][c] == 3)
                                if (new Rectangle(x, y, SQUARESIZE, SQUARESIZE).intersects(new Rectangle(c * SQUARESIZE, r * SQUARESIZE, SQUARESIZE, SQUARESIZE)))
                                    intersects=true;
                    //go through every tower, and if the potential tower intersects with a current tower, set intersects to true
                    for(Tower t : towers)
                        if(t.getBounds().intersects(new Rectangle(x, y, SQUARESIZE, SQUARESIZE)))
                            intersects=true;
                    //if the potential tower isn't intersecting with anything
                    if(!intersects)
                                    //spawn in the tower that is being spawned in by the towerboard
                                    switch (i) {
                                        //spawns in the corresponding tower and the right x and y coordinates
                                        //subtracts the required number of coins from the towerboard
                                        //tells the towerboard that it is not spawning in that tower anymore
                                        case 0:
                                            towerboard.setCoin(towerboard.getCoin()-200);
                                            spawnBasicTower(x, y);
                                            towerboard.setSpawn(0);
                                            break;
                                        case 1:
                                            towerboard.setCoin(towerboard.getCoin()-360);
                                            spawnCircleTower(x,y);
                                            towerboard.setSpawn(1);
                                            break;
                                        case 2:
                                            towerboard.setCoin(towerboard.getCoin()-350);
                                            spawnSniperTower(x,y);
                                            towerboard.setSpawn(2);
                                            break;
                                        case 3:
                                            towerboard.setCoin(towerboard.getCoin()-380);
                                            spawnFreezeTower(x,y);
                                            towerboard.setSpawn(3);
                                            break;
                                    }
                }
        }
    }

    private class MouseMotion implements MouseMotionListener {

       //if the mouse is moved or dragged within the gameboard
        //set the towerX to the corresponding x value in the buffered image, minus a bit so the mouse is in the center of the tower
        //set the towerY to the corresponding y value in the buffered image, minus half a square so the mouse is in the center of the tower

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
            towerX=map(mouseEvent.getX(),SQUARESIZE/2,(parentFrame.getWidth()-84)-SQUARESIZE/2);
            towerY=map(mouseEvent.getY(),0,parentFrame.getHeight()-84)-SQUARESIZE/2;
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            towerX=map(mouseEvent.getX(),SQUARESIZE/2,(parentFrame.getWidth()-84)-SQUARESIZE/2);
            towerY=map(mouseEvent.getY(),0,parentFrame.getHeight()-84)-SQUARESIZE/2;
        }
    }

    //used to map the mouse coordinates onto the buffered image coordinates
    public int map(int in, int in_min,int in_max) {
       //maps a given value from one range to another
        //Ex: map(1,0,2,0,4) returns 2 since it is in the middle of 0 and 4
        return (in - in_min) * (800) / (in_max - in_min);
    }

    //runs every few milliseconds to update everything that has changed
    public void update() {

       //redraws the map
       drawMap();

       //checks if the game is paused, if not it updates everything
       if(!scoreboard.isPaused()) {

           //if it is not a round, it sets it to a round and spawns in the new enemies
           if (!isRound) {
               nextRound();
               //tells the scoreboard that it has moved on to the next round
               scoreboard.nextRound();
               isRound = true;
           }

           //TODO make this work with the round system by probably making a new variable to change the intervals of spawning
           if (bEnemyCounter % 20 == 0)
               spawnBasicEnemy();

           //goes through the enemies list
           for (int i = 0; i < enemies.size(); i++) {
               //if it is dead it removes it from the map and gives coins to the player for destroying the enemy
               if (enemies.get(i).isDead()) {
                   towerboard.setCoin(towerboard.getCoin()+(enemies.get(i).getLevel()+1) * 25);
                   enemies.remove(i);
                   //tells the game that there is one less enemy
                   numEnemies--;
                   //if it has escaped it removes it from the map, loses a life, and tells the game there is one less enemy
               } else if (enemies.get(i).isEscaped()) {
                   enemies.remove(i);
                   scoreboard.loseLife();
                   numEnemies--;
               }
           }

           //if there are no enemies left and it is in a round
           if (numEnemies==0 && isRound) {
               //ends the round
               isRound = false;
               //pauses the game
               scoreboard.pause();
               //goes through all the towers and tells them the round is over
               for (Tower t : towers)
                   t.endRound();
           }

           //calls the tick method for all enemies, updating them
           for (Enemy e : enemies) {
               e.tick();
           }

           //calls the tick method for all towers, updating them
           for (Tower t : towers) {
               t.tick();
           }

           if(bEnemyCounter>=0)
               bEnemyCounter--;
       }

       //draws all enemies
        for (Enemy e : enemies) {
            e.draw(myBuffer);
        }

        //draws all towers
        for (Tower t : towers) {
            t.draw(myBuffer);
        }

        //draws the "fake" tower when the player is spawning on in
        for (int i = 0; i < 6; i++) {
           //checks if the player is spawning a tower in
            if (towerboard.getSpawn(i)) {
                //sets the color of the buffer to the color of the range circle, making it slightly transparent
                Color rangeCircle = Color.GRAY;
                myBuffer.setColor(new Color(rangeCircle.getRed(), rangeCircle.getGreen(), rangeCircle.getBlue(), 150));
                //draws the texture of the corresponding tower and the a circle showing the range of that tower
                switch (i) {
                    case 0:
                        myBuffer.fillOval((towerX + SQUARESIZE / 2) - 3 * SQUARESIZE, (towerY + SQUARESIZE / 2) - 3 * SQUARESIZE, 6 * SQUARESIZE, 6 * SQUARESIZE);
                        myBuffer.drawImage(new ImageIcon(new ImageIcon("Textures/Towers/Tower0.png").getImage().getScaledInstance(SQUARESIZE, SQUARESIZE, Image.SCALE_SMOOTH)).getImage(), towerX, towerY, null);
                        break;
                    case 1:
                        myBuffer.fillOval((towerX + SQUARESIZE / 2) - (int)(1.6*SQUARESIZE), (towerY + SQUARESIZE / 2) - (int)(1.6 *SQUARESIZE), (int)(3.2 * SQUARESIZE), (int)(3.2 * SQUARESIZE));
                        myBuffer.drawImage(new ImageIcon(new ImageIcon("Textures/Towers/Tower1.png").getImage().getScaledInstance(SQUARESIZE, SQUARESIZE, Image.SCALE_SMOOTH)).getImage(), towerX, towerY, null);
                        break;
                    case 2:
                        myBuffer.drawImage(new ImageIcon(new ImageIcon("Textures/Towers/Tower2.png").getImage().getScaledInstance(SQUARESIZE, SQUARESIZE, Image.SCALE_SMOOTH)).getImage(), towerX, towerY, null);
                        break;
                    case 3:
                        myBuffer.fillOval((towerX + SQUARESIZE / 2) - 2*SQUARESIZE, (towerY + SQUARESIZE / 2) - 2 *SQUARESIZE, 4 * SQUARESIZE, 4 * SQUARESIZE);
                        myBuffer.drawImage(new ImageIcon(new ImageIcon("Textures/Towers/Tower3.png").getImage().getScaledInstance(SQUARESIZE, SQUARESIZE, Image.SCALE_SMOOTH)).getImage(), towerX, towerY, null);
                    break;

                }
            }
        }

        //draws the buffered image to the screen with all changes
         repaint();

    }
   
}