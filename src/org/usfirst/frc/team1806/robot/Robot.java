
package org.usfirst.frc.team1806.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import org.usfirst.frc.team1806.robot.commands.HoldTote;
import org.usfirst.frc.team1806.robot.commands.LiftDown;
import org.usfirst.frc.team1806.robot.commands.StateMachine;
import org.usfirst.frc.team1806.robot.commands.elevatorCommands.AutoStack;
import org.usfirst.frc.team1806.robot.subsystems.Elevator;


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
	public static final Elevator lift = new Elevator(0.003 , 0.0001, 0.0);

    Command autonomousCommand;
    Command StateMachine;
    public static States statesObj = new States();
    
    //variables!!!!!!!!!!!!!!
    
    //drive

    
    Compressor c = new Compressor(0);
    
    
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	//new StateMachine().start();
    	oi = new OI();

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
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        //new StateMachine().start();

        //COMPRESSOR CODE HERE!
        c.setClosedLoopControl(true);
        
        System.out.println("teleop initialized fam");
        
                

    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){

    }

    
   
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        
        dt.arcadeDrive(dc.getLeftJoyY(), -dc.getRightJoyX());
        
        //add listener class? (For optical sensor, limit switches, etc.
        if(lift.getOpticalSensor() && statesObj.getAutoStackPos() == States.autoStackPosition.WAITING){
        	new AutoStack().start();
        }
        writeToDashboard();
        
        
        
        
    }
    
    public void writeToDashboard(){
    	SmartDashboard.putNumber("Lift Encoder Value", lift.getLiftEncoder());
        SmartDashboard.putNumber("Lift Power",lift.getLiftPowerPercentage());
        SmartDashboard.putBoolean("Optical Sensor",lift.getOpticalSensor());
        SmartDashboard.putBoolean("Top Limit",lift.getTopLimit());
        SmartDashboard.putBoolean("Bottom Limit",lift.getBottomLimit());
        SmartDashboard.putString("2StageState", Robot.statesObj.getSecondStageState().toString());
        SmartDashboard.putString("Lift State", Robot.statesObj.getLiftState().toString());
        SmartDashboard.putString("Clamp State", Robot.statesObj.getClampState().toString());
        SmartDashboard.putString("Extend State", Robot.statesObj.getExtendState().toString());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
