/* Key Generation of the RSA System
 * 
 * -Pick p and q to be random primes of some specified
 * 		length using the appropriate BigInteger constructor
 * 		for Java.
 * -Calculate n = p x q
 * -Calculate (n) = (p-1)x(q-1)
 * -Pick e to be a random prime between 1 and (n), such that
 * 		gcd(e, (n)) = .e should be similar in (bit) length to
 * 		p and q, but does not have to be the same length.
 * -Calculate d = e^-1 mod (n):
 * 		In BigInteger the method used for this purpose is
 * 		public BigInteger modInverse(BigInteger m)
 * 
 * When you execute this program, it should generate new public 
 * 		and private keys for your RSA cryptosystem, where p, q 
 * 		and e as defined above are all 512-bit integers and n  
 * 		should be ~1024 bits. Your program should output all 
 * 		three values e, d and n to the console as it generates 
 * 		them.  The values e and n should also be saved to a file 
 * 		called "pubkey.rsa" and the values d and n should be 
 * 		saved to a file "privkey.rsa".  To allow for nice access 
 * 		of these files, you MUST output and input these keys to 
 * 		and from the files using a Java ObjectOutputStream and 
 * 		ObjectInputStream.
 * */


//package project;

import java.math.*;
import java.util.*;
import java.io.*;

public class KeyGen 
{
	public static void Generate()
	{
		final BigInteger one = new BigInteger("1");
		final Random random = new Random();
		BigInteger p, q, n, phi, e, d;
		
		p = new BigInteger(512, random);
		q = new BigInteger(512, random);
		
		n = p.multiply(q);
		phi = p.subtract(one).multiply(q.subtract(one));
		e = new BigInteger(512, random);
		
		while(true)
		{
			if(e.gcd(phi).equals(one))
			{
				d = e.modInverse(phi);
				break;
			}
			else
			{
				e = new BigInteger(512, random);
			}
		}
		
		System.out.println("e = " + e);
		System.out.println("d = " + d);
		System.out.println("n = " + n);
		
		try 
		{
			FileOutputStream pub = new FileOutputStream("pubkey.rsa");
			FileOutputStream priv = new FileOutputStream("privkey.rsa");
			
			ObjectOutputStream pubkey = new ObjectOutputStream(pub);
			ObjectOutputStream privkey = new ObjectOutputStream(priv);
			
			pubkey.writeObject(e);
			pubkey.writeObject(n);
			pubkey.close();
			
			privkey.writeObject(d);
			privkey.writeObject(n);
			privkey.close();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}