package org.usfirst.frc.team1806.robot;

public class States {
	
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
		AUTOSTACK, MANUAL
	}
	
	public enum elevatorCommand{
		WAITING, MOVETONEXT
	}
	
	public enum autoStackPosition{
		WAITING, INPROGRESS
	}
	//Occurrences local_Occurrences = Occurrences.ELEVATOR_STOP;
	private secondStageState secondStageStateTracker = secondStageState.SECOND_STAGE_RELEASED;
	private liftState liftStateTracker = liftState.LIFT_STOPPED;
	private clampState clampStateTracker = clampState.ARMS_OPEN;
	private extendState extendStateTracker = extendState.ARMS_RETRACTED;
	private liftPosition liftPositionTracker = liftPosition.ZEROED;
	private robotMode robotModeTracker = robotMode.AUTOSTACK;
	private elevatorCommand elevatorCommandTracker = elevatorCommand.WAITING;
	private autoStackPosition autoStackPositionTracker = autoStackPosition.WAITING;
	
	public States.secondStageState getSecondStageState(){
		return secondStageStateTracker;
	}
	
	public States.liftState getLiftState(){
		return liftStateTracker;
	}
	
	public States.clampState getClampState(){
		return clampStateTracker;
	}
	
	public States.extendState getExtendState(){
		return extendStateTracker;
	}
	
	public States.liftPosition getLiftPositionStr(){
		return liftPositionTracker;
	}
	
	public States.robotMode getRobotMode(){
		return robotModeTracker;
	}
	
	public States.elevatorCommand getElevatorCommand(){
		return elevatorCommandTracker;
	}
	
	public States.autoStackPosition getAutoStackPos(){
		return autoStackPositionTracker;
	}
	
	public void setSecondStageStateHolding(){
		secondStageStateTracker = secondStageState.SECOND_STAGE_HOLDING;
	}
	
	public void setSecondStageStateReleased(){
		secondStageStateTracker = secondStageState.SECOND_STAGE_RELEASED;
	}
	
	public void setLiftStateMovingUp(){
		liftStateTracker = liftState.LIFT_MOVING_UP;
	}
	
	public void setLiftStateMovingDown(){
		liftStateTracker = liftState.LIFT_MOVING_DOWN;
	}
	
	public void setLiftStateStopped(){
		liftStateTracker = liftState.LIFT_STOPPED;
	}
	
	public void setClampStateClamped(){
		clampStateTracker = clampState.ARMS_CLAMPED;
	}
	
	public void setClampStateOpen(){
		clampStateTracker = clampState.ARMS_OPEN;
	}
	
	public void setExtendStateExtended(){
		extendStateTracker = extendState.ARMS_EXTENDED;
	}
	
	public void setExtendStateRetracted(){
		extendStateTracker = extendState.ARMS_RETRACTED;
	}
	
	public void setLiftPositionHolding(){
		liftPositionTracker = liftPosition.HOLDING_STATE;
	}
	
	public void setLiftPositionZeroed(){
		liftPositionTracker = liftPosition.ZEROED;
	}
	
	public void setLiftPositionOther(){
		liftPositionTracker = liftPosition.OTHER;
	}
	
	public void setRobotModeAutoStack(){
		robotModeTracker = robotMode.AUTOSTACK;
	}
	
	public void setRobotModeManual(){
		robotModeTracker = robotMode.MANUAL;
	}
	
	public void setElevatorCommandMoveToNext(){
		elevatorCommandTracker = elevatorCommand.MOVETONEXT;
	}
	
	public void setElevatorCommandWaiting(){
		elevatorCommandTracker = elevatorCommand.WAITING;
	}
	
	public void setAutoStackPositionWaiting(){
		autoStackPositionTracker = autoStackPosition.WAITING;
	}
	
	public void setAutoStackPositionInProgress(){
		autoStackPositionTracker = autoStackPosition.INPROGRESS;
	}
	
	
}
