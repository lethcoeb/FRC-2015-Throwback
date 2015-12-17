package org.usfirst.frc.team1806.robot.commands.DrivetrainCommands;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import static org.usfirst.frc.team1806.robot.Robot.drivetrainSS;

/**
 *
 */
public class DriveStraightToDistance extends Command {
	
	private static double m_driveP;
	private static double m_driveI;
	private static double m_driveD;
	
	private boolean iEnabled = false;
	
	private double m_distance;
	private double m_maxSpeed;
	
	private double PTurn = .0375;
	private double ITurn = .000045;
	private double DTurn = 0;
	
	private PIDController dController;
	private PIDSource Dps;
	private PIDOutput Dpo;
	
	private PIDController tController;
	private PIDSource ps;
	private PIDOutput po;
	
	private double tValue;
	private boolean m_moveFast;
	
    public DriveStraightToDistance(double distance, double maxSpeed, boolean moveFast) {
    	
    	setTimeout(distance/8);
    	requires(Robot.drivetrainSS);
    	m_distance = distance;
    	m_maxSpeed = maxSpeed;
    	m_moveFast = moveFast;
    	    	
    	if(m_moveFast){
    		m_driveP = Constants.drivetrainDriveP;
    		m_driveI = 0;
    		m_driveD = Constants.drivetrainDriveD;
    	}else{
    		//u moving slow
    		m_driveP = Constants.drivetrainDrivePSlow;
    		m_driveI = 0;
    		m_driveD = Constants.drivetrainDriveDSlow;
    	}
    	
    	Dps = new PIDSource(){

			@Override
			public double pidGet() {
				return (drivetrainSS.getLeftInches() + drivetrainSS.getRightInches())/2;
			}
    	};
    	
    	Dpo = new PIDOutput() {
			
			@Override
			public void pidWrite(double output) {
				if(Math.abs(output) < m_maxSpeed){
					drivetrainSS.arcadeDrive(-output, tValue);
				}else{
					drivetrainSS.arcadeDrive(Math.signum(output) * -m_maxSpeed, tValue);
				}
				
				//TODO: Remove me!!!!
				SmartDashboard.putNumber("power", Robot.drivetrainSS.getPower());
				SmartDashboard.putNumber("Turn", Robot.drivetrainSS.getTurn());
			}
		};
    	
    	ps = new PIDSource() {
			
			@Override
			public double pidGet() {
				// TODO Auto-generated method stub
				return drivetrainSS.getAngle180();
			}
		};
		
		po = new PIDOutput() {
			
			@Override
			public void pidWrite(double output) {
				// TODO Auto-generated method stub
				tValue = output;
			}
		};
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	    	
    	drivetrainSS.resetAngle();
    	drivetrainSS.resetEncoders();
    	
    	dController = new PIDController(m_driveP, m_driveI, m_driveD, Dps, Dpo);
    	dController.setContinuous(false);
    	dController.setAbsoluteTolerance(Constants.drivetrainDistanceTolerance);
    	dController.setOutputRange(-1, 1);
    	dController.enable();
    	dController.setSetpoint(m_distance);
    	
    	tController = new PIDController(PTurn, ITurn, DTurn, ps, po);
		tController.setAbsoluteTolerance(1);
		tController.setInputRange(-180, 180);
		tController.setContinuous();
		tController.enable();
		tController.setSetpoint(0);
		
		Robot.oi.driverDTControl = false;
	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if(!iEnabled && Math.abs((drivetrainSS.getRightInches() + drivetrainSS.getLeftInches())/2) < .4 
    		&& (drivetrainSS.getLeftRate() + drivetrainSS.getRightRate())/2 <=.05){
    		if(m_moveFast){
    			dController.setPID(Constants.drivetrainDriveP, Constants.drivetrainDriveI, Constants.drivetrainDriveD * .9);
    		}else{
    			dController.setPID(Constants.drivetrainDrivePSlow, Constants.drivetrainDriveISlow, Constants.drivetrainDriveDSlow * .9);
    		}
    		iEnabled = true;
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//top one drifts, bottom one stops at target
        //return ((drivetrainSS.getRightInches() + drivetrainSS.getLeftInches())/2 > m_distance);
    	return (Math.abs((drivetrainSS.getRightInches() + drivetrainSS.getLeftInches())/2 - m_distance) < Constants.drivetrainDistanceTolerance && (drivetrainSS.getLeftRate() + drivetrainSS.getRightRate())/2 <=.05 && (drivetrainSS.getLeftRate() + drivetrainSS.getRightRate())/2 >= 0 ); 
    }

    // Called once after isFinished returns true
    protected void end() {
    	dController.disable();
    	tController.disable();
       	Robot.oi.driverDTControl = true;
    	System.out.println("DriveStraight PID Loop Finished");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
