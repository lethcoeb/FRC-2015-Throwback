package org.usfirst.frc.team1806.robot.commands.elevatorCommands;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.States;
import org.usfirst.frc.team1806.robot.commands.Wait;
import org.usfirst.frc.team1806.robot.commands.IntakeCommands.IntakeStop;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DropSequence extends CommandGroup {
    
    public  DropSequence() {
    	
    	//MAKE SURE TO SET LIFT POSITION STATE TO ZEROED AFTER DROP
    	
    	//if arms extended, drop on platform and release
    	if(Robot.statesObj.extendStateTracker == States.extendState.ARMS_EXTENDED && Robot.statesObj.liftPositionTracker == States.liftPosition.HOLDING_STATE){
    		addSequential(new IntakeStop());
    		addSequential(new MoveFastToTarget(Constants.holdingPosition - 30));
    		addSequential(new OpenArms());
    		addSequential(new Wait(1));
    		addSequential(new RetractArms());
    		addSequential(new Wait(.25));
    		addSequential(new LiftReset());
    	}
    	
    	if(Robot.statesObj.extendStateTracker != States.extendState.ARMS_EXTENDED && Robot.statesObj.liftPositionTracker != States.liftPosition.ZEROED) {
    		addSequential(new SecondStageRelease());
    		addSequential(new MoveToZero());
    		addSequential(new LiftReset());
    	}
    	//if your in manuel and ur high in the air trying to drop a stack on another sn00p dogg.

    }
}
