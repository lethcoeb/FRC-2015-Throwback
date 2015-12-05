package org.usfirst.frc.team1806.robot.subsystems;

import org.usfirst.frc.team1806.robot.RobotMap;

import org.usfirst.frc.team1806.robot.Robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {
	
	private boolean intakeRunning;
	Talon rightIntake = new Talon(RobotMap.rightIntakeMotor);
	Talon leftIntake = new Talon(RobotMap.leftIntakeMotor);
    
	public Intake(){
		
		intakeRunning = false;
			
	}
	
    public void runAtSpeed(double speed){
    	rightIntake.set(speed);
    	leftIntake.set(-speed);
    	intakeRunning = true;
    }
    
    public void run1D(boolean runLeft){
    	if(runLeft){
    		rightIntake.set(-.75);
    		leftIntake.set(-.75);
    	}else{
    		rightIntake.set(.75);
    		leftIntake.set(.75);
    	}
    }
    
    public void stop(){
    	rightIntake.set(0);
    	leftIntake.set(0);
    	intakeRunning = false;
    }
    
    public boolean isRunning(){
    	return intakeRunning;
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

