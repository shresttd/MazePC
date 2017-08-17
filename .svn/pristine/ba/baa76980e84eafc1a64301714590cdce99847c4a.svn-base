package falstad;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import falstad.Constants.StateGUI;
import falstad.Robot.Turn;
import generation.CardinalDirection;
import generation.Cells;
import generation.Distance;
import generation.Factory;
import generation.MazeBuilder;
import generation.MazeConfiguration;
import generation.MazeContainer;
import generation.MazeFactory;
import generation.Order.Builder;
import generation.OrderStub;
import generation.Order; 

public class BasicRobotTest {

	OrderStub stub ;
    Robot robot ; 
	MazeController controller ;
	StateGUI state ;
	MazeFactory factory; 
		
	static final float INITIAL_BATTERY_LEVEL = 2500 ;
	static final float DISTANCE_SENSING = 1 ;
	static final float ROTATE_90 = 3 ;
	static final float MOVE_FORWARD = 5 ;

	/**
	 * Setups up before each test
	 * A fake controller is used to bypass the unnecessary graphical aspects 
	 * of MazeController.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		controller = new FakeController() ;
		factory = new MazeFactory(true);
		factory.order(controller) ;
		factory.waitTillDelivered();
		
		robot = controller.getRobot() ;
		robot.setMaze(controller) ;
		assertNotNull(robot);
		
		assertNotNull(controller.getMazeConfiguration()) ;		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * Tests whether the basic robot has a room sensor or not.
	 * Should be true.
	 */
	@Test
	public void testHasRoomSensor() {
		assertTrue( robot.hasRoomSensor() ) ;
	}
	
	/**
	 * Tests whether the robot has a distance sensor.
	 * Should be true.
	 */
	@Test
	public void testHasDistanceSensor() {	
		for ( Robot.Direction dir : Robot.Direction.values() ) {
			assertTrue( robot.hasDistanceSensor(dir) ) ;
		}
	}
	
	/**
	 * Tests the amount of energy the robot takes to make a full rotation.
	 * Should return amount of energy necessary for four 90 degree rotations.
	 */
	@Test
	public void testGetEnergyForFullRotation() {
		assertTrue( robot.getEnergyForFullRotation() == ROTATE_90 * 4 );
	}
	
	/**
	 * Tests amount of energy the robot requires to take a step forward.
	 * Should return the amount of energy needed to move forward, 5.
	 */
	@Test
	public void testGetEnergyForStepForward () {
		assertTrue (robot.getEnergyForStepForward() == MOVE_FORWARD ) ;
	} 
	
	/**
	 * Tests the initial battery level.
	 * Should return the initial battery level, 2500. 
	 */
	@Test
	public void testBatteryLevelAtBeginning() {
		assertTrue ( robot.getBatteryLevel() == INITIAL_BATTERY_LEVEL ) ;
	}
	
	@Test
	public void testNoBattery() {
		int level = 0 ;
		robot.setBatteryLevel(level);
		
		assertTrue ( robot.hasStopped() );
	}
	
	/**
	 * Tests battery level after no moves
	 * Should return the initial battery level.
	 */
	@Test
	public void testBatteryLevelAfterNoMoves() {
		robot.move(0, false);
		assertTrue ( robot.getBatteryLevel() == INITIAL_BATTERY_LEVEL ) ;
	}
	
	/**
	 * Tests battery level after left rotation
	 * Should return the initial battery minus the energy needed for a rotation
	 */
	@Test
	public void testBatteryLevelAfterLeftRotation() {
		Turn turn = Robot.Turn.LEFT ;
		
		robot.rotate(turn);
		assertTrue (robot.getBatteryLevel() == INITIAL_BATTERY_LEVEL - ROTATE_90 ) ;		
	}
	
	/**
	 * Tests battery level after right rotation
	 * Should return the initial battery minus the energy needed for a rotation
	 */
	@Test 
	public void testBatteryLevelAfterRightRotation() {
		robot.rotate(Robot.Turn.RIGHT);
		assertTrue (robot.getBatteryLevel() == INITIAL_BATTERY_LEVEL - ROTATE_90 ) ;
	}
	
	/**
	 * Tests battery level after around rotation
	 * Should return the initial battery minus the energy needed for two 90 degree rotations
	 */
	@Test
	public void testBatteryLevelAfterAroundRotation() {
		robot.rotate(Robot.Turn.AROUND);
		assertTrue (robot.getBatteryLevel() == INITIAL_BATTERY_LEVEL - ROTATE_90 * 2 ) ;
	}
	
	/**
	 * Tests if can see exit from starting position.
	 * Which should return false.
	 * Then tests if can see exit from exit position.
	 * Which should return true.
	 */
	@Test
	public void testCanSeeExit() {
		int[] startPos = controller.getMazeConfiguration().getStartingPosition() ;
		controller.setCurrentPosition(startPos[0], startPos[1]);
		for ( Robot.Direction dir : Robot.Direction.values() ) {
		    if ( robot.distanceToObstacle(dir) == Integer.MAX_VALUE ) {
		        assertFalse (robot.canSeeExit(dir) ) ;
		    }
		}
		
		int exitPos[] = controller.getMazeConfiguration().getMazedists().getExitPosition();
		controller.setCurrentPosition(exitPos[0], exitPos[1]);
		for ( Robot.Direction dir : Robot.Direction.values() ) {
		    if ( robot.distanceToObstacle(dir) == Integer.MAX_VALUE ) {
		        assertTrue (robot.canSeeExit(dir) ) ;
		    }
		}
	}
	
	/**
	 * Tests if can set battery level of the robot to desired level
	 * First sets to a level that is 0 or greater than 0.
	 * Which should return to the desired level.
	 * Then sets to a level that is less than 0.
	 * Which should return to 0.
	 */
	@Test
	public void testSetBatteryLevel() {				//can also test when battery level is below 0, and when 
		int level = 100 ;							//battery level is above 2500
		robot.setBatteryLevel(level);
		for (level = 0; level <= INITIAL_BATTERY_LEVEL ; level ++) {
			robot.setBatteryLevel(level);
			assertTrue ( robot.getBatteryLevel() == level ) ;
		}
	}
	
	/**
	 * Tests if the initial current direction is correct.
	 * Should be East
	 */
	@Test
	public void testCurrentDirectionAtBegginning() {		
		assertTrue (robot.getCurrentDirection() == CardinalDirection.East ) ;
	}
	
	/**
	 * Tests if the current direction is correct after left rotation.
	 * Should be South
	 */
	@Test
	public void testRotateLeft() {
		robot.rotate(Robot.Turn.LEFT);
		assertTrue (robot.getCurrentDirection() == CardinalDirection.South) ;
	}
	
	/**
	 * Tests if the current direction is correct after right rotation.
	 * Should be North
	 */
	@Test
	public void testRotateRight() {
		robot.rotate(Robot.Turn.RIGHT);
		assertTrue (robot.getCurrentDirection() == CardinalDirection.North) ;
	}
	
	/**
	 * Tests if the current direction is correct after around rotation.
	 * Should be West
	 */
	@Test
	public void testRotateAroundLeft() {
		robot.rotate(Robot.Turn.AROUND);
		assertTrue (robot.getCurrentDirection() == CardinalDirection.West) ;
	}
	
	/**
	 * Tests if room sensor correctly says if robot is inside a room or not
	 * Should return false because robot is not in a room in this case
	 */
	@Test
	public void testIsInsideRoomFalse() {
		int curPos [] = controller.getCurrentPosition() ;
		if (!controller.getMazeConfiguration().getMazecells().isInRoom(curPos[0], curPos[1]) ) {
			assertFalse (robot.isInsideRoom()) ;
		}
	}
	
	/**
	 * Tests if room sensor says if robot is inside a room or not
	 * Should return true because robot is inside a room in this case.
	 * @throws Exception
	 */
/*	@Test
	public void testIsInsideRoomTrue() throws Exception {
		int [] inRoom = new int [2] ;
		int x;
		int y;
		for (x = 0; x < controller.getMazeConfiguration().getWidth() ; x ++) {
			for (y = 0; y < controller.getMazeConfiguration().getHeight() ; y++ ) {
				if (controller.getMazeConfiguration().getMazecells().isInRoom(x, y) ) {
					inRoom[0] = x ;
					inRoom[1] = y ; 
				}
			}
		}
		controller.setCurrentPosition(inRoom[0], inRoom[1] ) ;
		assertTrue (robot.isInsideRoom());
	}*/
	
	/**
	 * Tests if robot correctly know if it is at the exit.
	 * Returns true because robot is at the exit
	 */
	@Test
	public void testIsAtExit() {
		int[] exitPos = controller.getMazeConfiguration().getMazedists().getExitPosition() ;
		controller.setCurrentPosition(exitPos[0], exitPos[1]);
		assertTrue(robot.isAtExit());
	}
	
	/**
	 * Tests if robot's current position is correct.
	 * Asserts true because it should be correct.
	 * @throws Exception
	 */
	@Test
	public void testGetCurrentPosition() throws Exception {
		int[] curPos = controller.getCurrentPosition() ;
		assertEquals(curPos[0], robot.getCurrentPosition()[0]) ;
		assertEquals(curPos[1], robot.getCurrentPosition()[1]) ;
	}
	
	/**
	 * Tests if robot moves when its battery level is 0.
	 * Robot should remain the original position.
	 * @throws Exception
	 */
	@Test
	public void testMoveWithNoBattery() throws Exception {
		robot.setBatteryLevel(0);
		controller.setCurrentPosition(0, 0);
		
		robot.move(1, true);
		
		assertEquals(0, robot.getCurrentPosition()[0]) ;
		assertEquals(0, robot.getCurrentPosition()[1]) ;		
	}
	
	/**
	 * Tests if robot rotates when its battery level is 0.
	 * Robot should remain the original position.
	 * @throws Exception
	 */
	@Test
	public void testRotateWithNoBattery() {
		robot.setBatteryLevel(0);
		
		CardinalDirection dir = robot.getCurrentDirection() ;
		
		for (Robot.Turn turn : Robot.Turn.values()) {
			robot.rotate(turn);
		}
		
		assertEquals(dir, robot.getCurrentDirection() ) ;
		
	}
}
