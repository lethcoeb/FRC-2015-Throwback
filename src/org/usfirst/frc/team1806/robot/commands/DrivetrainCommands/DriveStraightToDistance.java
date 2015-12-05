package org.usfirst.frc.team1806.robot.commands.DrivetrainCommands;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;
import static org.usfirst.frc.team1806.robot.Robot.drivetrainSS;

/**
 *
 */
public class DriveStraightToDistance extends PIDCommand {

	private double m_distance;
	private double m_maxSpeed;
	private final double P = .04;
	
    public DriveStraightToDistance(double distance, double maxSpeed) {
    	
        super(Constants.drivetrainDriveP, Constants.drivetrainDriveI, Constants.drivetrainDriveD);
    	requires(drivetrainSS);
    	m_distance = distance;
    	m_maxSpeed = maxSpeed;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drivetrainSS.resetAngle();
    	drivetrainSS.resetEncoders();
    	
    	getPIDController().setContinuous(false);
        getPIDController().setAbsoluteTolerance(Constants.drivetrainDistanceTolerance);
        getPIDController().setOutputRange(-1, 1);
    	
    	setSetpoint(m_distance);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Math.abs((drivetrainSS.getRightInches() + drivetrainSS.getLeftInches())/2 - m_distance) < Constants.drivetrainDistanceTolerance && (drivetrainSS.getLeftRate() + drivetrainSS.getRightRate())/2 <=.1); 
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrainSS.arcadeDrive(0, 0);
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
			drivetrainSS.arcadeDrive(-output, 0);
		}else if(Math.signum(output) == 1){
			drivetrainSS.arcadeDrive(-m_maxSpeed, 0);
		}else{
			drivetrainSS.arcadeDrive(m_maxSpeed, P * 0);
		}
		
	}
}
