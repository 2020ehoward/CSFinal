package Main;//Evan Howard, Iyouel Endashaw, 11 May 2017
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Towerboard extends JPanel
{
    //info booleans are used to keep track of when to display info about the tower (price, brief description)
    //spawn booleans are used to tell the gameboard when the player has pressed a button and is buying a tower
    private boolean[] info,spawn;
    //the jbuttons that correspond to each tower and allow the player to purchase them
   private JButton[] towers;
   //coin label displays number of coins, infolabel contains information about the tower that the player is hovering over
   private JLabel coinLabel,infoLabel;
   //number of coins the player has
   private int coin;
   public Towerboard()
   {
       //all booleans start as false, because no info is displayed and nothing is being spawned
       info = new boolean[6];
       spawn = new boolean[6];
       for(int i=0;i<info.length;i++) {
           info[i] = false;
           spawn[i] = false;
       }
       //player starts with 650 coins
       coin = 650;

       //border layout with margins of 10 pixels on the top, 5 on the left, and 5 on the right
      setLayout(new BorderLayout());
      setBorder(BorderFactory.createEmptyBorder(10, 5, 0, 5));

      //labelpanel is a subpanel with the coins and the info on it
      JPanel labelPanel = new JPanel();
      labelPanel.setBackground(Scoreboard.BACKGROUND);
      labelPanel.setLayout(new BorderLayout());

      //info label has the standard game font, dimensions of 130x160, and is in the south of the labelpanel
      infoLabel = new JLabel();
      infoLabel.setFont(new Font("Monospaced",Font.BOLD,12));
      infoLabel.setPreferredSize(new Dimension(130,160));
      labelPanel.add(infoLabel,BorderLayout.SOUTH);

      //coin label displays the number of coins in the north of the labelpanel, in the standard font
      coinLabel = new JLabel("Coins: "+coin);
       coinLabel.setFont(new Font("Monospaced",Font.BOLD,24));
       labelPanel.add(coinLabel,BorderLayout.NORTH);

       //the subpanel with the labels on it is in the north of the towerboard
       add(labelPanel,BorderLayout.NORTH);

       //there are six kinds of towers, with six textures
      towers = new JButton[6];
      ImageIcon[] towerTextures = new ImageIcon[6];

      //each texture is stored as Towers.Tower#.png with the # being the number it is in the array
       //this loop goes through the texture array, resizing the corresponding texture to the size of the button, and placing it in the array
      for(int i=0;i<towerTextures.length;i++)
          towerTextures[i] = new ImageIcon(new ImageIcon("Textures/Towers/Tower"+i+".png").getImage().getScaledInstance(64,72, Image.SCALE_SMOOTH));

      //this panel contains the buttons that purchase towers, as well as the big cancel button
      JPanel sCancel = new JPanel();
      sCancel.setBackground(Scoreboard.BACKGROUND);
      sCancel.setLayout(new GridLayout(2,1,0,10));

      //this panel contains all the tower buttons
      JPanel shop = new JPanel();
      shop.setBackground(Scoreboard.BACKGROUND);
      shop.setLayout(new GridLayout(3,2,10,10));


      //goes through all six kinds of tower
      for(int i=0;i<6;i++) {
          //makes it a new button with the applicable texture
            towers[i]=new JButton(towerTextures[i]);
            //sets the background to be the same as the background of the scoreboard and towerboard
              towers[i].setBackground(Scoreboard.BACKGROUND);
              //adds the tower button listener with the constructor being the number in the array that it is
              towers[i].addActionListener(new towerListener(i));
              //size of the buttons
              towers[i].setPreferredSize(new Dimension(64,72));
              //adds the mouselistener to detect when the player is hovering over it
              towers[i].addMouseListener(new mouseListener(i));
              //adds it to the shop panel
              shop.add(towers[i]);
          }
          //water and super tower are disabled by default as the player does not have enough coins to purchase them
        towers[4].setEnabled(false);
          towers[5].setEnabled(false);

          //the cancel button is a normal jbutton with an action listener
         JButton cancel = new JButton("Cancel");
      cancel.addActionListener(new cancelListener());
      //it has the word cancel on it in the standard font
         cancel.setFont(new Font("Monospaced",Font.BOLD, 24));

         //scancel panel has the shop and the cancel button
         sCancel.add(shop);
       sCancel.add(cancel);
       //scancel is placed in the south of the towerboard
         add(sCancel,BorderLayout.SOUTH);

         //the background color is the same as the background of the scoreboard
      setBackground(Scoreboard.BACKGROUND);
   }

   private class cancelListener implements ActionListener {
       //when the cancel button is pressed, it sets all spawning booleans to false
       public void actionPerformed(ActionEvent actionEvent) {
           for(int i=0;i<6;i++)
               setSpawn(i);
       }
   }

   private class mouseListener implements MouseListener {

       //myTower is the number of the tower in the array that the button corresponds to
       private int myTower;
       public mouseListener(int myTower) {
           this.myTower=myTower;
       }

       //next three methods only in because mouselistener is an interface, nothing needs to happen when those events happen
       public void mouseClicked(MouseEvent mouseEvent) {

       }

       public void mousePressed(MouseEvent mouseEvent) {

       }

       public void mouseReleased(MouseEvent mouseEvent) {

       }

       //if the mouse is in the button, it turns the corresponding info boolean to true
       public void mouseEntered(MouseEvent mouseEvent) {
           info[myTower]=true;
       }

       //if it leaves the button, it turns the corresponding info boolean to false
       public void mouseExited(MouseEvent mouseEvent) {
           info[myTower]=false;
       }
   }

    private class towerListener implements ActionListener {

       //the constructor is the number in the array of the tower
        int myTower;
        public towerListener(int x) {
            myTower = x;
        }
        //when clicked it turns the corresponding spawn boolean to true
        public void actionPerformed(ActionEvent e) {
            spawn[myTower]=true;
        }
        }

        //returns the spawn boolean for the number tower asked for
        public boolean getSpawn(int x) {
       return spawn[x];
        }

        //returns number of coins
    public int getCoin() {
        return coin;
    }

    //changes the number of coins
    public void setCoin(int coin) {
        this.coin = coin;
        //goes through all the towers, if the player has enough coins it is enabled, if not it is disabled
        for(int i=0;i<towers.length;i++)
            towers[i].setEnabled(true);
        if(this.coin<380)
            towers[3].setEnabled(false);
        if(this.coin<360)
            towers[1].setEnabled(false);
        if(this.coin<350)
            towers[2].setEnabled(false);
        if(this.coin<200)
            towers[0].setEnabled(false);
    }

    //sets the specified spawn boolean to false
    public void setSpawn(int x) {
        spawn[x] = false;
    }


       //the tick/update method
        public void update() {
       //checks if any info booleans are true
       //if there are, changes the test to the applicable info, if none are true it sets it to blank
       if(info[0]) {
           infoLabel.setText("<html><center>200 Coins<br>Spaghetti & Meatballs<br>A standard tower that shoots moderately fast</center></html>");
       }
       else if(info[1])
           infoLabel.setText("<html><center>360 Coins<br>Pepperoni Pizza<br>A slow tower that shoots in a circle</center></html>");
       else if(info[2])
           infoLabel.setText("<html><center>350 Coins<br>Italian Sub<br>Futuristic sandwich tech allows this sub to shoot with infinite range</center></html>");
       else if(info[3])
           infoLabel.setText("<html><center>380 Coins<br>Italian Ice<br>Freezes nearby hands every couple seconds</center></html>");
       else
           infoLabel.setText("");
       //updates the number of coins
       coinLabel.setText("Coins: "+coin);
   }
}