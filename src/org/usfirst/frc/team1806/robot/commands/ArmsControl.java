package org.usfirst.frc.team1806.robot.commands;

import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.States;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmsControl extends Command {

	//this command is probably too big.
	private String m_cmd;
	
    public ArmsControl(String cmd) {
        requires(Robot.lift);
        m_cmd = cmd;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	//if you wanna open them...
    	if(m_cmd == "release"){
    		Robot.lift.openArms();
    	}
    	
    	//if you wanna close them....
    	else if(m_cmd == "clamp"){
    		Robot.lift.closeArms();
    	}
    	
    	//if you wanna flip them.....
    	else if(m_cmd == "flipClamp"){
    		if(Robot.statesObj.getClampState() == States.clampState.ARMS_OPEN){
    			Robot.lift.closeArms();
    		}else{
    			Robot.lift.openArms();
    		}
    	}
    	
    	//if you wanna extend them...
    	else if(m_cmd == "extend"){
    		Robot.lift.extendArms();
    	}
    	
    	//if you wanna retract them...
    	else if(m_cmd == "retract"){
    		Robot.lift.retractArms();
    	}
    	
    	//if you wanna flip them...
    	else if(m_cmd == "extendFlip"){
    		if(Robot.statesObj.getExtendState() == States.extendState.ARMS_EXTENDED){
    			Robot.lift.retractArms();
    		}else{
    			Robot.lift.extendArms();
    		}
    	}
    	
    	//if you messed something up.....
    	else{
    		System.out.println("Invalid paramater for ArmsControl");
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
