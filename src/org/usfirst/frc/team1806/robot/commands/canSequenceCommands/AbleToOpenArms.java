package org.usfirst.frc.team1806.robot.commands.canSequenceCommands;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.States.liftPosition;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AbleToOpenArms extends Command {

    public AbleToOpenArms() {
        requires(Robot.lift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Robot.lift.getLiftEncoder() < Constants.canReleaseHeight);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.lift.openArms();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
