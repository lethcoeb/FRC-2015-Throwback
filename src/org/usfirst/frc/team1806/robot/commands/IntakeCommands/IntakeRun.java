package org.usfirst.frc.team1806.robot.commands.IntakeCommands;

import org.usfirst.frc.team1806.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeRun extends Command {

	private double m_speed;
	
    public IntakeRun(double speed) {
        requires(Robot.in);
        m_speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.in.runAtSpeed(m_speed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
