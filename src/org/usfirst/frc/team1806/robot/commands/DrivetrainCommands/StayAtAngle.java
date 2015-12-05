package org.usfirst.frc.team1806.robot.commands.DrivetrainCommands;

import static org.usfirst.frc.team1806.robot.Robot.drivetrainSS;

import org.usfirst.frc.team1806.robot.OI;
import org.usfirst.frc.team1806.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StayAtAngle extends Command {

	//.0365
	private double P = .0375;
	//.00002 is too low
	private double I = .000045;
	private double D = 0;
	
	private PIDController tc;
	private PIDSource ps;
	private PIDOutput po;
	
    public StayAtAngle() {
    	drivetrainSS.resetAngle();
    	requires(Robot.drivetrainSS);

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
				drivetrainSS.arcadeDrive(0, output);
			}
		};
		
		tc = new PIDController(P, I, D, ps, po);
    	tc.setAbsoluteTolerance(1);
    	tc.setInputRange(-180, 180);
    	tc.setContinuous();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drivetrainSS.resetAngle();
    	OI.driverDTControl = false;
    	tc.enable();
    	tc.setSetpoint(0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
