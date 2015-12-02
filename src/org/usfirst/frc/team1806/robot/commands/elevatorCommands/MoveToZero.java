package org.usfirst.frc.team1806.robot.commands.elevatorCommands;

import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.States;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveToZero extends Command {

	private boolean pidControl = false;
	
    public MoveToZero() {
    	requires(Robot.lift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.lift.brakeOff();
    	Robot.lift.moveDown();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!pidControl){
    		if(Robot.lift.getLiftEncoder() < 100){
    			pidControl = true;
    			Robot.lift.enable();
    			Robot.lift.setSetpoint(0);
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.lift.isWithinTightRange(0);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.lift.disable();
    	Robot.lift.stop();
    	Robot.statesObj.liftPositionTracker = States.liftPosition.ZEROED;
    	if(Robot.statesObj.robotModeTracker == States.robotMode.AUTOSTACK){
    		Robot.statesObj.totesHeld++;
    	}
    	System.out.println("Moved down successfully");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.lift.disable();
    }
}
