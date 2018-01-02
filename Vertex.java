/**
 * This class is used as an object class to temporarily store information about the 
 * cells and their walls
 * 
 * @author Axel
 */
public class Vertex {
	
	//Used to record the original coordinate of a cell
	int x1;
	int y1;
	
	//Used to record the neighboring coordinate of a cell
	int x2;
	int y2;
	
	//walls that are shared by the original cell and its neighboring cell
	int wall1;
	int wall2;
}
