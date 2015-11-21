package org.usfirst.frc.team1806.robot.commands.elevatorCommands;

import org.usfirst.frc.team1806.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveFastToY extends Command {

	private double m_target;
	
    public MoveFastToY(double target) {
        requires(Robot.lift);
        m_target = target;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.lift.moveUp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Robot.lift.getLiftEncoder() > m_target);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.lift.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.lift.zeroPower();
    }
}
