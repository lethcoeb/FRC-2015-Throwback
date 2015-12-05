
package org.usfirst.frc.team1806.robot;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1806.robot.commands.autonomous.PickupCanAndDriveBackwards;
import org.usfirst.frc.team1806.robot.commands.canSequenceCommands.PlaceCanOnTote;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.AutoStack;
import org.usfirst.frc.team1806.robot.subsystems.DrivetrainSS;
import org.usfirst.frc.team1806.robot.subsystems.Elevator;
import org.usfirst.frc.team1806.robot.subsystems.Intake;


public class Robot extends IterativeRobot {

	
	public static OI oi;
	
	
	public static final XboxController dc = new XboxController(RobotMap.driverController);
	public static final XboxController oc = new XboxController(RobotMap.operatorController);
	
	public static DrivetrainSS drivetrainSS;
	public static Elevator elevatorSS;
	public static Intake intakeSS;
	

	Preferences prefs;
	
	
	public logData d = new logData();
	Timer t = new Timer();

    Command autonomousCommand;
    public static States statesObj = new States();
    

    Latch autoStack = new Latch();
    Latch creepMode = new Latch();

    Compressor compressor = new Compressor(0);
    
    SendableChooser dataloggingSC = new SendableChooser();    
    
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	
    	prefs = Preferences.getInstance();
    	
    	prefs.putDouble("DrivetrainDriveP", Constants.drivetrainDriveP);
    	prefs.putDouble("DrivetrainDriveI", Constants.drivetrainDriveI);
    	prefs.putDouble("DrivetrainDriveD", Constants.drivetrainDriveD);
    	
    	prefs.putDouble("DrivetrainRotateP", Constants.drivetrainRotateP);
    	prefs.putDouble("DrivetrainRotateI", Constants.drivetrainRotateI);
    	prefs.putDouble("DrivetrainRotateD", Constants.drivetrainRotateD);
    	
    	prefs.putDouble("ElevatorP", Constants.elevatorP);
    	prefs.putDouble("ElevatorI", Constants.elevatorI);
    	prefs.putDouble("ElevatorD", Constants.elevatorD);
    	
    	prefs.putDouble("secondStageHeight", Constants.secondStageHeight);
    	
    	drivetrainSS = new DrivetrainSS();
    	elevatorSS = new Elevator(Constants.elevatorP , Constants.elevatorI, Constants.elevatorD);
    	intakeSS = new Intake();
    	
    	
    	    	
    	oi = new OI();
    	dataloggingSC.addObject("Logging = Off", statesObj.dataLogStateTracker = States.dataLogState.OFF);
    	dataloggingSC.addObject("Logging = On", statesObj.dataLogStateTracker = States.dataLogState.ON);
    	SmartDashboard.putData("Datalogging", dataloggingSC);
    	
    	System.out.println("Robot initialized!");
    	
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
        autonomousCommand = new PickupCanAndDriveBackwards();
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
    	//TODO: Make this a class.
    	prefs.putDouble("DrivetrainDriveP", Constants.drivetrainDriveP);
    	prefs.putDouble("DrivetrainDriveI", Constants.drivetrainDriveI);
    	prefs.putDouble("DrivetrainDriveD", Constants.drivetrainDriveD);
    	
    	prefs.putDouble("DrivetrainRotateP", Constants.drivetrainRotateP);
    	prefs.putDouble("DrivetrainRotateI", Constants.drivetrainRotateI);
    	prefs.putDouble("DrivetrainRotateD", Constants.drivetrainRotateD);
    	
    	prefs.putDouble("ElevatorP", Constants.elevatorP);
    	prefs.putDouble("ElevatorI", Constants.elevatorI);
    	prefs.putDouble("ElevatorD", Constants.elevatorD);
    	
    	prefs.putDouble("secondStageHeight", Constants.secondStageHeight);
    	
        if (autonomousCommand != null) autonomousCommand.cancel();
        compressor.setClosedLoopControl(true);
        
        System.out.println("Teleop Initialized!");
        
        //only runs new teleop cycle method if datalogState is on when teleop is initialized
        //datalog on/off is a radiobutton set on the java dashboard.
        if(statesObj.dataLogStateTracker == States.dataLogState.ON){
        	d.writeNewTeleopCycle();
        	System.out.println("Datalogging initiated!");
        	t.start();
        }
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){
    	elevatorSS.zeroPower();
    	System.out.println("Robot disabled!");
    }

    
   
    public void teleopPeriodic() {
    	Scheduler.getInstance().run();
    	
    	updateConstants();
    	System.out.println(drivetrainSS.getRightRate());
        
        //drivetrainSS.arcadeDrive(dc.getLeftJoyY(), dc.getRightJoyX());
        
        oi.update2();
        if(autoStack.update(((elevatorSS.getOpticalSensor() && statesObj.liftPositionTracker == States.liftPosition.ZEROED) || oc.getRawButton(7)) && statesObj.liftPositionTracker == States.liftPosition.ZEROED) && (statesObj.canSequenceStateTracker == States.canSequenceState.WAITING)){
        	
        	new AutoStack().start();
        }else if((Robot.statesObj.canSequenceStateTracker == States.canSequenceState.STACKHEIGHT || Robot.statesObj.canSequenceStateTracker == States.canSequenceState.MOVETONEXT) && elevatorSS.getOpticalSensor()){
        	new PlaceCanOnTote().start();
        }
        //writeToDashboard();
        
        if(statesObj.dataLogStateTracker == States.dataLogState.ON){
        	d.writeData(String.valueOf(Robot.elevatorSS.getLiftEncoder()), String.valueOf(t.get()), String.valueOf(elevatorSS.getLiftPowerPercentage()));
        }
        
        
        
    }
    
    public void updateConstants(){
    	Constants.drivetrainDriveP = prefs.getDouble("DrivetrainDriveP", Constants.drivetrainDriveP);
    	Constants.drivetrainDriveI = prefs.getDouble("DrivetrainDriveI", Constants.drivetrainDriveI);
    	Constants.drivetrainDriveD = prefs.getDouble("DrivetrainDriveD", Constants.drivetrainDriveD);
    	
    	Constants.drivetrainRotateP = prefs.getDouble("DrivetrainRotateP", Constants.drivetrainRotateP);
    	Constants.drivetrainRotateI = prefs.getDouble("DrivetrainRotateI", Constants.drivetrainRotateI);
    	Constants.drivetrainRotateD = prefs.getDouble("DrivetrainRotateD", Constants.drivetrainRotateD);
    	
    	Constants.elevatorP = prefs.getDouble("ElevatorP", Constants.elevatorP);
    	Constants.elevatorI = prefs.getDouble("ElevatorI", Constants.elevatorI);
    	Constants.elevatorD = prefs.getDouble("ElevatorD", Constants.elevatorD);
    	
    	Constants.secondStageHeight = prefs.getDouble("secondStageHeight", Constants.secondStageHeight);
    }
    
    public void writeToDashboard(){
    	SmartDashboard.putNumber("Lift Encoder Value", elevatorSS.getLiftEncoder());
        SmartDashboard.putNumber("Lift Power",elevatorSS.getLiftPowerPercentage());
        SmartDashboard.putBoolean("Optical Sensor",elevatorSS.getOpticalSensor());
        SmartDashboard.putBoolean("Top Limit",elevatorSS.getTopLimit());
        SmartDashboard.putBoolean("Bottom Limit",elevatorSS.getBottomLimit());
        
        SmartDashboard.putNumber("Totes held", Robot.statesObj.totesHeld);
        
        SmartDashboard.putString("LiftPosition", statesObj.liftPositionTracker.toString());
        SmartDashboard.putString("RobotMode", statesObj.robotModeTracker.toString());
        SmartDashboard.putString("ElevatorCommand", statesObj.elevatorCommandTracker.toString());
        SmartDashboard.putString("autoStackPosition", statesObj.autoStackPositionTracker.toString());
        SmartDashboard.putString("canSequenceState", statesObj.canSequenceStateTracker.toString());
        
        SmartDashboard.putData(Scheduler.getInstance());

    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
