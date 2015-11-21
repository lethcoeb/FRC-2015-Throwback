package org.usfirst.frc.team1806.robot.commands.elevatorCommands;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveToSecondStage extends Command {

    public MoveToSecondStage() {
        requires(Robot.lift);
            
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	Robot.lift.moveUp();
	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	//engage PID when you're close
    	
    	//maybe make this a separate command so you don't keep enabling the pid controller
    	if(Robot.lift.getLiftEncoder() > Constants.secondStagePIDEngage){
        	Robot.lift.enable();
            Robot.lift.setSetpoint(Constants.secondStageHeight);
            
    	}	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.lift.isWithinRange(Constants.secondStageHeight);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.lift.disable();
    }
}
