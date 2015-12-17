package org.usfirst.frc.team1806.robot;

public class Constants {

	/*
	//!!!! P = .015, I = .003, D = 0 works pretty well
	
	//PID!
		public static double elevatorP = 0.015;
		public static double elevatorI = 0.0003;
		public static double elevatorD = 0;
	
		public static double drivetrainDriveP = 1;
		public static double drivetrainDriveI = 0;
		public static double drivetrainDriveD = 0;
	//in inches
		public static double drivetrainDistanceTolerance = 1;
		public static double drivetrainRotateP = .05;
		public static double drivetrainRotateI = .01;
		public static double drivetrainRotateD = 0;
	//in degrees
		public static double drivetrainAngleTolerance = 2.0;
	
	//controller constants
		public static double driveStickDeadzone = .02;
		public static double operatorLS_Y_Deadzone = .1;
		public static double triggerDeadzone = .05;
	
	
	//location constants
		//elevator
			public static double holdingPosition = 150;
			public static double secondStageHeight = 660;
			public static double secondStagePIDEngage = 525;
			public static double highCanHoldHeight = 2000;
			public static double stackingCanHoldHeight = 1300;
			public static double canReleaseHeight = 700;
		
		//auto movement
	
	//check for if elevator is in desired range, +- the value.
			public static double acceptableTightHeightRange = 2;
		public static double acceptableHeightRange = 7;*/
		
		
		
	//!!!! P = .015, I = .003, D = 0 works pretty well
	
		//PID!
			public static double elevatorP = 0.015;
			public static double elevatorI = 0.0003;
			public static double elevatorD = 0;
		
			/*public static double drivetrainDriveP = 1;
			public static double drivetrainDriveI = .0175;
			public static double drivetrainDriveD = .14;*/
			
			public static double drivetrainDriveP = .403;
			public static double drivetrainDriveI = .1;
			//1.8 is okay for D i think
			public static double drivetrainDriveD = 1.75;
			
			
			public static double drivetrainDrivePSlow = .4;
			public static double drivetrainDriveISlow = 0;
			public static double drivetrainDriveDSlow = 1.8;
			
			
		//in inches
			public static double drivetrainDistanceTolerance = .2;
			
			
			/*public static double drivetrainRotateP = .05;
			public static double drivetrainRotateI = .01;
			public static double drivetrainRotateD = 0;*/
			
			public static double drivetrainRotateP = .035;
			public static double drivetrainRotateI = .0;
			public static double drivetrainRotateD = .07;
			
			
		//in degrees
			public static double drivetrainAngleTolerance = 1.0;
		
		//controller constants
			public static double driveStickDeadzone = .02;
			public static double operatorLS_Y_Deadzone = .1;
			public static double triggerDeadzone = .05;
		
		
		//location constants
			//elevator
				public static double holdingPosition = 150;
				public static double secondStageHeight = 660;
				public static double secondStagePIDEngage = 525;
				public static double highCanHoldHeight = 2000;
				public static double stackingCanHoldHeight = 1300;
				public static double canReleaseHeight = 700;
				public static double canRightingHighHeight = 800;
				public static double canRightingLowHeight = 100;
			
			//auto movement
		
		//check for if elevator is in desired range, +- the value.
				public static double acceptableTightHeightRange = 2;
			public static double acceptableHeightRange = 5;
	
	
}
