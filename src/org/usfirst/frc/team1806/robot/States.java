package org.usfirst.frc.team1806.robot;

public class States {
	
	private enum secondStageState{
		SECOND_STAGE_HOLDING, SECOND_STAGE_RELEASED
	}
	private enum liftState{
		LIFT_MOVING_UP, LIFT_MOVING_DOWN, LIFT_STOPPED
	}
	private enum clampState{
		ARMS_CLAMPED, ARMS_OPEN
	}
	private enum extendState{
		ARMS_EXTENDED, ARMS_RETRACTED
	}
	private enum liftPosition{
		HOLDING_STATE, ZEROED, OTHER
	}
	
	//Occurrences local_Occurrences = Occurrences.ELEVATOR_STOP;
	private secondStageState secondStageStateTracker = secondStageState.SECOND_STAGE_RELEASED;
	private liftState liftStateTracker = liftState.LIFT_STOPPED;
	private clampState clampStateTracker = clampState.ARMS_OPEN;
	private extendState extendStateTracker = extendState.ARMS_RETRACTED;
	private liftPosition liftPositionTracker = liftPosition.ZEROED;
	
	public String getSecondStageState(){
		return secondStageStateTracker.toString();
	}
	
	public String getLiftState(){
		return liftStateTracker.toString();
	}
	
	public String getClampState(){
		return clampStateTracker.toString();
	}
	
	public String getExtendState(){
		return extendStateTracker.toString();
	}
	
	public String getLiftPositionStr(){
		return liftPositionTracker.toString();
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
	
	
}
