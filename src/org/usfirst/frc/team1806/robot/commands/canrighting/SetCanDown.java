package org.usfirst.frc.team1806.robot.commands.canrighting;

import org.usfirst.frc.team1806.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetCanDown extends Command {

	private boolean hasReleased = false;
	
    public SetCanDown() {
        requires(Robot.elevatorSS);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.elevatorSS.moveDown();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!hasReleased && Robot.elevatorSS.getLiftEncoder() < 400){
    		Robot.elevatorSS.openArms();
    		Robot.elevatorSS.retractArms();
    		hasReleased = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.elevatorSS.getLiftEncoder() < 300;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
