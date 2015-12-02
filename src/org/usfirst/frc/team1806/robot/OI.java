package org.usfirst.frc.team1806.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team1806.robot.States;
import org.usfirst.frc.team1806.robot.commands.DrivetrainCommands.Turn90;
import org.usfirst.frc.team1806.robot.commands.IntakeCommands.IntakeRun;
import org.usfirst.frc.team1806.robot.commands.IntakeCommands.IntakeStop;
import org.usfirst.frc.team1806.robot.commands.canSequenceCommands.CanPickupSequence;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.DropSequence;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.ExtendArms;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.LiftReset;


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
	
	private double manualLiftPower;
	private double LTrigger;
	private double RTrigger;
	
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
	JoystickButton operatorButtonRS = new JoystickButton(operatorController, 10);

	
	public OI(){
		
		//buttons that are always listened for
		//basically the dumbest thing ever
		
		operatorButtonRS.whenPressed(new Turn90(Robot.drivetrain.navx.getAngle() + 90));
		
	}
	
	public void update(){

		//functions independent of auto/manual
		LTrigger = driverController.getRawAxis(2);
		RTrigger = driverController.getRawAxis(3);
		

		/*
		 * INTAKE COMMANDS! 
		 */
		
		if(Robot.statesObj.driverIntakeControlTracker == States.driverIntakeControl.DRIVER && Robot.statesObj.liftPositionTracker != States.liftPosition.HOLDING_STATE){
			if(LTrigger > 0){
				new IntakeRun(LTrigger).start();
			}else if(RTrigger > 0){
				new IntakeRun(-RTrigger).start();
			}else{
				new IntakeStop().start();
			}
		}
		

		/*
		 * MANUAL COMMANDS!
		 */
		
		if(robotModeLatch.update(operatorController.getRawButton(6))){
			if(Robot.statesObj.robotModeTracker == States.robotMode.AUTOSTACK){
				Robot.lift.disable();
				Robot.statesObj.robotModeTracker = States.robotMode.MANUAL;
				System.out.println("now in manual mode");
			}else{
					new LiftReset().start();
					Robot.statesObj.robotModeTracker = States.robotMode.AUTOSTACK;
					System.out.println("now in auto mode");
				
			}
		}
		
		
		/*
		 * AUTOSTACK COMMANDS!
		 */
		
		if(Robot.statesObj.robotModeTracker == States.robotMode.AUTOSTACK){
			
			if(Robot.statesObj.liftPositionTracker == States.liftPosition.HOLDING_STATE && (operatorController.getRawButton(1) || driverController.getRawButton(6))){
				Robot.statesObj.elevatorCommandTracker = States.elevatorCommand.MOVETONEXT;
			}else if(operatorController.getRawButton(4) && Robot.statesObj.liftPositionTracker == States.liftPosition.HOLDING_STATE){
					Robot.statesObj.driverIntakeControlTracker = States.driverIntakeControl.AUTOMATIC;
					new ExtendArms().start();
					new IntakeRun(-.25).start();
			}
			
			//Very long comparator :-(
			if(Robot.statesObj.liftPositionTracker == States.liftPosition.HOLDING_STATE && Robot.statesObj.extendStateTracker == States.extendState.ARMS_EXTENDED){
				operatorButtonLB.whenPressed(new DropSequence());
			}
		}
		
		/*
		 * RESET
		 */
		
		if(driverController.getRawButton(5)){
			if(Robot.lift.getBottomLimit()){
				new LiftReset().start();
				Robot.lift.openArms();
			}else{
				new LiftReset().start();
			}
		}
		
		/*
		 * MANUAL COMMANDS!
		 */
		
		if(Robot.statesObj.robotModeTracker == States.robotMode.MANUAL){
			

			
			manualLiftPower = -operatorController.getRawAxis(1);
			//System.out.println("is safe? " + Robot.lift.isSafe(manualLiftPower));
			
			if(armsClampLatch.update(operatorController.getRawButton(4))){
				if(Robot.statesObj.extendStateTracker == States.extendState.ARMS_EXTENDED){
					Robot.lift.retractArms();
				}else{
					Robot.lift.extendArms();
				}
				
			}if(armsExtendLatch.update(operatorController.getRawButton(3))){
				if(Robot.statesObj.clampStateTracker == States.clampState.ARMS_CLAMPED){
					Robot.lift.openArms();
				}else{
					Robot.lift.closeArms();
				}
				
			}if(Math.abs(manualLiftPower) > Constants.operatorLS_Y_Deadzone && Robot.lift.isSafe(manualLiftPower)){
				Robot.lift.manualMove(manualLiftPower);
			}else{
				Robot.lift.stop();
			}
		}
		
		
		/*
		 * CAN SEQUENCE COMMANDS!
		 */
		
		//if you're pressing A, AND you're in autostack, AND you're zeroed... start can sequence
		if(driverController.getRawButton(1) && Robot.statesObj.robotModeTracker == States.robotMode.AUTOSTACK && Robot.statesObj.liftPositionTracker == States.liftPosition.ZEROED){
			Robot.statesObj.robotModeTracker = States.robotMode.CANSEQUENCE;
			Robot.statesObj.liftPositionTracker = States.liftPosition.OTHER;
			new CanPickupSequence().start();
		}
		
		if(Robot.statesObj.liftPositionTracker == States.liftPosition.OTHER){
			if(driverController.getRawButton(1) && Robot.lift.getLiftEncoder() > 75){
				//Prevents double pressing at the start of the sequence.
				Robot.statesObj.canSequenceStateTracker = States.canSequenceState.MOVETONEXT;
			}
		}
		
		//NEED TO ADD DROPSEQUENCE

		
		if(operatorController.getRawButton(5)){
			new DropSequence().start();
		}
	}
}
	


