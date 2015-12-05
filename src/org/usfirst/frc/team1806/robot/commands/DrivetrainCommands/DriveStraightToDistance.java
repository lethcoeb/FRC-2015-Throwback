package org.usfirst.frc.team1806.robot.commands.DrivetrainCommands;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.PIDCommand;
import static org.usfirst.frc.team1806.robot.Robot.drivetrainSS;

/**
 *
 */
public class DriveStraightToDistance extends PIDCommand {

	private double m_distance;
	private double m_maxSpeed;
	
	private double PTurn = .0375;
	private double ITurn = .000045;
	private double DTurn = 0;
	
	private PIDController tController;
	private PIDSource ps;
	private PIDOutput po;
	
	private double tValue;
	
    public DriveStraightToDistance(double distance, double maxSpeed) {
    	
        super(Constants.drivetrainDriveP, Constants.drivetrainDriveI, Constants.drivetrainDriveD);
    	requires(drivetrainSS);
    	m_distance = distance;
    	m_maxSpeed = maxSpeed;
    	
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
    	
    	getPIDController().setContinuous(false);
        getPIDController().setAbsoluteTolerance(Constants.drivetrainDistanceTolerance);
        getPIDController().setOutputRange(-1, 1);
        
        Robot.oi.driverDTControl = false;
    	
    	setSetpoint(m_distance);
    	
    	tController = new PIDController(PTurn, ITurn, DTurn, ps, po);
		tController.setAbsoluteTolerance(1);
		tController.setInputRange(-180, 180);
		tController.setContinuous();
		tController.enable();
		tController.setSetpoint(0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//top one drifts, bottom one stops at target
        //return ((drivetrainSS.getRightInches() + drivetrainSS.getLeftInches())/2 > m_distance);
    	return (Math.abs((drivetrainSS.getRightInches() + drivetrainSS.getLeftInches())/2 - m_distance) < Constants.drivetrainDistanceTolerance && (drivetrainSS.getLeftRate() + drivetrainSS.getRightRate())/2 <=.1 && (drivetrainSS.getLeftRate() + drivetrainSS.getRightRate())/2 >= 0 ); 
    }

    // Called once after isFinished returns true
    protected void end() {
    	getPIDController().disable();
    	tController.disable();
       	Robot.oi.driverDTControl = true;
    	System.out.println("DriveStraight PID Loop Finished");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return (drivetrainSS.getLeftInches() + drivetrainSS.getRightInches())/2;
	}

	@Override
	protected void usePIDOutput(double output) {
		if(Math.abs(output) < m_maxSpeed){
			drivetrainSS.arcadeDrive(-output, tValue);
		}else{
			drivetrainSS.arcadeDrive(Math.signum(output) * -m_maxSpeed, tValue);
		}
		
	}
}
