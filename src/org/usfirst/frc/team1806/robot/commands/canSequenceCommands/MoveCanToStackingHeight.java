package org.usfirst.frc.team1806.robot.commands.canSequenceCommands;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.States;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveCanToStackingHeight extends Command {

	private boolean movingDown;
	
    public MoveCanToStackingHeight() {
        requires(Robot.elevatorSS);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//you need to move down
    	if(Robot.elevatorSS.getLiftEncoder() > Constants.stackingCanHoldHeight){
    		Robot.elevatorSS.moveDown();
    		movingDown = true;
    	}else{
    		Robot.elevatorSS.moveUp();
    		movingDown = false;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(movingDown){
        	return (Robot.elevatorSS.getLiftEncoder() < Constants.stackingCanHoldHeight);
        }else{
        	return (Robot.elevatorSS.getLiftEncoder() > Constants.stackingCanHoldHeight);
        }
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.statesObj.canSequenceStateTracker = States.canSequenceState.STACKHEIGHT;
    	Robot.elevatorSS.disable();
    	Robot.elevatorSS.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.elevatorSS.disable();
    }
}
