package org.usfirst.frc.team1806.robot.commands.canSequenceCommands;

import org.usfirst.frc.team1806.robot.commands.ResetStates;
import org.usfirst.frc.team1806.robot.commands.Wait;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.MoveToTarget;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.MoveToZero;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PlaceCanOnTote extends CommandGroup {
    
    public  PlaceCanOnTote() {
        
    	
    	addSequential(new MoveToZeroCan());
    	//addSequential(new AbleToOpenArms());
    	addSequential(new ResetStates());
    	
    	
    }
}
