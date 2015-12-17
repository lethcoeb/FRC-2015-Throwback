package org.usfirst.frc.team1806.robot.commands.canrighting;

import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.States;
import org.usfirst.frc.team1806.robot.States.canRightingMoveOn;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClampAndDown extends Command {

    public ClampAndDown() {
        requires(Robot.elevatorSS);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.statesObj.canRightingMoveOnTracker == States.canRightingMoveOn.MOVEON){
    		Robot.elevatorSS.moveDownMedium();
    		Robot.elevatorSS.closeArms();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.elevatorSS.getBottomLimit();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elevatorSS.stop();
    	Robot.statesObj.canRightingMoveOnTracker = States.canRightingMoveOn.WAITING;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
