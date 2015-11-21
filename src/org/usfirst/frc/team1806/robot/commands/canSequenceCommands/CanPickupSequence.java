package org.usfirst.frc.team1806.robot.commands.canSequenceCommands;

import org.usfirst.frc.team1806.robot.commands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CanPickupSequence extends CommandGroup {
    
    public  CanPickupSequence() {
      
    	addSequential(new PickupCan());
    	addSequential(new MoveCanToStackingHeight());
    	
    }
}
