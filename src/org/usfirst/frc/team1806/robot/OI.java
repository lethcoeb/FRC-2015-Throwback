package org.usfirst.frc.team1806.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.usfirst.frc.team1806.robot.States;
import org.usfirst.frc.team1806.robot.commands.ArmsControl;
import org.usfirst.frc.team1806.robot.commands.DropSequence;
import org.usfirst.frc.team1806.robot.commands.HoldTote;
import org.usfirst.frc.team1806.robot.commands.LiftDown;
import org.usfirst.frc.team1806.robot.commands.LiftReset;
import org.usfirst.frc.team1806.robot.commands.LiftUp;
import org.usfirst.frc.team1806.robot.commands.OpACommand;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.AutoStack;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.ExtendArms;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.ManualMove;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.MoveToTarget;

import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    
	private final Joystick driverController = new Joystick(RobotMap.driverController);
	private final Joystick operatorController = new Joystick(RobotMap.operatorController);
	
	private Latch armsClampLatch = new Latch();
	private Latch armsExtendLatch = new Latch();
	private Latch robotModeLatch = new Latch();
	private CommandGroup manualGroup = new CommandGroup();
	
	public OI(){
	
		/*if(driverController.getButtonA()){
			new LiftUp().start();
		}
		
		if(driverController.getButtonB()){
			new LiftDown().start();
		}
		
		if(driverController.getButtonX()){
			new HoldTote().start();
		}
		
		if(driverController.getButtonY()){
			new MoveUpToY(1500).start();
		}*/
		
		/*if(driverController.getButtonLB() && statesRequests.elevatorStates == ElevatorStates.HOLDING){
			new GrabAndReset().start();
		}*/
		
		
		
		JoystickButton driverButtonA = new JoystickButton(driverController, 1);
		JoystickButton driverButtonB = new JoystickButton(driverController, 2);
		JoystickButton driverButtonX = new JoystickButton(driverController, 3);
		JoystickButton driverButtonY = new JoystickButton(driverController, 4);
		JoystickButton driverButtonLB = new JoystickButton(driverController, 5);
		JoystickButton driverButtonRB = new JoystickButton(driverController, 6);
		
		JoystickButton operatorButtonA = new JoystickButton(operatorController, 1);
		JoystickButton operatorButtonX = new JoystickButton(operatorController, 3);
		JoystickButton operatorButtonY = new JoystickButton(operatorController, 4);
		JoystickButton operatorButtonLB = new JoystickButton(operatorController, 5);
		JoystickButton operatorButtonRB = new JoystickButton(operatorController, 6);
		
		//buttonA.whenPressed(new AutoStack());
		//buttonB.whenPressed(new LiftDown());
		//buttonX.whenPressed(new HoldTote());
		//buttonY.whenPressed(new MoveUpToY(1500));
		

		//functions independent of auto/manual
		if(driverController.getRawAxis(2) > .5){
			new DropSequence();
		}
		
		driverButtonRB.whenPressed(new LiftReset());
		
		if(robotModeLatch.update(operatorController.getRawButton(6))){
			if(Robot.statesObj.getRobotMode() == States.robotMode.AUTOSTACK){
				Robot.statesObj.setRobotModeManual();
				System.out.println("now in manual mode");
			}else{
				Robot.statesObj.setRobotModeAutoStack();
				System.out.println("now in auto mode");
			}
		}
		
		/*
		 * AUTOSTACK COMMANDS!
		 */
		
		if(Robot.statesObj.getRobotMode() == States.robotMode.AUTOSTACK){
			operatorButtonA.whenPressed(new OpACommand());
			if(Robot.statesObj.getLiftPosition() == States.liftPosition.HOLDING_STATE){
				operatorButtonY.whenPressed(new ExtendArms());
			}
			//this comparitor is long af so fix it maybe
			if(Robot.statesObj.getLiftPosition() == States.liftPosition.HOLDING_STATE && Robot.statesObj.getExtendState() == States.extendState.ARMS_EXTENDED){
				operatorButtonLB.whenPressed(new DropSequence());
			}
		}
		
		
		
		/*
		 * MANUAL COMMANDS!
		 */
		
		if(Robot.statesObj.getRobotMode() == States.robotMode.MANUAL){
			if(armsClampLatch.update(operatorController.getRawButton(3))){
				manualGroup.addParallel(new ArmsControl("flipClamp"));
			}if(armsExtendLatch.update(operatorController.getRawButton(4))){
				manualGroup.addParallel(new ArmsControl("extendFlip"));
			}if(Math.abs(operatorController.getRawAxis(1)) < Constants.operatorLS_Y_Deadzone){
				manualGroup.addParallel(new ManualMove(operatorController.getRawAxis(1)));
			}
		}
		
		
		
		
	}
	
}

