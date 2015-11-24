package org.usfirst.frc.team1806.robot.commands.elevatorCommands;

import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.States;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftReset extends Command {

    public LiftReset() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.lift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(!Robot.lift.getBottomLimit()){
    		Robot.lift.moveDownSlow();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.lift.getBottomLimit();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.lift.stop();
    	Robot.lift.openArms();
    	Robot.lift.retractArms();
    	Robot.lift.resetEncoder();
    	Robot.lift.secondStageRelease();
    	Robot.statesObj.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
