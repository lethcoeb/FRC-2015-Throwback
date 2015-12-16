package org.usfirst.frc.team1806.robot.commands.autonomous;

import org.usfirst.frc.team1806.robot.commands.Wait;
import org.usfirst.frc.team1806.robot.commands.DrivetrainCommands.DriveStraightToDistance;
import org.usfirst.frc.team1806.robot.commands.IntakeCommands.Intake1D;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.AutoStack;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.OpACommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TresTotalizador extends CommandGroup {
    
    public  TresTotalizador() {
       
    	addParallel(new OpACommand());
    	addParallel(new AutoStack());
    	addSequential(new Wait(1));
    	addParallel(new Intake1D(true));
    	addParallel(new DriveStraightToDistance(12, .3, false));
    	addSequential(new DriveStraightToDistance(12, .8, true));
    	
    }
}
