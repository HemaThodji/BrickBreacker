import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;

    private Timer timer;
    private int delay = 8;

    private int PlayerX = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    
    
    private MapGenerator map;

    // Constructor
    public GamePlay() {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // Background
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 692, 592);

        // Drawing bricks
        map.draw((Graphics2D) g);

        // Borders
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        
        //Score
        g.setColor(Color.WHITE);
        g.setFont(new Font ("serif", Font.BOLD,25));
        g.drawString(""+score,590,30);

        // Paddle
        g.setColor(Color.YELLOW);
        g.fillRect(PlayerX, 550, 100, 8);

        // Ball
        g.setColor(Color.YELLOW);
        g.fillOval(ballposX, ballposY, 20, 20);
        
        if(totalBricks<=0) {
        	play=false;
        	ballXdir=0;
        	ballYdir=0;
        	
        	g.setColor(Color.RED);
        	g.setFont(new Font ("serif", Font.BOLD,30));
        	g.drawString("You Won:",260,333);
        	
        	g.setFont(new Font ("serif", Font.BOLD,20));
        	g.drawString("Press Enter to Restart",230,350);
       
        }
        
        if(ballposY>570) {
        	play=false;
        	ballXdir=0;
        	ballYdir=0;
        	
        	g.setColor(Color.RED);
        	g.setFont(new Font ("serif", Font.BOLD,30));
        	g.drawString("Game Over,Scores:",190,333);
        	
        	g.setFont(new Font ("serif", Font.BOLD,20));
        	g.drawString("Press Enter to Restart",230,350);
        }
       g.dispose();
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (play) {
            // Ball and Paddle Collision
            if (new Rectangle(ballposX, ballposY, 20, 20)
                    .intersects(new Rectangle(PlayerX, 550, 100, 8))) {
                ballYdir = -ballYdir; // Corrected direction
            }

            // Ball and Brick Collision
            outerLoop:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {

                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);

                        if (ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j); // Ensure this method exists
                            totalBricks--;
                            score += 5;

                            // Corrected Collision Direction
                            if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }

                            break outerLoop; // Exit both loops
                        }
                    }
                }
            }

            // Move the ball
            ballposX += ballXdir;
            ballposY += ballYdir;

            // Ball collision with left wall
            if (ballposX <= 0) {
                ballXdir = -ballXdir;
            }

            // Ball collision with right wall
            if (ballposX >= 670) { // Considering screen width 692 and ball size 20
                ballXdir = -ballXdir;
            }

            // Ball collision with top wall
            if (ballposY <= 0) {
                ballYdir = -ballYdir;
            }

            // Ball falls below paddle (Game Over)
            if (ballposY >= 570) { // Considering screen height 592 and ball size 20
                play = false;
                ballXdir = 0;
                ballYdir = 0;
                System.out.println("Game Over! Final Score: " + score);
            }
        }
        repaint(); // Redraw the game panel
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (PlayerX >= 600) {
            	PlayerX=600;
            }else {
                moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (PlayerX < 10) {
            	PlayerX=10;
            }else {
                moveLeft();
            }
        }
        
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
        if(!play){
        	
        	play=true;
        	ballposX=120;
        	ballposY=350;
        	ballXdir= -1;
        	ballYdir=-2;
        	PlayerX=310;
        	score=0;
        	totalBricks=21;
        	map=new MapGenerator(3 , 7);
        	
        	repaint();
        	
        }
        }
    }

    public void moveRight() {
        play = true;
        PlayerX += 20;
      
    }

    public void moveLeft() {
        play = true;
        PlayerX -= 20;
        
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}

