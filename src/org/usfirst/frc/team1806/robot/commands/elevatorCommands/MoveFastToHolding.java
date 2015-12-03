package org.usfirst.frc.team1806.robot.commands.elevatorCommands;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.States;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveFastToHolding extends Command {

    public MoveFastToHolding() {
        requires(Robot.elevatorSS);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.elevatorSS.moveUp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Robot.elevatorSS.getLiftEncoder() > Constants.holdingPosition);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.statesObj.liftPositionTracker = States.liftPosition.HOLDING_STATE;
    	Robot.elevatorSS.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.elevatorSS.zeroPower();
    }
}
