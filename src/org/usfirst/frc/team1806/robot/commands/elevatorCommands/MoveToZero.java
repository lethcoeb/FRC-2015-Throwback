package org.usfirst.frc.team1806.robot.commands.elevatorCommands;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.States;
import org.usfirst.frc.team1806.robot.States.liftPosition;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveToZero extends Command {

    public MoveToZero() {
    	requires(Robot.lift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.lift.enable();
    	Robot.lift.setSetpoint(0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.lift.isWithinRange(0);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.lift.disable();
    	Robot.lift.stop();
    	Robot.statesObj.liftPositionTracker = States.liftPosition.ZEROED;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.lift.disable();
    }
}
