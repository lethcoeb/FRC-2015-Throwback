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
    	requires(Robot.elevatorSS);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.statesObj.driverIntakeControlTracker = States.driverIntakeControl.DRIVER;
    	Robot.intakeSS.stop();
    	Robot.elevatorSS.secondStageRelease();
    	if(!Robot.elevatorSS.getBottomLimit()){
    		Robot.elevatorSS.moveDownSlow();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.elevatorSS.getBottomLimit();
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    	Robot.elevatorSS.openArms();
    	Robot.elevatorSS.stop();
    	Robot.elevatorSS.retractArms();
    	Robot.elevatorSS.resetEncoder();

    	Robot.statesObj.reset();
    	System.out.println("now in auto mode");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
