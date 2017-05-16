//Evan Howard, 11 May 2017

import javax.swing.JFrame;
public class Driver
{
   public static void main(String[] args)
   {
      JFrame frame = new JFrame("Spaghetti Tower Defense");
      frame.setSize(800, 800);
      frame.setLocation(0, 0);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(new MainPanel());
      frame.setVisible(true);
   }
}