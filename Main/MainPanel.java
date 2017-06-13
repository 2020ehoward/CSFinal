package Main;//Evan Howard, 11 May 2017

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.InputMismatchException;

public class MainPanel extends JPanel
{

    //fields are all the sub panels, plus the timer used for all logic
   private Scoreboard scoreboard;
   private Gameboard gameboard;
   private Towerboard towerboard;
   private javax.swing.Timer t;

   //this requires the parent jframe as an argument to be passed to the gameboard
   public MainPanel(JFrame parentFrame)
   {
       //Main panel uses a border layout
      setLayout(new BorderLayout());

      //Creates a scoreboard panel on the bottom of the screen
      scoreboard = new Scoreboard(this);
      add(scoreboard,BorderLayout.SOUTH);

      //Creates a "towerboard" panel on the right of the screen, this is the shop
      towerboard = new Towerboard();
      add(towerboard,BorderLayout.EAST);

      //Creates a gameboard in the center of the screen, this will have the map on it
       gameboard = new Gameboard(parentFrame,scoreboard,towerboard);
       add(gameboard,BorderLayout.CENTER);

       //creates a timer with an initial delay of 10 milliseconds and starts it
      t = new javax.swing.Timer(10, new Listener());
      t.start();
   }
   private class Listener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
          //every time the game ticks, it runs the corresponding update methods in each of the subpanels
          gameboard.update();
          towerboard.update();
          scoreboard.update();

          //if the player reaches the end of the rounds, tell the scoreboard the game is over
          if(gameboard.isGameOver())
              scoreboard.endGame(true);
          //if the player runs out of life, tell the gameboard the game is over
          else if(scoreboard.isGameOver())
              gameboard.endGame();
      }
   }
   public void setSpeed(int x) {
       //used to change the speed of the game
       t.setDelay(x);
   }
   
}