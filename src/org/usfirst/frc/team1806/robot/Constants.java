package org.usfirst.frc.team1806.robot;

public class Constants {

	//PID!
	//P: .02 is smooth ish
	public static double P = 0.015;
	//I: .001??
	public static double I = 0.003;
	public static double D = 0;
	//public static double D = 0.001;
	
	//controller constants
	public static double operatorLS_Y_Deadzone = .1;
	public static double triggerDeadzone = .05;
	
	
	//location constants
	public static double holdingPosition = 150;
	public static double secondStageHeight = 650;
	public static double secondStagePIDEngage = 525;
	public static double highCanHoldHeight = 2000;
	public static double stackingCanHoldHeight = 1300;
	public static double canReleaseHeight = 700;
	
	public static double acceptableHeightRange = 10;
	
	
	
}
