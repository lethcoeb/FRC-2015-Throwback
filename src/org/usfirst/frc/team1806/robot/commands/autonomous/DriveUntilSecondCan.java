package org.usfirst.frc.team1806.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team1806.robot.Constants;

/**
 *
 */
public class DriveUntilSecondCan extends CommandGroup {
    
    public  DriveUntilSecondCan() {
        addSequential(new DriveStraightToDistance(secondToteDistance, .8, true));
        addSequential(new )
    }
}
