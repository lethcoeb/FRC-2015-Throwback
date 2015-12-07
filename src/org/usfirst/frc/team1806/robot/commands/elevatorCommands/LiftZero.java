package org.usfirst.frc.team1806.robot.commands.elevatorCommands;

import org.usfirst.frc.team1806.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftZero extends Command {

	private boolean hasHit = false;
	
    public LiftZero() {
        requires(Robot.elevatorSS);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.elevatorSS.moveUp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.elevatorSS.getLiftEncoder() > 100){
    		hasHit = true;
    		Robot.elevatorSS.moveDown();
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return hasHit && Robot.elevatorSS.getBottomLimit();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elevatorSS.stop();
    	Robot.elevatorSS.openArms();
    	Robot.elevatorSS.retractArms();
    	Robot.elevatorSS.resetEncoder();

    	Robot.statesObj.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
