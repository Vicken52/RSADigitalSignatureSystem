//package project;

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ChangeByte {

	@SuppressWarnings("resource")
	public static void Change(){
	
		//Prompt user for file name
 		Scanner input = new Scanner( System.in );
		System.out.println("Please enter the file name (test.txt.signed):");
    		String fileName = input.next();

		//read file 
		byte[] buffer = null;
		try{
			File file = new File( fileName );
			int length = (int) file.length();
			buffer  = new byte[length];
 			FileInputStream inputStream = new FileInputStream(file);
			inputStream.read( buffer );
			inputStream.close();
		}//try
		catch(IOException error){
			error.printStackTrace();
			return;
		}//catch

		//get, from user, which byte index to change
		System.out.println("Enter which byte to change, numbers "
				+ "starting at 0 and ending at " 
				+ buffer.length + " .");

		int index = input.nextInt();
		input.nextLine();

		//change byte specified
		for ( int i = 0; i < buffer.length; i++ )
		{
			if ( i == index )
			{
				Random random = new Random();
				//TODO: maybe delete this println
				System.out.println( "byte before changes: " + buffer[i] );
				buffer[i] = (byte) ( random.nextInt( 256 ) - 128 );
				
				//TODO: maybe delete this println
				System.out.println( "byte after changes: " + buffer[i] );
			}//if
		}//for
		
		//write back into the same file
		try{
			FileOutputStream outputStream = new FileOutputStream(fileName);
			outputStream.write( buffer );	
			outputStream.close();
		}//try
		catch( IOException error ){
			error.printStackTrace();
			return;
		}//catch
		
	}//change()

}
