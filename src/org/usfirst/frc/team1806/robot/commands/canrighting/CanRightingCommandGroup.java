package org.usfirst.frc.team1806.robot.commands.canrighting;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.LiftReset;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.MoveFastToTarget;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.MoveToTarget;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.MoveToZero;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CanRightingCommandGroup extends CommandGroup {
    
    public  CanRightingCommandGroup() {
        addSequential(new HoldingAndExtend());
        addSequential(new ClampAndDown());
        addSequential(new WaitForDriverInput());
        addSequential(new MoveFastToTarget(Constants.canRightingHighHeight));
        addSequential(new WaitForDriverInput());
        addSequential(new SetCanDown());
        addSequential(new LiftReset());
    }
}
