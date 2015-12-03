package org.usfirst.frc.team1806.robot.commands.elevatorCommands;

import org.usfirst.frc.team1806.robot.OI;
import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.States;
import org.usfirst.frc.team1806.robot.XboxController;
import org.usfirst.frc.team1806.robot.States.robotMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class waitForOpButton extends Command {

	private boolean cont;
	
    public waitForOpButton() {
        requires(Robot.elevatorSS);
    }

    // Called just before this Command runs the first time
    protected void initialize(){
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	cont = (Robot.statesObj.elevatorCommandTracker == States.elevatorCommand.MOVETONEXT);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return cont;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Got button A");
    	Robot.statesObj.elevatorCommandTracker = States.elevatorCommand.WAITING;
    	Robot.statesObj.liftPositionTracker = States.liftPosition.OTHER;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.elevatorSS.zeroPower();
    }
}
