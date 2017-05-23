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

   public MainPanel(JFrame parentFrame)
   {
      setLayout(new BorderLayout());
   
      scoreboard = new Scoreboard(this);
      add(scoreboard,BorderLayout.SOUTH);
   
      towerboard = new Towerboard();
      add(towerboard,BorderLayout.EAST);

       gameboard = new Gameboard(parentFrame,scoreboard,towerboard);
       add(gameboard,BorderLayout.CENTER);
      
      t = new javax.swing.Timer(10, new Listener());
      t.start();
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
           t.setDelay(x);
   }
   
}