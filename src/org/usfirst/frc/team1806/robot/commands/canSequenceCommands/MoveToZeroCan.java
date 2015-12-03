package org.usfirst.frc.team1806.robot.commands.canSequenceCommands;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.States.liftPosition;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveToZeroCan extends Command {

	private boolean canReleased = false;
	
    public MoveToZeroCan() {
        requires(Robot.elevatorSS);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.elevatorSS.moveDown();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if(!canReleased){
    		if (Robot.elevatorSS.getLiftEncoder() < Constants.canReleaseHeight){
    			Robot.elevatorSS.openArms();
    			canReleased = true;
    		}
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.elevatorSS.getBottomLimit();
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
