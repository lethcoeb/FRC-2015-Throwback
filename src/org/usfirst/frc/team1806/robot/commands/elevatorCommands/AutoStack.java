package org.usfirst.frc.team1806.robot.commands.elevatorCommands;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.RobotMap;
import org.usfirst.frc.team1806.robot.States;
import org.usfirst.frc.team1806.robot.commands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoStack extends CommandGroup {
    
    public  AutoStack() {
    	

    	addSequential(new CloseArms());
    	addSequential(new Wait(.25));
    	addSequential(new MoveFastToHolding());
    	addSequential(new waitForOpButton());
    	addSequential(new SecondStageRelease());
    	addSequential(new MoveToSecondStage());
       	addSequential(new SecondStageHold());
    	addSequential(new Wait(.25));
    	addSequential(new OpenArms());
    	addSequential(new MoveToZero());
    	

    }
}
