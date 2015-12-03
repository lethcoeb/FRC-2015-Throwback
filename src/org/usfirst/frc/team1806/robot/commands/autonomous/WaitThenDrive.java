package org.usfirst.frc.team1806.robot.commands.autonomous;

import org.usfirst.frc.team1806.robot.commands.Wait;
import org.usfirst.frc.team1806.robot.commands.DrivetrainCommands.DriveStraightToDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class WaitThenDrive extends CommandGroup {
    
    public  WaitThenDrive(double time, double distance) {
        
    	addSequential(new Wait(time));
    	addSequential(new DriveStraightToDistance(distance));
    	
    }
}
