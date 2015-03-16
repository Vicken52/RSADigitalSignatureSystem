package project;

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.util.Random;
import java.math.BigInteger;

public class ChangeByte {

	@SuppressWarnings("resource")
	public static void Change(){
	
		//Prompt user for file name
 		Scanner input = new Scanner( System.in );
		System.out.println("Please enter the file name (test.txt.signed):");
    		String fileName = input.next();

		//read file 
		byte[] buffer = null;
		BigInteger encrypted;
		BigInteger unencrypted;
		byte[] ciphered;
		String text;
		try{
			File file = new File( fileName );
			int length = (int) file.length();
			buffer  = new byte[length];
 			ObjectInputStream inputStream = new ObjectInputStream( new FileInputStream( file ) );
			encrypted = (BigInteger) inputStream.readObject();
			text = (String) inputStream.readObject();	
			
			ciphered = encrypted.toByteArray();

			inputStream.close();
		}//try
		catch(IOException error){
			error.printStackTrace();
			return;
		}//catch
		catch( ClassNotFoundException error){
			error.printStackTrace();
			return;
		}//catch

		//get, from user, which byte index to change
		System.out.println("Enter which byte to change, numbers "
				+ "starting at 0 and ending at " 
				+ ciphered.length + " .");

		int index = input.nextInt();
		input.nextLine();

		//change byte specified
		for ( int i = 0; i < ciphered.length; i++ )
		{
			if ( i == index )
			{
				Random random = new Random();
				System.out.println( "byte before changes: " + ciphered[i] );
				byte randomByte;
				do{
					randomByte = (byte) ( random.nextInt( 256 ) - 128 );
				}
				while( ciphered[i] == randomByte); 
				ciphered[i] = randomByte;
				
				System.out.println( "byte after changes: " + ciphered[i] );
			}//if
		}//for
		
		//write back into the same file
		try{
			ObjectOutputStream outputStream = new ObjectOutputStream( new FileOutputStream( fileName ) );
			encrypted = new BigInteger(ciphered);
			
			outputStream.writeObject(encrypted);
			outputStream.writeObject(text);	
			outputStream.close();
		}//try
		catch( IOException error ){
			error.printStackTrace();
			return;
		}//catch
		
	}//change()

}
