package falstad;

import java.awt.Event;
import java.awt.event.KeyEvent;

import falstad.Constants.StateGUI;
import generation.Distance;

public class ManualDriver implements RobotDriver{
	
	private Distance distance ;	
	private int width ;
	private int height ;
	private int pathLength ;
    
	private Robot robot ;
	private MazeController mazeController ;
	
	static final int INITIAL_PATHLENGTH = 0 ;
	
	public ManualDriver() {
		pathLength = 0 ;
	}

	@Override
	public void setRobot(Robot r) {
		this.robot = r;
	}

	@Override
	public void setDimensions(int width, int height) {
		
		assert (width >= 0);
		assert (height >= 0);
		
		this.width = width;
		this.height = height;
	}

	@Override
	public void setDistance(Distance distance) {
		
		assert (null != distance) ; 
		this.distance = distance;
	}

	@Override
	public boolean drive2Exit() throws Exception {		
		return false; 
	}

	@Override
	public float getEnergyConsumption() {
		return BasicRobot.INITIAL_BATTERY_LEVEL - robot.getBatteryLevel();
	}

	@Override
	public int getPathLength() {
		return this.pathLength ;
	}
	
	/**
	 * Sets the given maze controller to this manual driver.
	 * @param controller
	 */
	public void setMazeController(MazeController controller) {
		this.mazeController = controller ; 
	}
	
	/** Is called by the simple key listener
	 * If the screen is at the title screen, sets the driver's path length to 0 
	 * and the robot's battery level to its initial level
	 * If the screen is inside the maze, the method takes in the 4 arrow key commands for movement
	 * and rotations.
	 * Any other key commands are sent to the maze controller.
	 * In any other state, the key commands are directly sent to the maze controller. 
	 * @param key, the command entered by the user
	 * @return true
	 */
	public boolean keyDown(int key) {

		/*switch (mazeController.getState() ) {
		case STATE_TITLE: {
			this.pathLength = 0 ;
			robot.setBatteryLevel(BasicRobot.INITIAL_BATTERY_LEVEL) ;
			mazeController.keyDown(key) ;
			break;
			}
		case STATE_PLAY : {		
			switch (key) { 
			case 107 : 		//up
				if (robot.distanceToObstacle(Robot.Direction.FORWARD ) > 0 ) {
				    this.pathLength ++ ;
				}
				robot.move(1, true);
				break;
			case 104 : 			//left
				robot.rotate(Robot.Turn.LEFT) ; 
				break;
			case 108 : 					//right
				robot.rotate(Robot.Turn.RIGHT) ;
				break;
			case 106 : 			//down				
				robot.rotate(Robot.Turn.AROUND);
				robot.move(1, true);
				break;
		default : {
			mazeController.keyDown(key) ;
		}
		//}
		//break;
		//}
		
		default : {
			mazeController.keyDown(key) ;
		}
		}*/
		
		switch (key) { 
		case 107 : 		//up
			if (robot.distanceToObstacle(Robot.Direction.FORWARD ) > 0 ) {
			    this.pathLength ++ ;
			}
			robot.move(1, true);
			break;
		case 104 : 			//left
			robot.rotate(Robot.Turn.LEFT) ; 
			break;
		case 108 : 					//right
			robot.rotate(Robot.Turn.RIGHT) ;
			break;
		case 106 : 			//down				
			robot.rotate(Robot.Turn.AROUND);
			robot.move(1, true);
			break;
		}
		System.out.println("Key: " + key);
		System.out.println("pathlength: " + pathLength);
		
		return true ;
	}

	public void setPathLength(int pathlength) {
		this.pathLength = pathlength; 
	}
	
}
