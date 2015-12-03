package org.usfirst.frc.team1806.robot.commands.autonomous;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.CloseArms;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.MoveToTarget;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PickupCanAndDriveBackwards extends CommandGroup {
    
    public  PickupCanAndDriveBackwards() {
        
    	addSequential(new CloseArms());
    	addSequential(new MoveToTarget(Constants.stackingCanHoldHeight));
    	addParallel(new WaitThenDrive(2, -24));
    	
    	
    }
}
