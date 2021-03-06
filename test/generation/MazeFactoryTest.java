package generation;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * This class tests MazeBuilder through MazeFactory. 
 * Can be used for many different MazeBuilder algorithms.
 * Uses stub of Order, class called OrderStub.
 */
public class MazeFactoryTest {
  	 		
	OrderStub stub;
	Factory testFactory;
    MazeConfiguration mazeConfig;

	@Before
	public void setUp() throws Exception { 
		stub = new OrderStub();
		
		testFactory = new MazeFactory(true);
		testFactory.order(stub);

		testFactory.waitTillDelivered();
		 
		mazeConfig = stub.getMazeConfiguration();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * Checks if the exit is reachable from a certain starting position.
	 */
	@Test
	public void testHasExit() {
		
		int INFINITY = Integer.MAX_VALUE;
		
		mazeConfig.setStartingPosition(0, 1);
		
		assertTrue(INFINITY > mazeConfig.getMazedists().getDistance(0, 1));
	}
	
	/**
	 * Checks to see if the exit is reachable from every spot in the maze.
	 * Should be true.
	 */
	@Test
    public void testExitFromEverySpot(){
		
		int INFINITY = Integer.MAX_VALUE;
	
		int row;
		int column; 
		
		for ( row = 0; row < mazeConfig.getWidth() - 1; row++) {
			for ( column = 0; column < mazeConfig.getHeight() - 1; column++ ) {
		        assertTrue(INFINITY > mazeConfig.getMazedists().getDistance(row, column));
			}
		}
	}
	
	/**
	 * Checks to make sure the width of the maze is correct.
	 */
	@Test
	public void testWidth(){
		
		mazeConfig.setWidth(10);
		
		assertTrue(10 == mazeConfig.getWidth());
		
		mazeConfig.setWidth(99);
		assertTrue(99 == mazeConfig.getWidth());
	}
	
	/** 
	 * Checks to see that the height of the maze is correct.
	 */
	
	@Test
	public void testHeight(){
		
		mazeConfig.setHeight(10);
		assertTrue(10 == mazeConfig.getHeight());
		
		mazeConfig.setHeight(99);
		assertTrue(99 == mazeConfig.getHeight());
	}

	
	/**
	 * Checks to see that the entrance and exit positions of the maze are the farthest points in the maze. 
	 * The distance between the entrance and the exit positions are the maximum distance possible. 
	 * Shoulder be the farthest distance.
	 */
	@Test
	public void testEntryExitMaxDistance(){
		
		int[] startPosition = mazeConfig.getMazedists().getStartPosition();
		
		assertTrue (mazeConfig.getMazedists().getMaxDistance() == mazeConfig.getMazedists().getDistance(startPosition[0], startPosition[1]));
	}
	
	/** 
	 * Checks to see if every position in the maze is a valid position.
	 * Every position should be valid
	 */
	@Test
	public void testValidPosition(){
		
		int row;
		int col;
				
		for (row = 0; row < mazeConfig.getWidth() - 1; row++) {
			for (col = 0; col < mazeConfig.getHeight() - 1; col++){
				assertTrue(mazeConfig.isValidPosition(row, col));
			}
		}		
	}
	
	/**
	 * Tests to see if the starting position is correct.
	 * Should be correct.
	 */
	/*@Test
	public void testCorrectStartPosition(){
		
		mazeConfig.setStartingPosition(9, 8);
		
		assertTrue(9 == mazeConfig.getStartingPosition()[0] && 8 == mazeConfig.getStartingPosition()[1]);
	}*/

	
	/** 
	 * Tests to see that the maze has an opening - that the exit position of the maze is exactly one move away from exiting the maze.
	 * Maze should have an opening. 
	 */
	@Test
	public void testExitHasOpening() {
 		
 		int [] exitPosition = mazeConfig.getMazedists().getExitPosition();
 		 		
 		assertTrue(1 == mazeConfig.getDistanceToExit(exitPosition[0], exitPosition[1]) );
	}
}
