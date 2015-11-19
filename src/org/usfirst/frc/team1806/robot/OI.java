package org.usfirst.frc.team1806.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.usfirst.frc.team1806.robot.States;
import org.usfirst.frc.team1806.robot.commands.GrabAndReset;
import org.usfirst.frc.team1806.robot.commands.HoldTote;
import org.usfirst.frc.team1806.robot.commands.LiftDown;
import org.usfirst.frc.team1806.robot.commands.LiftReset;
import org.usfirst.frc.team1806.robot.commands.LiftUp;
import org.usfirst.frc.team1806.robot.commands.MoveUpToY;
import org.usfirst.frc.team1806.robot.commands.OpACommand;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.AutoStack;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.MoveToTarget;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    
	//private final XboxController driverController = new XboxController(RobotMap.driverController);
	private final Joystick driverController = new Joystick(RobotMap.driverController);
	private final Joystick operatorController = new Joystick(RobotMap.operatorController);
	//private final XboxController driverController = new XboxController(RobotMap.driverController);
	public States statesRequests;
	
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
		
		
		//buttonA.whenPressed(new AutoStack());
		//buttonB.whenPressed(new LiftDown());
		//buttonX.whenPressed(new HoldTote());
		//buttonY.whenPressed(new MoveUpToY(1500));
		driverButtonRB.whenPressed(new LiftReset());

		
		
		if(Robot.statesObj.getRobotMode() == States.robotMode.AUTOSTACK){
			operatorButtonA.whenPressed(new OpACommand());
		}
		
		
		
		
	}
	
}

