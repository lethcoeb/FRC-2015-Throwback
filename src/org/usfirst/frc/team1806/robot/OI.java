package org.usfirst.frc.team1806.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team1806.robot.States;
import org.usfirst.frc.team1806.robot.Commands.opACommands;
import org.usfirst.frc.team1806.robot.Commands.resetCommand;
import org.usfirst.frc.team1806.robot.commands.DrivetrainCommands.DriveStraightToDistance;
import org.usfirst.frc.team1806.robot.commands.DrivetrainCommands.StayAtAngle;
import org.usfirst.frc.team1806.robot.commands.DrivetrainCommands.TurnToAngle;
import org.usfirst.frc.team1806.robot.commands.IntakeCommands.IntakeRun;
import org.usfirst.frc.team1806.robot.commands.IntakeCommands.IntakeStop;
import org.usfirst.frc.team1806.robot.commands.canSequenceCommands.CanPickupSequence;
import org.usfirst.frc.team1806.robot.commands.canrighting.CanRightingCommandGroup;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.DropSequence;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.ExtendArms;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.LiftReset;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.LiftZero;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    
	private final Joystick driverController = new Joystick(RobotMap.driverController);
	private final Joystick operatorController = new Joystick(RobotMap.operatorController);
	
	private final XboxController dController = new XboxController(0);
	private final XboxController oController = new XboxController(1);
	
	private Latch armsClampLatch = new Latch();
	private Latch armsExtendLatch = new Latch();
	private Latch robotModeLatch = new Latch();
	private Latch driverDTLatch = new Latch();
	public static boolean driverDTControl = true;
	
	private Latch rsLatch = new Latch();
	
	private double lStickY;
	private double rStickX;
	private double manualLiftPower;
	public double LTrigger;
	public double RTrigger;
	
	public Commands m_commands;
	
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
		//Always
		//ALWAYS!!!!!!!!!!!!!!!!!!!!!
		//basically the dumbest thing ever
		
		operatorButtonX.whenPressed(new TurnToAngle(90, .9));
		operatorButtonRS.whenPressed(new DriveStraightToDistance(48, .75, true));
		
	}
	
	public void update(){

		//functions independent of auto/manual
		LTrigger = driverController.getRawAxis(2);
		RTrigger = driverController.getRawAxis(3);
		

		/*
		 * INTAKE COMMANDS! 
		 */
		
		if(Robot.statesObj.driverIntakeControlTracker == States.driverIntakeControl.DRIVER && (Robot.statesObj.robotModeTracker == States.robotMode.AUTOSTACK && Robot.statesObj.liftPositionTracker != States.liftPosition.HOLDING_STATE) && !Robot.intakeSS.isRunning()){
			if((LTrigger > 0)){
				new IntakeRun(LTrigger).start();
			}else if((RTrigger > 0)){
				new IntakeRun(-RTrigger).start();
			}
		}
		

		/*
		 * MANUAL COMMANDS!
		 */
		
		else if(robotModeLatch.update(operatorController.getRawButton(6))){
			if(Robot.statesObj.robotModeTracker == States.robotMode.AUTOSTACK){
				Robot.elevatorSS.disable();
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
			if(Robot.elevatorSS.getBottomLimit()){
				new LiftReset().start();
				Robot.elevatorSS.openArms();
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
					Robot.elevatorSS.retractArms();
				}else{
					Robot.elevatorSS.extendArms();
				}
				
			}else if(armsExtendLatch.update(operatorController.getRawButton(3))){
				if(Robot.statesObj.clampStateTracker == States.clampState.ARMS_CLAMPED){
					Robot.elevatorSS.openArms();
				}else{
					Robot.elevatorSS.closeArms();
				}
				
			}else if(Math.abs(manualLiftPower) > Constants.operatorLS_Y_Deadzone && Robot.elevatorSS.isSafe(manualLiftPower)){
				Robot.elevatorSS.manualMove(manualLiftPower);
			}else{
				Robot.elevatorSS.stop();
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
			if(driverController.getRawButton(1) && Robot.elevatorSS.getLiftEncoder() > 75){
				//Prevents double pressing at the start of the sequence.
				Robot.statesObj.canSequenceStateTracker = States.canSequenceState.MOVETONEXT;
			}
		}
		
		//NEED TO ADD DROPSEQUENCE

		
		if(operatorController.getRawButton(5)){
			new DropSequence().start();
		}
	}
	
	public void update2(){
		
		lStickY = dController.getLeftJoyY();
		rStickX = dController.getRightJoyX();
		LTrigger = dController.getLeftTrigger();
		RTrigger = dController.getRightTrigger();
		
		
		/*if(driverDTLatch.update(dController.getButtonX())){
			driverDTControl = !driverDTControl;
		}*/
		
		if(dController.getButtonX()){
			if(Robot.statesObj.robotModeTracker == States.robotMode.AUTOSTACK){
				Robot.statesObj.robotModeTracker = States.robotMode.CANRIGHTINGSEQUENCE;
				new CanRightingCommandGroup().start();
			}
			Robot.statesObj.canRightingMoveOnTracker = States.canRightingMoveOn.MOVEON;
		}else{
			Robot.statesObj.canRightingMoveOnTracker = States.canRightingMoveOn.WAITING;
 		}
		
		if(Math.abs(dController.getLeftJoyY()) > Constants.driveStickDeadzone  || Math.abs(dController.getRightJoyX()) > Constants.driveStickDeadzone){
			if(driverDTControl){
				Robot.drivetrainSS.arcadeDrive(lStickY-Constants.driveStickDeadzone, rStickX-Constants.driveStickDeadzone);
			}
		}
		
		if((LTrigger > .1 || RTrigger > .1) && Robot.statesObj.driverIntakeControlTracker == States.driverIntakeControl.DRIVER && !Robot.intakeSS.isRunning()){

			if((LTrigger > .1)){
				new IntakeRun(LTrigger).start();
			}else if(RTrigger > .1){
				new IntakeRun(-RTrigger).start();
			}
			
		}
				
		//RESET COMMANDS
		if(dController.getButtonLB()){
			if(Robot.elevatorSS.getBottomLimit()){
				new LiftReset().start();
				Robot.elevatorSS.openArms();
			}else{
				Robot.statesObj.robotModeTracker = States.robotMode.AUTOSTACK;
				new LiftReset().start();
			}	
			
		//CAN SEQUENCE COMMANDS
		}else if(dController.getButtonA()){
			if(Robot.statesObj.robotModeTracker == States.robotMode.AUTOSTACK && Robot.statesObj.liftPositionTracker == States.liftPosition.ZEROED){
				Robot.statesObj.robotModeTracker = States.robotMode.CANSEQUENCE;
				Robot.statesObj.liftPositionTracker = States.liftPosition.OTHER;
				new CanPickupSequence().start();
			}
			
			else if(Robot.statesObj.liftPositionTracker == States.liftPosition.OTHER && Robot.elevatorSS.getLiftEncoder() > 75){
					//Prevents double pressing at the start of the sequence.
					Robot.statesObj.canSequenceStateTracker = States.canSequenceState.MOVETONEXT;
			}
		}else if(dController.getButtonB()){
			if(Robot.elevatorSS.getBottomLimit()){
				new LiftZero().start();
			}
		}
				
		if(oController.getButtonA() || dController.getButtonRB()){
			if(Robot.statesObj.robotModeTracker == States.robotMode.AUTOSTACK && Robot.statesObj.liftPositionTracker == States.liftPosition.HOLDING_STATE){
				Robot.statesObj.elevatorCommandTracker = States.elevatorCommand.MOVETONEXT;
			}else{
				Robot.statesObj.elevatorCommandTracker = States.elevatorCommand.WAITING;
			}
		}else if(oController.getButtonY()){
			if(Robot.statesObj.robotModeTracker == States.robotMode.AUTOSTACK && Robot.statesObj.liftPositionTracker == States.liftPosition.HOLDING_STATE){
				Robot.statesObj.driverIntakeControlTracker = States.driverIntakeControl.AUTOMATIC;
				new ExtendArms().start();
				new IntakeRun(-.4).start();
			}
			
			//Commented out because it's taken care of in the manual mode else/if.
			
			/*else if(Robot.statesObj.robotModeTracker == States.robotMode.MANUAL){
				if (armsExtendLatch.update(oController.getButtonY())){
					if(Robot.statesObj.extendStateTracker == States.extendState.ARMS_EXTENDED){
						Robot.elevatorSS.retractArms();
					}else{
						Robot.elevatorSS.extendArms();
					}
				}
			}*/
		/*else if(oController.getButtonX()){
			if(armsClampLatch.update(oController.getButtonX()) && Robot.statesObj.robotModeTracker == States.robotMode.MANUAL){
				if(Robot.statesObj.clampStateTracker == States.clampState.ARMS_CLAMPED){
					Robot.elevatorSS.openArms();
				}else{
					Robot.elevatorSS.closeArms();
				}
			}*/
		}else if(robotModeLatch.update(oController.getButtonRB())){
			if(Robot.statesObj.robotModeTracker == States.robotMode.AUTOSTACK){
				Robot.elevatorSS.disable();
				Robot.statesObj.robotModeTracker = States.robotMode.MANUAL;
				System.out.println("now in manual mode");
			}else{
				new LiftReset().start();
				Robot.statesObj.robotModeTracker = States.robotMode.AUTOSTACK;
				System.out.println("now in auto mode");
			}
		}
		
		//Commands for manual mode
		if(Robot.statesObj.robotModeTracker == States.robotMode.MANUAL){
			
			manualLiftPower = -operatorController.getRawAxis(1);
			
			if(armsClampLatch.update(operatorController.getRawButton(4))){
				if(Robot.statesObj.extendStateTracker == States.extendState.ARMS_EXTENDED){
					Robot.elevatorSS.retractArms();
				}else{
					Robot.elevatorSS.extendArms();
				}
				
			}if(armsExtendLatch.update(operatorController.getRawButton(3))){
				if(Robot.statesObj.clampStateTracker == States.clampState.ARMS_CLAMPED){
					Robot.elevatorSS.openArms();
				}else{
					Robot.elevatorSS.closeArms();
				}
				
			}if(Math.abs(manualLiftPower) > Constants.operatorLS_Y_Deadzone && Robot.elevatorSS.isSafe(manualLiftPower)){
				Robot.elevatorSS.manualMove(manualLiftPower);
			}else{
				Robot.elevatorSS.stop();
			}
			
		}
		
	}
	
	public Commands updateCmds(){
		
		if(dController.getButtonLB()){
			m_commands.resetCommandTracker = resetCommand.ON;
		}else{
			m_commands.resetCommandTracker = resetCommand.OFF;
		}
		
		if(dController.getButtonA()){
			m_commands.opACommandsTracker = opACommands.ON;
		}else{
			m_commands.opACommandsTracker = opACommands.OFF;
		}
		
		return m_commands;
		
	}
	
}
	


