package org.usfirst.frc.team1806.robot.subsystems;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.RobotMap;
import org.usfirst.frc.team1806.robot.States;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class Elevator extends PIDSubsystem {
    
	//initialize motors and sensors and solenoids OH MY!
	Talon elevatorMotorTalon = new Talon(RobotMap.elevatorMotor);
	DoubleSolenoid secondStage = new DoubleSolenoid(RobotMap.secondStageHold, RobotMap.secondStageRelease);
	DoubleSolenoid armsExtend = new DoubleSolenoid(RobotMap.extendIn, RobotMap.extendOut);
	DoubleSolenoid armsPinch = new DoubleSolenoid(RobotMap.armsPinch, RobotMap.armsRelease);
	DoubleSolenoid brake = new DoubleSolenoid(RobotMap.brakeHold, RobotMap.brakeLeggo);
	Encoder liftEncoder = new Encoder(RobotMap.liftEncA, RobotMap.liftEncB);
	DigitalInput bottomLimit = new DigitalInput(RobotMap.bottomSensor);
	DigitalInput topLimit = new DigitalInput(RobotMap.topSensor);
	DigitalInput opticalSensor = new DigitalInput(RobotMap.photoSensor);
	
	
	/*
	 * Getters
	 */
	
	public double getLiftEncoder(){
		return liftEncoder.get();
	}
	
		//Limit switches & optical sensor are reversed so we switch them to get them to output an accurate value
	public boolean getBottomLimit(){
		return !bottomLimit.get();
	}
	
	public boolean getTopLimit(){
		return !topLimit.get();
	}
	
	public boolean getOpticalSensor(){
		return !opticalSensor.get();
	}
	
	public int getLiftPowerPercentage(){
		return ((elevatorMotorTalon.getRaw()-1011) * 100/525);
	}
	
	public boolean isSafe(double liftPower){
		
		//If you're at the top and sending power up (or vice versa), it's unsafe.
		if((!topLimit.get() && liftPower > 0)  || (!bottomLimit.get() && liftPower < 0)){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean isWithinRange(double target){
		return !(Math.abs(target - Robot.elevatorSS.getLiftEncoder()) > Constants.acceptableHeightRange);
	}
	
	public boolean isWithinTightRange(double target){
		return !(Math.abs(target - Robot.elevatorSS.getLiftEncoder()) > Constants.acceptableTightHeightRange);
	}
	
	
	//constructor
	public Elevator(double P, double I, double D){
		super(P, I, D);
		getPIDController().setContinuous(false);
		setAbsoluteTolerance(Constants.acceptableHeightRange);
	}
	
	
	/*
	 * Motor and Solenoid manipulation
	 */
	
	public void manualMove(double speed){
		
		//Don't allow movement if you're trying to move the lift past its hardstops
		if((!bottomLimit.get() && speed < 0)  ||  (!topLimit.get() && speed > 0)){
			System.out.println("no can do cracker jack");
		}else{
			brakeOff();
			elevatorMotorTalon.set(speed);
		}
	}
	
	public void moveUp(){
		brakeOff();
		if(topLimit.get()){
			elevatorMotorTalon.set(1);
		}
		else{
			System.out.println("Invalid - elevator too high");
		}
		
	}

	public void moveDown(){
		if(bottomLimit.get()){
			brakeOff();
			elevatorMotorTalon.set(-.75);
		}
		else{
			System.out.println("Invalid - elevator too low");
		}
	}
	
	public void moveDownSlow(){
		if(bottomLimit.get()){
			brakeOff();
			elevatorMotorTalon.set(-.3);
		}
		else{
			System.out.println("invalid - elevator too low");
		}
		
	}
	
	public void moveDownMedium(){
		if(bottomLimit.get()){
			brakeOff();
			elevatorMotorTalon.set(-.5);
		}
		else{
			System.out.println("invalid - elevator too low");
		}
	}
	
	public void stop(){
		elevatorMotorTalon.set(0);
		brakeOn();
	}
	
	public void zeroPower(){
		elevatorMotorTalon.set(0);
	}
	
	public void openArms(){
		armsPinch.set(DoubleSolenoid.Value.kForward);
		Robot.statesObj.clampStateTracker = States.clampState.ARMS_OPEN;
	}
	
	public void closeArms(){
		armsPinch.set(DoubleSolenoid.Value.kReverse);
		Robot.statesObj.clampStateTracker = States.clampState.ARMS_CLAMPED;
	}
	
	public void extendArms(){
		armsExtend.set(DoubleSolenoid.Value.kReverse);
		Robot.statesObj.extendStateTracker = States.extendState.ARMS_EXTENDED;
	}
	
	public void retractArms(){
		armsExtend.set(DoubleSolenoid.Value.kForward);
		Robot.statesObj.extendStateTracker = States.extendState.ARMS_RETRACTED;
	}
	
	public void secondStageHold(){
		secondStage.set(DoubleSolenoid.Value.kForward);
		Robot.statesObj.secondStageStateTracker = States.secondStageState.SECOND_STAGE_HOLDING;
	}
	
	public void secondStageRelease(){
		secondStage.set(DoubleSolenoid.Value.kReverse);
		Robot.statesObj.secondStageStateTracker = States.secondStageState.SECOND_STAGE_RELEASED;
	}
	
	public void secondStageFlip(){
		if (secondStage.get() == DoubleSolenoid.Value.kReverse) {
			secondStage.set(DoubleSolenoid.Value.kForward);
			Robot.statesObj.secondStageStateTracker = States.secondStageState.SECOND_STAGE_HOLDING;

		}
		else{
			secondStage.set(DoubleSolenoid.Value.kReverse);
			Robot.statesObj.secondStageStateTracker = States.secondStageState.SECOND_STAGE_RELEASED;
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
	
    public void initDefaultCommand() {

    }

    /*
     * PID Stuff
     * 
     * To use PID: use the .SetSetpoint method in commands
     */
    
	@Override
	protected double returnPIDInput() {
		//The sensor that the pid uses to calculate error
		return liftEncoder.get();
	}

	@Override
	protected void usePIDOutput(double output) {
		//What is controlled by the PID output
		brakeOff();
		if(Robot.statesObj.robotModeTracker == States.robotMode.AUTOSTACK){
			if(Robot.statesObj.totesHeld == 0){
				elevatorMotorTalon.set( output* .85);
			}else if(Robot.statesObj.totesHeld == 1){
				elevatorMotorTalon.set( output* .98);

			}
		
			else{
				elevatorMotorTalon.set(output);
			}
		}
	}
}

