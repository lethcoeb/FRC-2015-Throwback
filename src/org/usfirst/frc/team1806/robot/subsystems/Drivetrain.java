package org.usfirst.frc.team1806.robot.subsystems;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drivetrain extends Subsystem implements PIDOutput {
    
	public AHRS navx;
	
    Talon rightDrive = new Talon(RobotMap.rightDriveMotor);
    Talon leftDrive = new Talon(RobotMap.leftDriveMotor);

    PIDController turnController = new PIDController(Constants.drivetrainP, Constants.drivetrainI, Constants.drivetrainD, 0, navx, this);

    public Drivetrain(){
    	
    	try{
    		navx = new AHRS(SerialPort.Port.kMXP); /* Alternatives:  SPI.Port.kMXP, I2C.Port.kMXP or SerialPort.Port.kUSB */
    	}catch (RuntimeException ex ){
    		System.out.println("Error instantiating navX: " + ex.getMessage());
    		DriverStation.reportError("Error instantiating navX: " + ex.getMessage(), true);
    	}
    	
    	turnController.setInputRange(-180.0f,  180.0f);
        turnController.setOutputRange(-1.0, 1.0);
        turnController.setAbsoluteTolerance(Constants.drivetrainAngleTolerance);
        turnController.setContinuous(true);
    }
    
    public void setAngle(double targetAngle){
    	turnController.setSetpoint(targetAngle);
    }
    
    double rotateToAngleRate;
    
    public void initDefaultCommand() {
        
    }

	@Override
	public void pidWrite(double output) {
		// TODO Auto-generated method stub
		rotateToAngleRate = output;
	}
}

