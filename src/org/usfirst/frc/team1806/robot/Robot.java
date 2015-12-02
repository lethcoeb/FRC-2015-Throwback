
package org.usfirst.frc.team1806.robot;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1806.robot.commands.canSequenceCommands.PlaceCanOnTote;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.AutoStack;
import org.usfirst.frc.team1806.robot.subsystems.Drivetrain;
import org.usfirst.frc.team1806.robot.subsystems.Elevator;
import org.usfirst.frc.team1806.robot.subsystems.Intake;

import com.kauailabs.navx.frc.AHRS;







/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;
	
	
	public static final XboxController dc = new XboxController(RobotMap.driverController);
	public static final XboxController oc = new XboxController(RobotMap.operatorController);
	
	
	private static final RobotDrive dt = new RobotDrive(RobotMap.leftDriveMotor, RobotMap.rightDriveMotor);
	public static Drivetrain drivetrain;
	public static final Elevator lift = new Elevator(Constants.elevatorP , Constants.elevatorI, Constants.elevatorD);
	public static final Intake in = new Intake();
	
	
	public logData d = new logData();
	Timer t = new Timer();

    Command autonomousCommand;
    public static States statesObj = new States();
    

    Latch autoStack = new Latch();
    Latch creepMode = new Latch();

    Compressor compressor = new Compressor(0);
    
    SendableChooser sc = new SendableChooser();    
    
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	
    	
    	
    	oi = new OI();
    	sc.addObject("Logging = Off", statesObj.dataLogStateTracker = States.dataLogState.OFF);
    	sc.addObject("Logging = On", statesObj.dataLogStateTracker = States.dataLogState.ON);
    	SmartDashboard.putData("Datalogging", sc);
    	
    	drivetrain = new Drivetrain();

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        if (autonomousCommand != null) autonomousCommand.cancel();
        compressor.setClosedLoopControl(true);
        
        System.out.println("teleop initialized fam");
        
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
    	lift.zeroPower();
    	System.out.println("Robot disabled!");
    }

    
   
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        
        dt.arcadeDrive(dc.getLeftJoyY(), -dc.getRightJoyX());
        oi.update();
        if(autoStack.update(((lift.getOpticalSensor() && statesObj.liftPositionTracker == States.liftPosition.ZEROED) || oc.getRawButton(7)) && statesObj.liftPositionTracker == States.liftPosition.ZEROED) && (statesObj.canSequenceStateTracker == States.canSequenceState.WAITING)){
        	
        	new AutoStack().start();
        }else if((Robot.statesObj.canSequenceStateTracker == States.canSequenceState.STACKHEIGHT || Robot.statesObj.canSequenceStateTracker == States.canSequenceState.MOVETONEXT) && lift.getOpticalSensor()){
        	new PlaceCanOnTote().start();
        }
        writeToDashboard();
        
        if(statesObj.dataLogStateTracker == States.dataLogState.ON){
        	d.writeData(String.valueOf(Robot.lift.getLiftEncoder()), String.valueOf(t.get()), String.valueOf(lift.getLiftPowerPercentage()));
        }
        
        System.out.println(Robot.drivetrain.navx.getAngle());
        
        
    }
    
    public void writeToDashboard(){
    	SmartDashboard.putNumber("Lift Encoder Value", lift.getLiftEncoder());
        SmartDashboard.putNumber("Lift Power",lift.getLiftPowerPercentage());
        SmartDashboard.putBoolean("Optical Sensor",lift.getOpticalSensor());
        SmartDashboard.putBoolean("Top Limit",lift.getTopLimit());
        SmartDashboard.putBoolean("Bottom Limit",lift.getBottomLimit());
        
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
