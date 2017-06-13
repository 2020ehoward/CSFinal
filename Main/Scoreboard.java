package Main;//Evan Howard, 11 May 2017
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Scoreboard extends JPanel
{   
   public static final Color BACKGROUND = new Color(255, 227, 191);

   //isPaused is true when the player pauses the game
    //isGameOver is true when the player runs out of life
   private boolean isPaused,isGameOver;
   //how much life is left, the round the player is on, the speed of the game (timer in the main panel)
   private int life,round,speed;
   //displays the life, and the current round
   private JLabel lifeLabel,roundLabel;
   //four buttons used to control speed of the game
   private JToggleButton pause,speed1,speed2,speed3;
   //parent main panel
   private MainPanel mPanel;
   public Scoreboard(MainPanel mPanel)
   {
       this.mPanel = mPanel;
       //player starts with 60 life
       life = 60;
       //starts at round 0
       round = 0;
       //starting speed is 10 milliseconds
       speed = 10;
       //the game starts paused and the game is not over
       isPaused=true;
       isGameOver=false;

       setLayout(new BorderLayout());

       //the panel with all the speed buttons
       JPanel speedPanel = new JPanel();
       speedPanel.setBackground(BACKGROUND);

       //each buttons has an action listener with the constructor of the speed from 0 (paused) to 4 (very fast)
       //each buttons also has the corresponding texture
       //the pause button starts pressed down
       pause = new JToggleButton(new ImageIcon("Textures/Buttons/Speed0.png"),true);
       pause.addActionListener(new speedListener(0));
       speedPanel.add(pause);

       speed1 = new JToggleButton(new ImageIcon("Textures/Buttons/Speed1.png"));
       speed1.addActionListener(new speedListener(1));
       speedPanel.add(speed1);

       speed2 = new JToggleButton(new ImageIcon("Textures/Buttons/Speed2.png"));
       speed2.addActionListener(new speedListener(2));
       speedPanel.add(speed2);

       speed3 = new JToggleButton(new ImageIcon("Textures/Buttons/Speed3.png"));
       speed3.addActionListener(new speedListener(3));
       speedPanel.add(speed3);

       //buttons are in the left of the panel
       add(speedPanel,BorderLayout.WEST);

       //round and life use the variables to display them to the player
        lifeLabel = new JLabel("        Life Remaining: "+life);
        lifeLabel.setFont(new Font("Monospaced",Font.BOLD,16));
        roundLabel = new JLabel("Round: "+round+"    ");
       roundLabel.setFont(new Font("Monospaced",Font.BOLD,16));

       //life is in the center of the panel
        add(lifeLabel,BorderLayout.CENTER);
        //round is in the right
        add(roundLabel,BorderLayout.EAST);

        //background of all panels is the static color BACKGROUND
      setBackground(BACKGROUND);
   }

   //removes the specifed life
    public void loseLife(int damage) {
        this.life-=damage;
    }

    //increases the round number
    public void nextRound() {
        this.round++;
    }

    //returns the round that the game is on
    public int getRound() {
        return round;
    }

    //pauses the game, same thing as if the played pressed pause
    public void pause() {
       pause.doClick();
    }

    //returns the booleans to tell the game if it is paused or the game has ended
    public boolean isPaused() {
        return isPaused;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    //used to end the game, all buttons are disabled and the game is paused
    public void endGame(boolean playerWins) {
       if(!playerWins && !isGameOver)
           JOptionPane.showMessageDialog(getParent(),"Game Over!");
       isGameOver=true;
       pause();
       pause.setEnabled(false);
       speed1.setEnabled(false);
        speed2.setEnabled(false);
        speed3.setEnabled(false);
    }

    //listener for all the speed button
    /*
    Important to remember that JToggleButtons act as switches, so
    if it is not pressed down and gets pressed, then isSelected is
    now true and vice versa
     */
    private class speedListener implements ActionListener {

       //mySpeed goes from 0 (paused) to 4 (very fast)
       int mySpeed;
       //constructor used to change speed
        public speedListener(int x) {
            mySpeed = x;
        }
        public void actionPerformed(ActionEvent e) {
            //creates copy of button pressed in order to check if it has been pressed
            JToggleButton temp = (JToggleButton)e.getSource();
            //if wasn't pressed before, and is now selected
            if(temp.isSelected()) {
                //if it the pause button, pause the game, otherwise unpause the game and change the speed
                switch (mySpeed) {
                    case 0:
                        isPaused=true;
                        break;
                    case 1:
                        isPaused=false;
                        speed = 50;
                        break;
                    case 2:
                        isPaused=false;
                        speed = 10;
                        break;
                    case 3:
                        isPaused=false;
                        speed = 2;
                        break;
                }
                //if another button is pressed and it is not the button that the player just pressed, press it again, disabling it
                //this is so only one button is selected at a time
                if(!pause.equals(temp) && pause.isSelected())
                    pause.doClick();
                if(!speed1.equals(temp) && speed1.isSelected())
                    speed1.doClick();
                if(!speed2.equals(temp) && speed2.isSelected())
                    speed2.doClick();
                if(!speed3.equals(temp) && speed3.isSelected())
                    speed3.doClick();
                //set the speed of the main panel timer to reflect the new speed
                mPanel.setSpeed(speed);
            }
            //if the player is pressing a button that is already pressed
            else {
                //find out which button has been pressed, and press it again, keeping it selected
                switch(mySpeed) {
                    case 0:
                        if(isPaused)
                            temp.doClick();
                        break;
                    case 1:
                        if(speed == 50 && !isPaused)
                            temp.doClick();
                        break;
                    case 2:
                        if(speed == 10 && !isPaused)
                            temp.doClick();
                        break;
                    case 3:
                        if(speed == 2 && !isPaused)
                            temp.doClick();
                        break;
                }
            }
        }
    }
    //updates the labels to show any changes in life or round
    public void update() {
        lifeLabel.setText("        Life Remaining: "+life);
        roundLabel.setText("Round: "+round+"    ");

        //if the player runs out of life, end the game
        if(life<=0) {
            life=0;
            endGame(false);
        }
   }
   
}