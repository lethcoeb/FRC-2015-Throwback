package org.usfirst.frc.team1806.robot.commands.elevatorCommands;

import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveToTarget extends Command {

	private double m_targetPos;
    public MoveToTarget(double targetPos) {
    	requires(Robot.lift);
    	m_targetPos = targetPos;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.lift.enable();
    	Robot.lift.setSetpoint(m_targetPos);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.lift.isWithinRange(m_targetPos);
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("pid movement to " + m_targetPos + " finished");
    	Robot.lift.disable();
    	Robot.lift.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.lift.disable();
    }
}
