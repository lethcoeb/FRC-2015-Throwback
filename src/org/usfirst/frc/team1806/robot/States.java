package org.usfirst.frc.team1806.robot;

import org.usfirst.frc.team1806.robot.commands.canSequenceCommands.CanPickupSequence;

public class States {
	
	public States(){
		reset();
	}
	
	public enum secondStageState{
		//used for tracking second stage pos
		SECOND_STAGE_HOLDING, SECOND_STAGE_RELEASED
	}
	public enum liftState{
		LIFT_MOVING_UP, LIFT_MOVING_DOWN, LIFT_STOPPED
	}
	public enum clampState{
		ARMS_CLAMPED, ARMS_OPEN
	}
	public enum extendState{
		ARMS_EXTENDED, ARMS_RETRACTED
	}
	public enum liftPosition{
		//tracks lift position
		//zeroed is useful
		HOLDING_STATE, ZEROED, OTHER
	}
	
	public enum robotMode{
		//tracks mode that robot is in
		//used for driver/op controls
		AUTOSTACK, MANUAL, CANSEQUENCE, CANRIGHTINGSEQUENCE
	}
	
	public enum elevatorCommand{
		//used for waiting for operator's a button when autostacking
		WAITING, MOVETONEXT
	}
	
	public enum autoStackPosition{
		WAITING, INPROGRESS
	}
	
	public enum dataLogState{
		ON, OFF
	}
	
	public enum canSequenceState{
		WAITING, MOVETONEXT, STACKHEIGHT
	}
	
	public enum driverIntakeControl{
		DRIVER, AUTOMATIC
	}
	
	public enum canRightingMoveOn{
		MOVEON, WAITING
	}
	
	public int totesHeld;
	
	
	public secondStageState secondStageStateTracker;
	public liftState liftStateTracker;
	public clampState clampStateTracker;
	public extendState extendStateTracker;
	public liftPosition liftPositionTracker;
	public robotMode robotModeTracker;
	public elevatorCommand elevatorCommandTracker;
	public autoStackPosition autoStackPositionTracker;
	public dataLogState dataLogStateTracker;
	public canSequenceState canSequenceStateTracker;
	public driverIntakeControl driverIntakeControlTracker;
	public canRightingMoveOn canRightingMoveOnTracker;
	
	public void reset(){
		secondStageStateTracker = secondStageState.SECOND_STAGE_RELEASED;
		liftStateTracker = liftState.LIFT_STOPPED;
		clampStateTracker = clampState.ARMS_OPEN;
		extendStateTracker = extendState.ARMS_RETRACTED;
		liftPositionTracker = liftPosition.ZEROED;
		robotModeTracker = robotMode.AUTOSTACK;
		elevatorCommandTracker = elevatorCommand.WAITING;
		autoStackPositionTracker = autoStackPosition.WAITING;
		dataLogStateTracker = dataLogState.OFF;
		canSequenceStateTracker = canSequenceState.WAITING;
		totesHeld = 0;
		driverIntakeControlTracker = driverIntakeControl.DRIVER;
		canRightingMoveOnTracker = canRightingMoveOn.WAITING;
	}
	

}
