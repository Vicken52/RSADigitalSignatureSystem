/* RSA Algorithm for a primitive digital signature system.
 * 
 * Details: Your goal is for the receiver of a message to be able
 * 		to discover any tampering with the message sent by the 
 * 		sender, and, assuming that the sender's private key has not
 * 		been compromised, to verify that the sender is indeed the 
 * 		one who sent the message.
 * 
 * Sender: The Sender of the message will create the message itself
 * 		in plaintext. This can be any text message stored in any
 * 		file on the sender's computer. Before "sending" the message,
 * 		the sender will do the following.
 * 
 * 1) Process the plaintext file in the following way:
 * 		a) Read in the file byte by byte, adding each to an MD5
 * 			instance of the MessageDigest. Once you have added the
 * 			complete file to the MessageDigest, obtain the 128 bit
 * 			(16 byte) digest array value from it.
 *		b) You now have a digest array which is ready to be signed.
 *			Using the sign/magnitude constructor for BigInteger in 
 *			Java, convert this array into a single BigInteger. Next 
 *			sign the BigInteger using a proper key that you generated 
 *			in Part 1 of the project using the RSA algorithm. To make 
 *			verification easier, prepend this value to your plaintext 
 *			file (i.e. put it in the front of the file). You can do 
 *			this using an ObjectOutputStream first write your BigInteger
 *			signature value, then write the rest of your file byte by byte. 
 *			Your message is now ready to "send".  However, for the purposes 
 *			of this project you will simply leave it in the file. If your 
 *			original file was <filename>.<ext>, call this file 
 *			<filename>.<ext>.signed
 *
 * Receiver:
 * 
 * 1)	a) Upon "receiving" the message the receiver first reads the 
 * 			BigInteger value (using an ObjectInputStream), then "encrypts" 
 * 			it using the proper key generated in Part 1 of the assignment 
 * 			using the RSA algorithm. The resulting BigInteger is then 
 * 			converted back to an array of bytes using the toByteArray() 
 * 			method, with the result being the digest array that you will 
 * 			now use to check to see if the message has been tampered with.
 * 		b) Read the remaining bytes of the file, adding them to a new 
 * 			MessageDigest object. Once you have added all of the bytes, 
 * 			obtain the message digest for the received file. If this 
 * 			exactly matches the transmitted message digest, the message has 
 * 			not been tampered with  otherwise it has been tampered with. 
 * 			The receiver should output a message indicating whether or not 
 * 			the received file was valid.
 * */

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
		}
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
	        
	        System.out.println("Please enter the file name for the Plaintext (test.txt.signed):");
			String file_name = in.next();
	        
	        ObjectInputStream signed = new ObjectInputStream(new FileInputStream(file_name));
	        
	        encrypted = (BigInteger) signed.readObject();
	        String plaintext = (String) signed.readObject();
	        
	        BigInteger decrypted = encrypted.modPow(d, n);
	        
	        MessageDigest m1 = MessageDigest.getInstance("MD5");
	        MessageDigest m2 = MessageDigest.getInstance("MD5");
	        
	        byte [] b1 = plaintext.getBytes();
			byte [] b2 = decrypted.toByteArray();
			
			m1.update(b1);
			m2.update(b2);

			byte [] digest1 = m1.digest();
			byte [] digest2 = m2.digest();
	        
	        if (MessageDigest.isEqual(digest1, digest2))
	        {
				System.out.println("Equal");
	        }
	        else
	        {
	        	System.out.println("Corrupted");
	        }
			
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}

}
