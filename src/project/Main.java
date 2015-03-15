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
			System.out.println("\n1. Generate Keys");
			System.out.println("2. \"Send\" file to Encrypt");
			System.out.println("3. \"Receive\" File to Decrypt");
			System.out.println("4. Change a byte in a file");
			System.out.println("5. Close Program");
			int choice = scanner.nextInt();
		
			switch(choice)
			{
			case 1:
				KeyGen.Generate();
				break;
			case 2:
				DigitalSignature.Send();
				break;
			case 3:
				DigitalSignature.Receive();
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
