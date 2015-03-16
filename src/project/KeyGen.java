package project;

import java.math.*;
import java.security.SecureRandom;
import java.io.*;

public class KeyGen 
{
	public static void Generate()
	{
		final BigInteger one = BigInteger.ONE;
		final SecureRandom random = new SecureRandom();
		BigInteger p, q, n, phi, e, d;
		
		do{
			p = new BigInteger(512, random);
		}//do
		while(!p.isProbablePrime(20));

		do{
			q = new BigInteger(512, random);
		}//do
		while(!q.isProbablePrime(20));
		
		n = p.multiply(q);
		phi = (p.subtract(one)).multiply(q.subtract(one));
		e = new BigInteger(512, random);
		
		while(true)
		{
	        //makes sure 1 < e < n & e is prime of phi	
			if(e.gcd(phi).equals(one) && e.compareTo(one) > 0 && e.compareTo(n) < 0 )
			{
				d = e.modInverse(phi);
				break;
			}//if
			else
			{
				e = new BigInteger(512, random);
			}//else
		}//while
		
		System.out.println("e = " + e);
		System.out.println("d = " + d);
		System.out.println("n = " + n);
		
		try{ 
		
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
		}//catch
	}//generate


}
