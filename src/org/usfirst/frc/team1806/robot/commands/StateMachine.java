package org.usfirst.frc.team1806.robot.commands;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.usfirst.frc.team1806.robot.States;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class StateMachine extends Command {

	String state = "";
	
    public StateMachine() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	while(1==1){//While teleop/autonomous is enabled.
    		switch(state){
    		case "State 1": SmartDashboard.putString("Elevator State: ", "State 1" );
    			state = "State 2";
    			break;
    		case "State 2": SmartDashboard.putString("Elevator State: ", "State 2" );
    		state = "State 3";
				break;
    		case "State 3": SmartDashboard.putString("Elevator State: ", "State 3" );
    		state = "State 4";
				break;
    		case "State 4": SmartDashboard.putString("Elevator State: ", "State 4" );
    		state = "State 1";
				break;
    		default: SmartDashboard.putString("Elevator State: ",  "Default");
    			state = "State 1";
    			break;
    		}
    		
    	}
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
    }
}
