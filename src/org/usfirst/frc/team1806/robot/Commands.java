package org.usfirst.frc.team1806.robot;

public class Commands {

	public enum opACommands{
		ON, OFF
	}
	
	public enum resetCommand{
		ON, OFF
	}
	
	public opACommands opACommandsTracker = opACommands.OFF;
	public resetCommand resetCommandTracker = resetCommand.OFF;
	
	public void reset(){
		opACommandsTracker = opACommands.OFF;
		resetCommandTracker = resetCommand.OFF;

	}
	
}
