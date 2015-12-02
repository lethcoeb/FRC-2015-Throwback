package org.usfirst.frc.team1806.robot;

public class Constants {

	//!!!! P = .015, I = .003, D = 0 works pretty well
	
	//PID!
	public static double elevatorP = 0.015;
	public static double elevatorI = 0.0003;
	public static double elevatorD = 0;
	
	public static double drivetrainP = 1;
	public static double drivetrainI = 0;
	public static double drivetrainD = 0;
	public static double drivetrainAngleTolerance = 2.0;
	
	//controller constants
	public static double operatorLS_Y_Deadzone = .1;
	public static double triggerDeadzone = .05;
	
	
	//location constants
	public static double holdingPosition = 150;
	public static double secondStageHeight = 660;
	public static double secondStagePIDEngage = 525;
	public static double highCanHoldHeight = 2000;
	public static double stackingCanHoldHeight = 1300;
	public static double canReleaseHeight = 700;
	
	//check for if elevator is in desired range, +- the value.
	public static double acceptableTightHeightRange = 2;
	public static double acceptableHeightRange = 7;
	
	
	
}
