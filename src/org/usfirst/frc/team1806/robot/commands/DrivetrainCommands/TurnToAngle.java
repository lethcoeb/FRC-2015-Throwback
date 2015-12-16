package org.usfirst.frc.team1806.robot.commands.DrivetrainCommands;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.subsystems.DrivetrainSS;
import static org.usfirst.frc.team1806.robot.Robot.drivetrainSS;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 *
 */
public class TurnToAngle extends PIDCommand {

	private double m_angle;
	private double m_maxTurnPower;
	
    public TurnToAngle(double angle, double maxTurnPower) {
    	
    	super(Constants.drivetrainRotateP, Constants.drivetrainRotateI, Constants.drivetrainRotateD);
        requires(Robot.drivetrainSS);
        m_angle = angle;
        m_maxTurnPower = maxTurnPower;
        getPIDController().setContinuous(true);
        getPIDController().setAbsoluteTolerance(Constants.drivetrainAngleTolerance);
        getPIDController().setInputRange(0, 360);
        getPIDController().setOutputRange(-1, 1);
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrainSS.resetAngle();
    	setSetpoint(m_angle);
    	Robot.oi.driverDTControl = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Math.abs(Robot.drivetrainSS.getAngle180() - m_angle) < Constants.drivetrainAngleTolerance && Math.abs(Robot.drivetrainSS.getRotationRate()) < .5;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.oi.driverDTControl = true;
    	drivetrainSS.arcadeDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return drivetrainSS.getAngle180();
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		
		if (output > m_maxTurnPower) {
    		drivetrainSS.arcadeDrive(0, m_maxTurnPower);
    	} else if (output < -m_maxTurnPower) {
    		drivetrainSS.arcadeDrive(0, -m_maxTurnPower);
    	} else {
    		drivetrainSS.arcadeDrive(0, output);
    	}
		
	}
}
