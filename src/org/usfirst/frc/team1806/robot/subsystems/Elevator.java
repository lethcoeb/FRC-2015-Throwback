package org.usfirst.frc.team1806.robot.subsystems;

import org.usfirst.frc.team1806.robot.OI;
import org.usfirst.frc.team1806.robot.RobotMap;
import org.usfirst.frc.team1806.robot.States;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Elevator extends Subsystem {
    
	Talon elevatorMotorTalon = new Talon(RobotMap.elevatorMotor);
	DoubleSolenoid secondStage = new DoubleSolenoid(RobotMap.secondStageHold, RobotMap.secondStageRelease);
	DoubleSolenoid armsExtend = new DoubleSolenoid(RobotMap.extendIn, RobotMap.extendOut);
	DoubleSolenoid armsPinch = new DoubleSolenoid(RobotMap.armsPinch, RobotMap.armsRelease);
	DoubleSolenoid brake = new DoubleSolenoid(RobotMap.brakeHold, RobotMap.brakeLeggo);
	Encoder liftEncoder = new Encoder(RobotMap.liftEncA, RobotMap.liftEncB);
	DigitalInput bottomLimit = new DigitalInput(RobotMap.bottomSensor);
	DigitalInput topLimit = new DigitalInput(RobotMap.topSensor);
	DigitalInput opticalSensor = new DigitalInput(RobotMap.photoSensor);
	
	//getters
	public int getLiftEncoder(){
		return liftEncoder.get();
	}
	
	public boolean getBottomLimit(){
		return bottomLimit.get();
	}
	
	public boolean getTopLimit(){
		return topLimit.get();
	}
	
	public boolean getOpticalSensor(){
		return !opticalSensor.get();
	}
	
	public int getLiftPowerPercentage(){
		return ((elevatorMotorTalon.getRaw()-1011) * 100/525);
	}
	
	//constructor
	public Elevator(){
		
	}
	
	//make it do shit
	public void moveUp(){
		brakeOff();
		if(topLimit.get()){
			elevatorMotorTalon.set(1);
		}
		else{
			SmartDashboard.putString("Elevator State: ", "too high");
		}
		
	}
	//MAKE SURE TO ADD THE CONDITIONAL STATEMENTS
	public void moveDown(){
		brakeOff();
		elevatorMotorTalon.set(-.75);
		
	}
	
	public void moveDownSlow(){
		brakeOff();
		elevatorMotorTalon.set(-.3);
	}
	
	public void stop(){
		elevatorMotorTalon.set(0);
		brakeOn();
	}
	
	public void openArms(){
		armsPinch.set(DoubleSolenoid.Value.kForward);
	}
	
	public void closeArms(){
		armsPinch.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void extendArms(){
		armsExtend.set(DoubleSolenoid.Value.kForward);
	}
	
	public void retractArms(){
		armsExtend.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void secondStageHold(){
		secondStage.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void secondStageRelease(){
		secondStage.set(DoubleSolenoid.Value.kForward);
	}
	
	public void secondStageFlip(){
		if (secondStage.get() == DoubleSolenoid.Value.kReverse) {
			secondStage.set(DoubleSolenoid.Value.kForward);
		}
		else{
			secondStage.set(DoubleSolenoid.Value.kReverse);
		}
	}
	
	public void brakeOn(){
		brake.set(DoubleSolenoid.Value.kForward);
	}
	
	public void brakeOff(){
		brake.set(DoubleSolenoid.Value.kReverse);
	}
	
	
	public void resetEncoder(){
		liftEncoder.reset();
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {

    	
    	
    }
}

