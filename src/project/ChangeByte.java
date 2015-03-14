package project;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.util.Scanner;

public class ChangeByte 
{
	@SuppressWarnings({ "resource", "unused" })
	public static void Change() 
	{
		try {
	        Scanner in = new Scanner(System.in);
	        
	        System.out.println("Please enter the file name to Change Byte (test.txt.signed):");
			String file_name = in.next();
	        
	        ObjectInputStream signed = new ObjectInputStream(new FileInputStream(file_name));
	        
	        BigInteger encrypted = (BigInteger) signed.readObject();
	        String plaintext = (String) signed.readObject();
	        
	        byte [] b1 = encrypted.toByteArray();
	        byte [] b2 = plaintext.getBytes();
			
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}

}
