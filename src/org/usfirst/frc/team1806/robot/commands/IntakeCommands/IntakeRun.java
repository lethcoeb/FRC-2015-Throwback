package org.usfirst.frc.team1806.robot.commands.IntakeCommands;

import org.usfirst.frc.team1806.robot.Robot;

import edu.wpi.first.wpilibj.SerialPort.StopBits;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeRun extends Command {

	private double m_speed;
	private boolean stop = false;
	
    public IntakeRun(double speed) {
        requires(Robot.intakeSS);
        m_speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.intakeSS.runAtSpeed(m_speed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.oi.LTrigger > .1){
			Robot.intakeSS.runAtSpeed(Robot.oi.LTrigger);
		}else if(Robot.oi.RTrigger > .1){
			Robot.intakeSS.runAtSpeed(-Robot.oi.RTrigger);
		}else{
			stop = true;
		}
    }
    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return stop;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intakeSS.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	//Robot.intakeSS.stop();
    }
}
