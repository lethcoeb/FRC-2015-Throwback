package org.usfirst.frc.team1806.robot.commands.elevatorCommands;

import org.usfirst.frc.team1806.robot.Robot;

import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveToTarget extends Command {

    public MoveToTarget() {
    	requires(Robot.lift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.lift.enable();
    	Robot.lift.setSetpoint(500.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("pid cmd running");
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//return false;
        return (Math.abs(Robot.lift.getSetpoint() - Robot.lift.getLiftEncoder()) < 5);
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("pid command finished");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
