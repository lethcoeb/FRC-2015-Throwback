package org.usfirst.frc.team1806.robot.subsystems;

import org.usfirst.frc.team1806.robot.RobotMap;

import org.usfirst.frc.team1806.robot.Robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {
	
	Talon rightIntake = new Talon(RobotMap.rightIntakeMotor);
	Talon leftIntake = new Talon(RobotMap.leftIntakeMotor);
    
	public Intake(){
			
	}
	
    public void runAtSpeed(double speed){
    	rightIntake.set(speed);
    	leftIntake.set(-speed);
    }
    
    public void stop(){
    	rightIntake.set(0);
    	leftIntake.set(0);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

