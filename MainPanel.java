//Evan Howard, 11 May 2017
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.JOptionPane;
import java.util.*;

public class MainPanel extends JPanel
{
   
   private Scoreboard scoreboard;
   private Gameboard gameboard;
   private Towerboard towerboard;
   private javax.swing.Timer t;

   public MainPanel()
   {
      setLayout(new BorderLayout());
   
      scoreboard = new Scoreboard(this);
      add(scoreboard,BorderLayout.SOUTH);
   
      gameboard = new Gameboard();
      add(gameboard,BorderLayout.CENTER);
   
      towerboard = new Towerboard();
      add(towerboard,BorderLayout.EAST);
      
      t = new javax.swing.Timer(0, new Listener());
   }
   private class Listener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
          gameboard.update();
          towerboard.update();
          scoreboard.update();
      }
   }
   public void setSpeed(int x) {
       if(x!=0) {
           t.start();
           t.setDelay(x);
       }
       else
           t.stop();
   }
   
}