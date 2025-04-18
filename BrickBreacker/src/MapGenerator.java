import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {

    public int map[][];
    public int brickWidth;
    public int brickHeight;

    // Constructor with corrected parameter syntax
    public MapGenerator(int row, int col) {
        map = new int[row][col];

        // Initialize bricks with value 1 (indicating they exist)
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = 1;
            }
        }

        brickWidth = 540 / col;
        brickHeight = 150 / row;
    }

    // Draws the bricks on the screen
    public void draw(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {  // Fixed loop condition
            for (int j = 0; j < map[0].length; j++) {  // Fixed spelling error
                if (map[i][j] > 0) {
                    g.setColor(Color.WHITE);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    
                    // Draw black borders around bricks for visibility
                    g.setColor(Color.BLACK);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    
                    
                }
            }
        }
    }
    
    public void setBrickValue(int value ,int row,int col) {
    	map[row][col]=value;
    }
}
