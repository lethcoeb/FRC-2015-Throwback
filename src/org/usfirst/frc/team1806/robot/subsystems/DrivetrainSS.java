package org.usfirst.frc.team1806.robot.subsystems;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DrivetrainSS extends Subsystem{
    
	AHRS navx;
	Encoder leftEncoder;
	Encoder rightEncoder;

    RobotDrive rd = new RobotDrive(RobotMap.leftDriveMotor, RobotMap.rightDriveMotor);
    
    public DrivetrainSS(){
    	    	
    	try{
    		navx = new AHRS(SerialPort.Port.kMXP); /* Alternatives:  SPI.Port.kMXP, I2C.Port.kMXP or SerialPort.Port.kUSB */
    	}catch (RuntimeException ex ){
    		System.out.println("Error instantiating navX: " + ex.getMessage());
    		DriverStation.reportError("Error instantiating navX: " + ex.getMessage(), true);
    	}
    	
    	leftEncoder = new Encoder(RobotMap.lDriveEncA, RobotMap.lDriveEncB);
    	rightEncoder = new Encoder(RobotMap.rDriveEncA, RobotMap.rDriveEncB);
    	
    	//to get rid of annoying errors lol
    	rd.setSafetyEnabled(false);
    	
    	//leftEncoder.setDistancePerPulse(1/256);
    }
    
    public void arcadeDrive(double throttle, double turn){
    	rd.arcadeDrive(throttle, -turn);
    }
    
    public double getAngle(){
    	return navx.getAngle();
    }
    
    public double getAngle180(){
		if(navx.getAngle() <= 180){
			return navx.getAngle();
		}else{
			//angle is greater than 180
			//returns negative values up to 180
			return navx.getAngle() - 360;
		}
    }
    
    public double getRotationRate(){
    	return navx.getRate();
    }
    
    public void resetAngle(){
    	navx.zeroYaw();
    }
    
    public void resetEncoders(){
    	leftEncoder.reset();
    	rightEncoder.reset();
    }
    
    public double getLeftRaw(){
    	return leftEncoder.getRaw();
    }
    
    public double getLeftInches(){
    	
    	//55.041 counts per inch
    	return (leftEncoder.getRaw() / 55.04167);
    }
    
    public double getRightInches(){
    	return (rightEncoder.getRaw() / 55.04167);
    }
    
    public double getLeftDistance(){
    	return leftEncoder.getDistance();
    }
    
    public double getRightDistance(){
    	return rightEncoder.getDistance();
    }
    
    public double getLeftRate(){
    	return leftEncoder.getRate();
    }
    
    public double getRightRate(){
    	return rightEncoder.getRate();
    }
    
    double rotateToAngleRate;
    
    public void initDefaultCommand() {
        
    }
}

