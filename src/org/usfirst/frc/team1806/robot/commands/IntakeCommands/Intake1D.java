package org.usfirst.frc.team1806.robot.commands.IntakeCommands;

import org.usfirst.frc.team1806.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Intake1D extends Command {

	private boolean m_runLeft;
	
    public Intake1D(boolean runLeft) {
    	requires(Robot.intakeSS);
        m_runLeft = runLeft;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.intakeSS.run1D(m_runLeft);
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
    	Robot.intakeSS.stop();
    }
}
