package org.usfirst.frc.team1806.robot.commands.elevatorCommands;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveToTarget extends Command {

	private boolean pid_enabled = false;
	private double m_targetPos;
	
    public MoveToTarget(double targetPos) {
    	requires(Robot.elevatorSS);
    	m_targetPos = targetPos;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//move up full speed, move down full speed, or engage pid
    	Robot.elevatorSS.brakeOff();
    	if(Math.abs(m_targetPos - Robot.elevatorSS.getLiftEncoder()) > 100){
    		if(Robot.elevatorSS.getLiftEncoder() > m_targetPos){
    			Robot.elevatorSS.moveDown();
    		}else{
    			Robot.elevatorSS.moveUp();
    		}
    	}else{
    		//you're within pid engage range, so engage ur pid
    		Robot.elevatorSS.enable();
    		Robot.elevatorSS.setSetpoint(m_targetPos);
    		pid_enabled = true;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if((Math.abs(m_targetPos - Robot.elevatorSS.getLiftEncoder()) < 100) && !pid_enabled){
    		//you need to enable the pid if your difference is less than 100 AND pid isn't currently enabled
    		Robot.elevatorSS.enable();
    		Robot.elevatorSS.setSetpoint(m_targetPos);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.elevatorSS.isWithinRange(m_targetPos);
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("pid movement to " + m_targetPos + " finished");
    	Robot.elevatorSS.disable();
    	Robot.elevatorSS.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.elevatorSS.disable();
    }
}
