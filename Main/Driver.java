package Main;//Evan Howard, 11 May 2017

import javax.swing.JFrame;
public class Driver
{
   public static void main(String[] args)
   {
      //Standard driver, creates an 800x800 frame with a main panel within it, then sets it to visible
      JFrame frame = new JFrame("Spaghetti Tower Defense");
      frame.setSize(800, 800);
      frame.setLocation(0, 0);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(new MainPanel((frame)));
      frame.setVisible(true);
   }
}