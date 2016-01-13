package org.usfirst.frc.team1806.robot.commands.elevatorCommands;

import java.sql.Time;

import org.usfirst.frc.team1806.robot.Commands;
import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.Commands.opACommands;
import org.usfirst.frc.team1806.robot.Commands.resetCommand;
import org.usfirst.frc.team1806.robot.States.canSequenceState;
import org.usfirst.frc.team1806.robot.States.clampState;
import org.usfirst.frc.team1806.robot.States.secondStageState;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 *
 */
public class Autostack2 extends Command {

	private enum States{
		START, CLAMP, MOVETOHOLDING, MOVETOSECONDSTAGE, SECONDSTAGEENGAGE, MOVEDOWN, RELEASE, RESET
	}
	
	private Commands commands;
	
	private States tracker;
	
	private boolean finished = false;
	
	private Timer timer = new Timer();
	
    public Autostack2() {
        requires(Robot.drivetrainSS);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	tracker = States.START;
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	commands = Robot.oi.updateCmds();
    	
    	if(commands.resetCommandTracker == resetCommand.ON){
    		tracker = States.RESET;
    	}
    	
    	switch (tracker) {
		case START:
			
			Robot.statesObj.reset();
			tracker = States.CLAMP;
			System.out.print("Start run, moving to CLAMP");
			
			break;
			
		case CLAMP:
			
			Robot.elevatorSS.closeArms();
			tracker = States.MOVETOHOLDING;
			System.out.println("CLAMP run, moving to MOVETOHOLDING");
		
			break;
			
		case MOVETOHOLDING:
			
			if(Robot.elevatorSS.getSpeed() == 0){
				Robot.elevatorSS.moveUp();
			}else if(commands.opACommandsTracker == opACommands.ON){
				tracker = States.MOVETOSECONDSTAGE;
				System.out.println("MOVETOHOLDING done, moving to MOVETOSECONDSTAGE");
			}else if(Robot.elevatorSS.getLiftEncoder() > Constants.holdingPosition){
				Robot.elevatorSS.stop();
			}
			
			break;
			
		case MOVETOSECONDSTAGE:
			
			if(Robot.elevatorSS.getSpeed() == 0){
				Robot.elevatorSS.moveUp();
			}else if((Constants.secondStageHeight - Robot.elevatorSS.getLiftEncoder()) < 100 && !Robot.elevatorSS.isEnabled()){
				Robot.elevatorSS.enable();
				Robot.elevatorSS.setSetpoint(Constants.secondStageHeight);
			}else if(Robot.elevatorSS.isWithinRange(Constants.secondStageHeight) && Robot.elevatorSS.getSpeed() < .2 && Robot.elevatorSS.getSpeed() > 0){
				tracker = States.SECONDSTAGEENGAGE;
				System.out.println("MOVETOSECONDSTAGE done, moving to SECONDSTAGEENGAGE");
			}
			
			break;
			
		case SECONDSTAGEENGAGE:
			
			if(Robot.statesObj.secondStageStateTracker == secondStageState.SECOND_STAGE_RELEASED){
				Robot.elevatorSS.secondStageHold();
				timer.start();
				timer.reset();
			}else if(timer.get() > .5){
				Robot.elevatorSS.disable();
				timer.stop();
				tracker = States.MOVEDOWN;
				System.out.println("SECONDSTAGEENGAGE done, moving to MOVEDOWN");
			}
			
			break;
			
		case MOVEDOWN:
			
			if(Robot.elevatorSS.getSpeed() != -.75){
				Robot.elevatorSS.moveDown();
			}else if(Robot.elevatorSS.getLiftEncoder() <= Constants.secondStageHeight - 100 && Robot.statesObj.clampStateTracker == clampState.ARMS_CLAMPED){
				Robot.elevatorSS.openArms();
				System.out.println("Arms released");
			}
			else if(Robot.elevatorSS.getLiftEncoder() < 100 && Robot.elevatorSS.getSpeed() != 0){
				Robot.elevatorSS.zeroPower();
			}else if(Robot.elevatorSS.getBottomLimit()){
				Robot.elevatorSS.stop();
				finished = true;
			}
			
			
			break;

		case RESET:
			
			finished = true;
			new LiftReset().start();
    		
    		break;

		default:
			break;
    	}
    	
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Autostack sequence FINISHED!");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
