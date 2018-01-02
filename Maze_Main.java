import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Maze_Main  extends JFrame {
		//good presets: 4, 4, 20
		//good presets: 50, 50, 20
		//good presets: 200, 800, 2
	
		//the x size of the matrix
		int width = 10;
		//the y size of the matrix
		int height = 10;
		//the size of each cell
		int cellSize = 40;
		Generator maze1 = new Generator(width, height);
		
		//Constructor Method
		public Maze_Main(){
			initUI();
		}
		/**
		 * This is the main method that allows the program to run.
		 * run() creates a new maze object and makes the maze GUI visible.
		 */
		public static void main(String[] args){
			/*Runnable allows the order of operations to be followed when
			 executing commands within the GUI, such as using the keys to
			 move the cursor through the maze.
			 */
			SwingUtilities.invokeLater(new Runnable() {
				//Sets the visibility of the window to visible
				@Override
				public void run(){
					Maze_Main maze = new Maze_Main();
					maze.setVisible(true);
				}
			});
		}
		/**
		 * This method initializes the GUI
		 */
		private void initUI(){
			/*sets the title of the window
			 * @param accepts a empty or nonempty string*/
			setTitle("Maze Game");
			
			/*exits application
			 * @param decides what operation is performed*/
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			/*Sets the size of the window
			 * @param accepts an integer that determines width of the window
			 * @param accepts an integer that determines height of the window*/
			setSize(width * cellSize + 200, height * cellSize + 75);
			
			MazeGui mg = new MazeGui(maze1, cellSize);
			
			add(mg);
			//receives key events
			addKeyListener(mg);
			//sets the window in the center of the screen
			setLocationRelativeTo(null);
		}
}
