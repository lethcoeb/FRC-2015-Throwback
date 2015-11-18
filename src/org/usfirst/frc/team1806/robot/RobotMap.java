package org.usfirst.frc.team1806.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
   
	
	//controllers
	public static int driverController = 0;
	public static int operatorController = 1;
    
	//motors
	public static int rightDriveMotor = 1;
	public static int leftDriveMotor = 4;
	public static int elevatorMotor = 2;
	public static int rightIntakeMotor = 3;
	public static int leftIntakeMotor = 0;
	
	//pneumatics
	public static int extendOut = 0;
	public static int extendIn = 1;
	public static int secondStageHold = 2;
	public static int secondStageRelease = 3;
	public static int brakeLeggo = 4;
	public static int brakeHold = 5;
	public static int armsRelease = 6;
	public static int armsPinch = 7;
	
    //dio
	public static int photoSensor = 0;
	public static int topSensor = 1;
	public static int bottomSensor = 2;
	public static int liftEncA = 4;
	public static int liftEncB = 5;
	public static int rDriveEncA = 6;
	public static int rDriveEncB = 7;
	public static int lDriveEncA = 8;
	public static int lDriveEncB = 9;
	
}
