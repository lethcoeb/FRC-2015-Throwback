package org.usfirst.frc.team1806.robot.commands.elevatorCommands;

import org.usfirst.frc.team1806.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveFastToTarget extends Command {

	private double m_target;
	private boolean movingUp;
	
    public MoveFastToTarget(double target) {
        requires(Robot.elevatorSS);
        m_target = target;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(Robot.elevatorSS.getLiftEncoder() > m_target){
    		Robot.elevatorSS.moveDown();
    		movingUp = false;
    	}else{
    		Robot.elevatorSS.moveUp();
    		movingUp = true;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(movingUp){
    		return Robot.elevatorSS.getLiftEncoder() > m_target;
    	}else{
    		return Robot.elevatorSS.getLiftEncoder() < m_target;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elevatorSS.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.elevatorSS.zeroPower();
    }
}
