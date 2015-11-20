package org.usfirst.frc.team1806.robot.commands;

import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.States;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.MoveToTarget;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.OpenArms;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DropSequence extends CommandGroup {
    
    public  DropSequence() {
    	
    	//if arms extended, drop on platform and release
    	if(Robot.statesObj.getExtendState() == States.extendState.ARMS_EXTENDED){
    		//need to  add code to drop stack on platform
    		addSequential(new OpenArms());
    	}
    	
    	//if you're holding the drop button, then it should keep dropping while you back up maybe????
    	else if(Robot.lift.getLiftEncoder() <= 0){
    		addSequential(new OpenArms());
    	}
    	
    	//if the arms aren't extended AND ur lift is in the air, just drop what u got fam
    	else{
    		addSequential(new MoveToTarget(0));
    		addSequential(new OpenArms());
    	}
    }
}
