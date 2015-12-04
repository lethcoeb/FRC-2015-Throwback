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
	private final double P = .04;
	
    public DriveStraightToDistance(double distance) {
    	
        super(Constants.drivetrainDriveP, Constants.drivetrainDriveI, Constants.drivetrainDriveD);
    	requires(drivetrainSS);
    	m_distance = distance;
    	
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
        return (getPIDController().onTarget() && drivetrainSS.getLeftRate() <=.1); 
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
		return drivetrainSS.getLeftInches();
	}

	@Override
	protected void usePIDOutput(double output) {
		
		drivetrainSS.arcadeDrive(-output, P * drivetrainSS.getAngle());
		
	}
}
