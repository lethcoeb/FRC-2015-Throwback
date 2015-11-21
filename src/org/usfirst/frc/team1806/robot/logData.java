package org.usfirst.frc.team1806.robot;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;


public class logData {
	
	Calendar c = Calendar.getInstance();
	String fileName = "/U/" + String.valueOf(c.getTime() + ".csv");
	FileWriter fileWriter;
	BufferedWriter bufferedWriter;
	
	public logData(){
		//replace spaces and colons to make filename acceptable
		fileName = fileName.replaceAll("\\s", "_");
		fileName = fileName.replace(":", "-");
	}
		
	
	
	public void writeData(String data, String time, String power){
	

		


        try {
        	
        	 FileWriter fileWriter = new FileWriter(fileName, true);
        	 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        		

        		
        	
            // Assume default encoding.


            // Always wrap FileWriter in BufferedWriter.


            // Note that write() does not automatically
            // append a newline character.
            
            //bufferedWriter.newLine();
            
            bufferedWriter.write(data +  "," + time + "," + power + "\r\n");

            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                "failed to write to file '"
                + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }	
	}
	
	public void writeNewTeleopCycle(){
		
		try {
        	
       	 FileWriter fileWriter = new FileWriter(fileName, true);
       	 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
       		

       		
       	
           // Assume default encoding.


           // Always wrap FileWriter in BufferedWriter.


           // Note that write() does not automatically
           // append a newline character.
           
           //bufferedWriter.newLine();
           
           bufferedWriter.write("\r\n" + "New Teleop Cycle Started" + "\r\n");
           bufferedWriter.write("P: " + Constants.P + "," + "I: " + Constants.I + "," 
        		   + "D: " + Constants.D + "\r\n");
           bufferedWriter.write(Constants.secondStagePIDEngage + " = PID Engage Height" + "," 
        		   + Constants.secondStageHeight + " = PID Target Height" + "\r\n" + "\r\n");
           bufferedWriter.write("Height,Time,Power" + "\r\n");

           // Always close files.
           bufferedWriter.close();
       }
       catch(IOException ex) {
           System.out.println(
               "failed to write to file '"
               + fileName + "'");
           // Or we could just do this:
           // ex.printStackTrace();
       }	
		
	}
	
	
}
