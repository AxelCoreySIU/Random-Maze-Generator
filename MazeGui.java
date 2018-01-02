import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Path2D;
import javax.swing.JPanel;

/**
 * This class is responsible for drawing the walls of the maze, drawing the player, and recognizing the
 * user pressing the arrow keys in order to move the cursor through the maze 
 * 
 * @author Axel
 *
 */
@SuppressWarnings("serial")
public class MazeGui extends JPanel implements KeyListener{
	Generator m1;
	int offsetX = 10;
	int offsetY = 10;
	int cellSize = 20;
	
	Integer moveCounter = 0;
	
	int pointX, pointY, oldX, oldY;
	boolean erase;
	
	public MazeGui(){
		m1 = new Generator();
		pointX = offsetX + cellSize / 2;
		pointY = offsetY + cellSize / 2;
		oldX = pointX;
		oldY = pointY;
		addKeyListener(this);
	}
	public MazeGui(Generator m2){
		m1 = m2;
		pointX = offsetX + cellSize / 2;
		pointY = offsetY + cellSize / 2;
		oldX = pointX;
		oldY = pointY;
		addKeyListener(this);
	}
	public MazeGui(Generator m2, int cellSize2){
		m1 = m2;
		cellSize = cellSize2;
		pointX = offsetX + cellSize / 2;
		pointY = offsetY + cellSize / 2;
		oldX = pointX;
		oldY = pointY;
		addKeyListener(this);
	}

	/**
	 * This method loops through each of the x and y coordinates of each cell and
	 * draws a wall if a wall is present for that cell
	 * @param g accepts a Graphics object
	 */
	private void doDrawing(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.cyan);
		Dimension size = getSize();
		Insets insets = getInsets();
		int w = size.width - insets.left - insets.right;
		int h = size.height - insets.top - insets.bottom;
		
		g2d.setBackground(Color.black);
		g2d.clearRect(0,  0,  w, h);
		
		Path2D mazeShape = new Path2D.Double();
		
		//Goes through each cell, finds if there is a wall, if so, draws a line
		int x, y;
		for(Integer i = 0; i < m1.width; i++){
			x = i * cellSize + offsetX;
			for(Integer j = 0; j < m1.height; j++){
				y = j * cellSize + offsetY;
				
				if(m1.cells[i][j].walls[0] == 1){
					mazeShape.moveTo(x, y);
					mazeShape.lineTo(x + cellSize, y);
					g2d.drawLine(x, y, x + cellSize, y);
				}
				if(m1.cells[i][j].walls[1] == 1){
					mazeShape.moveTo(x + cellSize, y);
					mazeShape.lineTo(x + cellSize, y + cellSize);
					g2d.drawLine(x + cellSize, y, x + cellSize, y + cellSize);
				}
				if(m1.cells[i][j].walls[2] == 1){
					mazeShape.moveTo(x, y + cellSize);
					mazeShape.lineTo(x + cellSize, y + cellSize);
					g2d.drawLine(x, y + cellSize, x + cellSize, y + cellSize);
				}
				if(m1.cells[i][j].walls[3] == 1){
					mazeShape.moveTo(x, y);
					mazeShape.lineTo(x, y + cellSize);
					g2d.drawLine(x, y, x, y + cellSize);
				}
			}
		}
		//Displays where the cursor is according to each direction moved
		x = (oldX - offsetX - cellSize / 2) / cellSize;
		y = (oldY - offsetY - cellSize / 2) / cellSize;
		//left
		if (x >= 0 && x < m1.width && oldX > pointX
				&& m1.cells[x][y].walls[3] == 1){
			pointX = oldX;
			pointY = oldY;
		}
		//right
		else if (x >= 0 && x < m1.width && oldX < pointX
				&& m1.cells[x][y].walls[1] == 1){
			pointX = oldX;
			pointY = oldY;
		}
		//up
		else if (y >= 0 && y < m1.height && oldY > pointY
				&& m1.cells[x][y].walls[0] == 1){
			pointX = oldX;
			pointY = oldY;
		}
		//down
		else if (y >= 0 && y < m1.height && oldY < pointY
				&& m1.cells[x][y].walls[2] == 1){
			pointX = oldX;
			pointY = oldY;
		}
		if(pointX != oldX || pointY != oldY){
			moveCounter++;
		}
		g2d.drawString("Moves: " + moveCounter.toString(), m1.width * cellSize
				+ offsetX + 20, 20);
		g2d.drawString("Move: Arrow Keys", m1.width * cellSize
				+ offsetX + 20, 40);
		if(y == m1.height - 1 && x == m1.width - 1){
			System.out.println("You won");
			g2d.drawString("you Won!", m1.width * cellSize + offsetX + 20, 100);
		}	
		//draw the player dot
		g.setColor(Color.green);
		g.fillRect(pointX - 2, pointY - 2, 4, 4);
		
	}
	/**
	 * This method stores the specified graphics into the JPanel and then
	 * uses said graphics to draw out the walls and the interface of said GUI
	 * Calls doDrawing in order to draw the walls/interface
	 * @param g accepts a Graphics object
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		doDrawing(g);
	}
	/**
	 * This method accepts an event, which is the key being pressed, and then the Key Listener handles the 
	 * event by turning the code of that specific key into something that Java can recognize and use
	 *  
	 * @param key this variable accepts the code of a certain key being pressed on the keyboard
	 */
	@SuppressWarnings("static-access")
	@Override
	public void keyPressed(KeyEvent key){
		
		oldX = pointX;
		oldY = pointY;
		
		if(key.getKeyCode() == key.VK_DOWN){
			pointY = pointY + cellSize;
			if(pointY > getBounds().height){
				pointY = getBounds().height;
			}
		}
		if(key.getKeyCode() == key.VK_UP){
			pointY = pointY - cellSize;
			if(pointY < 0){
				pointY = 0;
			}
		}
		if(key.getKeyCode() == key.VK_LEFT){
			pointX = pointX - cellSize;
			if(pointX < 0){
				pointX = 0;
			}
		}
		if(key.getKeyCode() == key.VK_RIGHT){
			pointX = pointX + cellSize;
			if(pointX > getBounds().width){
				pointX = getBounds().width;
			}
		}
		repaint();
	}
	/*We had to include these two methods because they were a part of the imported Key Listener interface
	  even though neither of the two contain any code.  Without these methods, upon running the code,
	  we would get errors
	  */
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
