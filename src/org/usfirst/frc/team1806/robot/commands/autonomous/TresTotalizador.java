package org.usfirst.frc.team1806.robot.commands.autonomous;

import org.usfirst.frc.team1806.robot.commands.Wait;
import org.usfirst.frc.team1806.robot.commands.DrivetrainCommands.DriveStraightToDistance;
import org.usfirst.frc.team1806.robot.commands.IntakeCommands.Intake1D;
import org.usfirst.frc.team1806.robot.commands.IntakeCommands.IntakeRun;
import org.usfirst.frc.team1806.robot.commands.IntakeCommands.IntakeStop;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.AutoStack;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.OpACommand;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.OpenArms;
import org.usfirst.frc.team1806.robot.Constants;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team1806.robot.commands.DrivetrainCommands.TurnToAngle;

/**
 *
 */
public class TresTotalizador extends CommandGroup {
    
    public  TresTotalizador() {
       
    	addParallel(new OpACommand());
    	//Pick Up first tote
    	addParallel(new AutoStack());
    	addSequential(new Wait(1));
    	addParallel(new Intake1D(true));	//spin Can Left
    	addParallel(new DriveStraightToDistance(Constants.firstCanDistance, .3, true));
    	//Drive to and Intake the Second Tote
    	addSequential(new IntakeRun(.5));
    	addSequential(new DriveStraightToDistance(Constants.secondToteDistance, .8, true));
    	addSequential(new AutoStack());
    	//Spin Can out of the way
    	addParallel(new Intake1D(false));
    	addParallel(new DriveStraightToDistance(Constants.secondCanDistance, .3, true));
    	//Drive to and Intake the Third Tote
    	addSequential(new IntakeRun(.5));
    	addSequential(new DriveStraightToDistance(Constants.thirdToteDistance, .8, true));
    	addSequential(new AutoStack());
    	//Spin Can out of the way
    	addParallel(new IntakeStop());
    	//drop Tote off
    	addSequential(new TurnToAngle(90,.8));//TODO: Figure out what the angle orientation is. 
    	addSequential(new DriveStraightToDistance(Constants.dropOffDistance, 1, true));
    	addSequential(new OpenArms());
    	//Back away from Tote
    	addParallel(new IntakeRun(-.5));
    	addParallel(new DriveStraightToDistance(Constants.backUpDistance, -1, true));
    	
    }
}
