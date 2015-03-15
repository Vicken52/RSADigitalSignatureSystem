/* Have a menu-driven loop that allows the user to "send" or "receive"
 * 		files. "Send" will simply prompt for an input filename and 
 * 		process the file as specified in DigitalSignature.java. "Receive"
 * 		will also prompt for a filename (should be a .signed file which
 * 		had previously been output from a "Send") and process it as
 * 		specified in DigitalSignature.java, outputting whether or not
 * 		the file has been tampered with.
 * */


package project;

import java.util.*;

public class Main 
{
	@SuppressWarnings("resource")
	public static void main(String[] args) 
	{
		Scanner scanner = new Scanner(System.in);
		
		while(true)
		{
			System.out.println("1. Send file to Encrypt");
			System.out.println("2. Receive File to Decrypt");
			System.out.println("3. Generate Keys");
			System.out.println("4. Change Byte");
			System.out.println("5. Close Program");
			int choice = scanner.nextInt();
		
			switch(choice)
			{
			case 1:
				DigitalSignature.Send();
				break;
			case 2:
				DigitalSignature.Receive();
				break;
			case 3:
				KeyGen.Generate();
				break;
			case 4:
				ChangeByte.Change();
				break;
			case 5:
				System.exit(0);
				break;
			}
		}

	}

}
