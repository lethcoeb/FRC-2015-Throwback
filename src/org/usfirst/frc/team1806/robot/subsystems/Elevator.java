package org.usfirst.frc.team1806.robot.subsystems;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.OI;
import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.RobotMap;
import org.usfirst.frc.team1806.robot.States;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Elevator extends PIDSubsystem {
    
	Talon elevatorMotorTalon = new Talon(RobotMap.elevatorMotor);
	DoubleSolenoid secondStage = new DoubleSolenoid(RobotMap.secondStageHold, RobotMap.secondStageRelease);
	DoubleSolenoid armsExtend = new DoubleSolenoid(RobotMap.extendIn, RobotMap.extendOut);
	DoubleSolenoid armsPinch = new DoubleSolenoid(RobotMap.armsPinch, RobotMap.armsRelease);
	DoubleSolenoid brake = new DoubleSolenoid(RobotMap.brakeHold, RobotMap.brakeLeggo);
	Encoder liftEncoder = new Encoder(RobotMap.liftEncA, RobotMap.liftEncB);
	DigitalInput bottomLimit = new DigitalInput(RobotMap.bottomSensor);
	DigitalInput topLimit = new DigitalInput(RobotMap.topSensor);
	DigitalInput opticalSensor = new DigitalInput(RobotMap.photoSensor);
	
	//public States statesObj = new States();


	
	
	
	//public String getLocalOccurences(){
	//	return local_Occurrences.toString();
	//}
	
	
	//getters
	
	
	public double getLiftEncoder(){
		return liftEncoder.get();
	}
	
	//limit switches are reversed - the ! is to reverse them to be right lol
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
		
		//if youre at the top and sending power up, it's unsafe. vice versa
		//limit switches are reversed because the sensors are backwards
		if((!topLimit.get() && liftPower > 0)  || (!bottomLimit.get() && liftPower < 0)){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean isWithinRange(double target){
		return !(Math.abs(target - Robot.lift.getLiftEncoder()) > Constants.acceptableHeightRange);
	}
	
	//constructor
	public Elevator(double P, double I, double D){
		super(P, I, D);
		getPIDController().setContinuous(false);
		setAbsoluteTolerance(30);
	}
	
	public void manualMove(double speed){
		
		
		if((!bottomLimit.get() && speed < 0)  ||  (!topLimit.get() && speed > 0)){
			System.out.println("no can do cracker jack");
		}else{
			brakeOff();
			elevatorMotorTalon.set(speed);
		}
	}
	//make it do shit
	public void moveUp(){
		brakeOff();
		if(topLimit.get()){
			elevatorMotorTalon.set(1);
		}
		else{
			System.out.println("Invalid - elevator too high");
		}
		
	}
	
	/*public void moveUpToPos(double targetPosition){
		elevatorMotorTalon.pidWrite();
	}*/

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
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	//setters for conditions

	
    public void initDefaultCommand() {

    	
    	
    }

	@Override
	protected double returnPIDInput() {
		return liftEncoder.get();
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		brakeOff();
		elevatorMotorTalon.set(output);
	}
}

