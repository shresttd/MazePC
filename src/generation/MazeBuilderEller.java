package generation;

//import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


/**
 * This class has the responsibility to create a maze of given dimensions (width, height) 
* together with a solution based on a distance matrix.
* The MazeBuilder implements Runnable such that it can be run a separate thread.
* The MazeFactory has a MazeBuilder and handles the thread management.   

* 
* The maze is built with Eller's algorithm. 
* This means sets in a matrix are merged horizontally and vertically at random, save for certain conditions.
* Pathways are generated by breaking walls between two cells when their sets are merged. 
* Further information under generatePathways() method. Helper methods are used for generatePathways() method. 
* @author Tejaswi Darshan Shrestha
*/
     
public class MazeBuilderEller extends MazeBuilder implements Runnable {
	         
    private int [][] grid;

	public MazeBuilderEller() {
		// TODO Auto-generated constructor stub
		super();
		System.out.println("MazeBuilderEller uses Eller's algorithm to generate maze.");
	} 
	

	public MazeBuilderEller(boolean det) {
		super(det);
		System.out.println("MazeBuilderEller uses Eller's algorithm to generate maze.");
	}

	/***
	 * This method generates pathways into the maze by using Eller's algorithm of randomly merging sets.
	 * A 2-dimensional array consisting of primitive integers is used as a mirror matrix for the Cells matrix.
	 * Each int value in the 2-dimensional array grid represents a set.
	 * In the beginning, every element in the grid is in its own unique set. 
	 * Sets are merged horizontally randomly, as long as they are not already in the same set. 
	 * For vertical merging, if a set is alone in a row, it is merged vertically. If the set has multiple values, at least one of the values
	 * is merged into the next set. Otherwise, sets are merged vertically at random.
	 * The random decision to merge or not merge is denoted by a boolean coinFlip() method, which returns true or false.
	 * A merging of sets is represented by the merged set's value being changed to the set it is merging to in the 2-D grid. 
	 * In the Cells, a merging of sets results in deleting a existing wall in the specified direction.
	 */
    @Override
    protected void generatePathways() {
    	grid = new int [width][height];
    	int x;
    	int y;
    	int value = 1;
    	
        for (y = 0; y < height; y ++) {
        	for (x = 0; x < width; x ++){
        		grid[x][y] = value;
        		value++;
        	}
        }
        
    	for (y = 0; y < height - 1; y ++) {
    		
        	for (x = 0; x < width - 1; x++) {

                mergeHorizontally(grid, x,y);
                
                int count = countValues(grid, x, y);
            	mergeVertically(grid, x,y, count);
        		
        	}
        	
        	x = width - 1;
        	int count = countValues(grid,x,y);
        	mergeVertically(grid, x, y, count);
        	
    	}
    	
    	y = height - 1;
    	
    	for (x = 0; x < width - 1; x ++) {

    		mergeLastRow(grid, x, y);
    	}

    }
    
    /** 
     * Performs a horizontal merger of sets, both in the 2-D mirror grid, as well as the actual Maze Cell. 
     * A merger in the 2-D mirror is done by changing the value of the cell to the set it is being merged to.
     * A merger in the Cells matrix is done by deleting the wall in the specified direction.
     * Cells are merged at random if two cells are not in the same set already.
     * After two sets are merged, all the values in the set in the 2-D mirror grid are changed to the set as well. 
     * @param grid
     * @param x
     * @param y
     */
	protected void mergeHorizontally(int [][] grid, int x, int y){
		
		if (grid[x+1][y] != grid[x][y]){
			 
			boolean coin = flipCoin();
			if (coin) {
		
				int temp = grid[x+1][y];
				int curVal = grid[x][y];
								
				for (int col = 0; col < width; col ++) {
					if (grid[col][y] == temp) {
						grid[col][y] = curVal;				
					}
				}
				
				Wall eastWall = new Wall(x, y, CardinalDirection.East);
				cells.deleteWall(eastWall);
			}
		}
	}
	
    /** 
     * Horizontally merges all the sets in the last row into one single set. 
     * Performs a horizontal merger of sets, both in the 2-D mirror grid, as well as the actual Maze Cell. 
     * A merger in the 2-D mirror is done by changing the value of the cell to the set it is being merged to.
     * A merger in the Cells matrix is done by deleting the wall in the specified direction.
     * @param grid
     * @param x
     * @param y
     */
	protected void mergeLastRow (int [][] grid, int x, int y) {
		if (grid[x+1][y] != grid[x][y]){
			
			int temp = grid[x+1][y];
			int curVal = grid[x][y];
							
			for (int col = 0; col < width; col ++) {
				if (grid[col][y] == temp) {
					grid[col][y] = curVal;				
				}
			}
			
			Wall eastWall = new Wall(x, y, CardinalDirection.East);
			cells.deleteWall(eastWall);
			
		}
	}
	
    /** 
     * Performs a vertical merger of sets, both in the 2-D mirror grid, as well as the actual Maze Cell. 
     * A merger in the 2-D mirror is done by changing the value of the cell to the set it is being merged to.
     * A merger in the Cells matrix is done by deleting the wall in the specified direction.
     * After two sets are merged, all the values in the set in the 2-D mirror grid are changed to the set as well. 
     * If a set only contains one element, that set is merged into the next row.
     * Otherwise, at least one element from each set is merged into the next row. This element is chosen randomly out of all
     * the possible elements in that set. 
     * The rest of the elements in a multi-element set are merged at random.
     * @param grid
     * @param x
     * @param y
     */
	protected void mergeVertically(int [][] grid, int x, int y, int count ){
		
		if (count == 1) {
			grid[x][y+1] = grid[x][y];
			Wall southWall = new Wall(x, y, CardinalDirection.South);
			cells.deleteWall(southWall);
		}
		
		else {
			
			int numberOfVerticalMerges = 0;
			for (int check = 0; check < width; check ++) {
				if (grid[check][y] == grid[x][y]) {
					if (grid[check][y+1] == grid[check][y]) {
						numberOfVerticalMerges++;
					}
				}
			}
			
			if (numberOfVerticalMerges == 0) {
		    	ArrayList<Integer> newList = new ArrayList<Integer>();
		    	for (int col = 0; col < width ; col++) {
		    		if (grid[col][y] == grid[x][y]) {
			    	    newList.add(col);
			    	}
		    	}
			
				Random random = new Random();
				int randomIndex = random.nextInt(newList.size());
				
				int merge = newList.get(randomIndex);
				
				grid[merge][y + 1] = grid[merge][y];
				Wall mergeSouthWall = new Wall(merge, y, CardinalDirection.South);
				cells.deleteWall(mergeSouthWall);
			}
			else { 
				boolean coin = flipCoin();
				
				if (coin) {
				    grid[x][y+1] = grid[x][y];
				    Wall southWall = new Wall(x, y, CardinalDirection.South);
				    cells.deleteWall(southWall);
				}
			}
		}
	}	
	
	/**
	 * Counts the number of times a value appears in the 2-D mirror grid. 
	 * This represents the number of elements a set contains.
	 * Used to see if there are any sets that contain only one element - in which case this set must be
	 * merged vertically to the next row. 
	 * Used in generatePathways() and mergeVertically().
	 * @param grid
	 * @param x
	 * @param y
	 * @return
	 */
	private int countValues(int grid [][], int x, int y) {
		int curVal = grid[x][y];
		int count = 0;
		
		for (int col = 0; col < width; col ++) {
			if (grid[col][y] == curVal) {
				count ++;
			}
		}	
		return count;
	}
	
	/** 
	 * Returns a random boolean, either true or false.
	 * Used to "flip a coin" to see whether to merge a set horizontally/vertically or not at random; only used 
	 * if they are not already required to be merged.
	 * Used in mergeHorizontally() and mergeVertically().
	 * @return
	 */
	private boolean flipCoin(){
		
		Random random = new Random();
								
	    boolean coin = random.nextBoolean();
	   
	    return coin;
	}    
	
	/** 
	 * Helper method to get grid for test cases 
	 * @return
	 */
	protected int [][] getGrid() {
		return this.grid;
	}
	

}
