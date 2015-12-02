package org.usfirst.frc.team1806.robot.commands.DrivetrainCommands;

import org.usfirst.frc.team1806.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Turn90 extends Command {

	private double m_targetAngle;
	
    public Turn90(double targetAngle) {
        requires(Robot.drivetrain);
        m_targetAngle = targetAngle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.setAngle(m_targetAngle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
