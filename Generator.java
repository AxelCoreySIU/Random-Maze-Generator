import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * This class is responsible for generating the maze by breaking down walls of each individual cell
 * in order to create one path from the start to the end
 * 
 * @author Axel
 *
 */
public class Generator {
	int width;
	int height;
	Cell[][] cells;
	
	/**
	 * Constructor Method
	 */
	public Generator(){
		width = 25;
		height = 25;
		cells = new Cell[width][height];
		initializeCells();
		generateMaze();
	}
	
	/**
	 * Constructor Method that initializes the x and y size of the cell 
	 * within our GUI
	 * @param x must be greater than 0, but has no maximum limit. Determines width of the cell block
	 * @param y must be greater than 0, but has no maximum limit. Determines the height of the cell block
	 */
	public Generator(int x, int y){
		width = x;
		height = y;
		cells = new Cell[width][height];
		initializeCells();
		generateMaze();
	}
	/**
	 * This method creates a grid structure using the width and height specified by the user
	 * and puts all of the cells into a double array (matrix)
	 */
	private void initializeCells(){
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				cells[i][j] = new Cell();
				cells[i][j].x = i;
				cells[i][j].y = j;
				if(i == 0)
					//represents up
					cells[i][j].borders[0] = 1;
				if(j == 0)
					//represents left
					cells[i][j].borders[3] = 1;
			    if(i == width - 1)
			    	//represents down
					cells[i][j].borders[2] = 1;
				if(j == height - 1)
					//represents right
					cells[i][j].borders[1] = 1;
			}
		}
	}
	/**
	 * Breaks down the walls of the previously created cell grid. 
	 * The ArrayList is used to hold four or less neighboring cells at a time
	 * The Stack is used to store all of the cells with their set walls (not yet drawn)
	 */
	private void generateMaze(){
		
		Random rand = new Random();
		
		int x = rand.nextInt(width); //random starting location
		int y = rand.nextInt(height);
		
		//cellStack holds a list of cell locations
		Stack<Cell> cellStack = new Stack<Cell>();
		int totalCells = width * height;  //number of cells
		int visitedCells = 1;	//counter to ensure every cell is visited
		Cell currentCell = cells[x][y];
		
		//Used to store neighboring cells of a randomly chosen location
		ArrayList<Vertex> neighborCellList = new ArrayList<Vertex>();
		
		Vertex tmpV = new Vertex();
		
		while(visitedCells < totalCells){
			//gets rid of everything within the Array List before breaking down cell walls
			neighborCellList.clear();
			//if the vertex isn't cleared, it will result in a simpler maze
			tmpV = new Vertex();//this statement clears the Vertex variable
			//selects neighbor that is below the chosen cell
			if(y - 1 >= 0 && cells[x][y - 1].checkWalls() == true){
				tmpV.x1 = x;
				tmpV.y1 = y;
				tmpV.x2 = x;
				tmpV.y2 = y - 1;
				tmpV.wall1 = 0;
				tmpV.wall2 = 2;
				//stores above information in the array list
				neighborCellList.add(tmpV);
			}
			
			tmpV = new Vertex();
			//selects cell neighbor that is above the chosen cell
			if(y + 1 < height && cells[x][y + 1].checkWalls() == true){
				tmpV.x1 = x;
				tmpV.y1 = y;
				tmpV.x2 = x;
				tmpV.y2 = y + 1;
				tmpV.wall1 = 2; 
				tmpV.wall2 = 0;
				//stores above information in the array list
				neighborCellList.add(tmpV);
			}
			
			tmpV = new Vertex();
			//selects neighbor that is to the left of the chosen cell
			if(x - 1 >= 0 && cells[x - 1][y].checkWalls() == true){
				tmpV.x1 = x;
				tmpV.y1 = y;
				tmpV.x2 = x - 1;
				tmpV.y2 = y;
				tmpV.wall1 = 3;
				tmpV.wall2 = 1;
				//stores above information in the array list
				neighborCellList.add(tmpV);
			}
			tmpV = new Vertex();
			//selects neighbor that is to the right of the chosen cell
			if(x + 1 < width && cells[x + 1][y].checkWalls() == true){
				tmpV.x1 = x;
				tmpV.y1 = y;
				tmpV.x2 = x + 1;
				tmpV.y2 = y;
				tmpV.wall1 = 1;
				tmpV.wall2 = 3;
				//stores above information in the array list
				neighborCellList.add(tmpV);
			}
			//if an unvisited neighboring cell is found
			if(neighborCellList.size() >= 1){
				
				//randomly chooses a neighboring cell from the list
				int r1 = rand.nextInt(neighborCellList.size());
				tmpV = neighborCellList.get(r1);
				
				//use info saved in the array list to break down a shared wall between the origin and neighbor cells
				cells[tmpV.x1][tmpV.y1].walls[tmpV.wall1] = 0;
				cells[tmpV.x2][tmpV.y2].walls[tmpV.wall2] = 0;
				
				//push the current cell to the stack
				cellStack.push(currentCell);
				
				//makes the neighboring cell the current cell
				currentCell = cells[tmpV.x2][tmpV.y2];
				
				//update x and y coordinates to the neighboring x and y coordinates
				x = currentCell.x;
				y = currentCell.y;
				
				visitedCells++;
			}
			//else get the last cell from the stack and use that as the current cell
			else{
				//removes the previous updated cell from the stack
				currentCell = cellStack.pop();
				//updates the x and y coordinates to the neighboring x and y coordinates
				x = currentCell.x;
				y = currentCell.y;
			}
		}
		//starting cell; selects top left cell, breaks left wall to create entrance
		cells[0][0].walls[3] = 0;
		//exit cell; selects bottom right cell, breaks right wall to create exit
		cells[width - 1][height - 1].walls[1] = 0;
	}
}
