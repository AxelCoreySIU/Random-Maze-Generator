
public class Cell {
	//using byte makes it easier to save space in large matrices
		//used to get information about the walls around a cell
		byte[] walls = {1,1,1,1};
		//used in generator to set the walls around a cell to create a grid
		byte[] borders = {0,0,0,0};

		Integer x;
		Integer y;
		
		/**
		 * This method determines if a cell is surrounded by walls or not
		 * @return true if a cell is completely surrounded by walls, if not returns false
		 */
		public boolean checkWalls(){
			if(walls[0] == 1 && walls[1] == 1 & walls[2] == 1 && walls[3] == 1){
				return true;
			}
			else{
				return false;
			}
		}
}
