package org.usfirst.frc.team1806.robot.commands.elevatorCommands;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveToSecondStage extends Command {

	private boolean pidControl = false;
	
    public MoveToSecondStage() {
        requires(Robot.elevatorSS);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	Robot.elevatorSS.moveUp();
	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	//engage PID when you're close
    	
    	
    	    if(Robot.elevatorSS.getLiftEncoder() > Constants.secondStagePIDEngage && !pidControl){
    	    	if(Robot.statesObj.totesHeld == 0){
    	    		Robot.elevatorSS.enable();
    	    		Robot.elevatorSS.setSetpoint(Constants.secondStageHeight);
    	    	}else{
    	    	
    	    		Robot.elevatorSS.enable();
    	    		Robot.elevatorSS.setSetpoint(Constants.secondStageHeight);
    	    		pidControl = true;
    	    		
    	    	}
    	    		
    	    	
            
    	}	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
        return Robot.elevatorSS.isWithinRange(Constants.secondStageHeight);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elevatorSS.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.elevatorSS.disable();
    }
}
