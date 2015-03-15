package project;

import java.io.*;
import java.math.*;
import java.security.MessageDigest;
import java.util.Scanner;

public class DigitalSignature 
{

	@SuppressWarnings({ "resource", "unused" })
	public static void Send() 
	{
		BigInteger e, n;
		
		try {
			ObjectInputStream pub = new ObjectInputStream(new FileInputStream("pubkey.rsa"));
			
	        	// read and print what we wrote before
	        	e = (BigInteger) pub.readObject();
	        	n = (BigInteger) pub.readObject();
	        
	        	//Needs to be changed to User Input
	        	Scanner in = new Scanner(System.in);
	        
	        	System.out.println("Please enter the file name for the Plaintext (test.txt):");
	        	String file_name = in.next();
	        
	        	BufferedReader br = new BufferedReader(new FileReader(file_name));
	        
	        	StringBuilder sb = new StringBuilder();
	        	String line = br.readLine();

	        	while (line != null) {
	            		sb.append(line);
	            		sb.append("\n");
	            		line = br.readLine();
	        	}
	        
	        	String text = sb.toString();
	       	 
	        	MessageDigest m = MessageDigest.getInstance("MD5");
	       	 
	        	byte [] b = text.getBytes();
	       	 
	        	m.update(b);
	       	 
	        	byte [] digest = m.digest();
	       	 
	        	BigInteger encrypt = new BigInteger(digest);
	        	BigInteger encrypted = encrypt.modPow(e, n);
	       	 
	        	byte [] encryptedM = encrypted.toByteArray();
	       	 
	        	String outputFile = file_name + ".signed";
	       	 
	        	FileOutputStream newT = new FileOutputStream(outputFile);
				
			ObjectOutputStream newText = new ObjectOutputStream(newT);
				
			newText.writeObject(encrypted);
			newText.writeObject(text);
			newText.close();
				
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			return;
		}
		System.out.println("File successfully created/changed.");
	}
	
	@SuppressWarnings({ "resource" })
	public static void Receive() 
	{
		BigInteger d, n, encrypted;
		
		try {
			ObjectInputStream pub = new ObjectInputStream(new FileInputStream("privkey.rsa"));
			
	        	// read and print what we wrote before
	        	d = (BigInteger) pub.readObject();
	        	n = (BigInteger) pub.readObject();
	       	 
	        	//Needs to be changed to User Input
	        	Scanner in = new Scanner(System.in);

	        	try {
	        		System.out.println("Please enter the file name for the Plaintext (test.txt.signed):");
				String file_name = in.next();
		       	 
		        	ObjectInputStream signed = new ObjectInputStream(new FileInputStream(file_name));
		       	 
		        	encrypted = (BigInteger) signed.readObject();
		        	String plainText = (String) signed.readObject();
		       	 
		        	BigInteger decrypted = encrypted.modPow(d, n);
		        
		        	MessageDigest m = MessageDigest.getInstance("MD5");
		       	 
		        	byte [] b1 = plainText.getBytes();
				byte [] b2 = decrypted.toByteArray();
					
				m.update(b1);
	
				byte [] digest = m.digest();
		       	 
		        	if (MessageDigest.isEqual(digest, b2))
		        	{
						System.out.println("File is not corrupted.");
		        	}
		        	else
		        	{
		        		System.out.println("File is Corrupted.");
		        	}
	        	} catch ( IOException error ) {
	        		System.out.println("File is Corrupted or nonexistant.");
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
