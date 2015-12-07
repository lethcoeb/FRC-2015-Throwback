package org.usfirst.frc.team1806.robot.commands.canrighting;

import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.States;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WaitForDriverInput extends Command {

    public WaitForDriverInput() {
        requires(Robot.elevatorSS);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.statesObj.canRightingMoveOnTracker == States.canRightingMoveOn.MOVEON;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
