package org.usfirst.frc.team1806.robot.commands;

import org.usfirst.frc.team1806.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveUpToY extends Command {

	private int targetPos;
	private boolean quit = false;
	
    public MoveUpToY(int encVal) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.lift);
    	targetPos = encVal;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.lift.moveUp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.lift.getLiftEncoder() > targetPos){
    		quit = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return quit;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.lift.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
