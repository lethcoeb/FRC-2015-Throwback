package org.usfirst.frc.team1806.robot;

public class States {
	
	public States(){
		reset();
	}
	
	public enum secondStageState{
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
		HOLDING_STATE, ZEROED, OTHER
	}
	
	public enum robotMode{
		AUTOSTACK, MANUAL, CANSEQUENCE
	}
	
	public enum elevatorCommand{
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
	}
	

}
