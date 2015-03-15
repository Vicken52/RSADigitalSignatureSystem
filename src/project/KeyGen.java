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


package project;

import java.math.*;
import java.util.*;
import java.io.*;

public class KeyGen 
{
	public static void Generate()
	{
		final BigInteger one = BigInteger.ONE;
		final Random random = new Random();
		BigInteger p, q, n, phi, e, d;
		
		do{
			p = new BigInteger( 512, random );
		}//do
		while( !p.isProbablePrime( 20 ) );

		do{
			q = new BigInteger( 512, random );
		}//do
		while( !q.isProbablePrime( 20 ) );
		
		n = p.multiply(q);
		phi = p.subtract(one).multiply(q.subtract(one));
		e = new BigInteger(512, random);
		
		while(true){
	                //makes sure 1 < e < n & e is prime of phi	
			if( e.gcd(phi).equals(one) && e.compareTo( one ) > 0 && e.compareTo( n ) < 0 ){
			
				d = e.modInverse(phi);
				break;
			}//if
			else{
				e = new BigInteger(512, random);
			}//else
		}//while
		
		System.out.println("e = " + e);
		System.out.println("d = " + d);
		System.out.println("n = " + n);

		p = BigInteger.valueOf( 25);

		BigInteger encrypted = p.modPow( e , n );
 		BigInteger unencrypted = encrypted.modPow( d,n);

		
		try{ 
		
			FileOutputStream pub = new FileOutputStream( "pubkey.rsa");
			FileOutputStream priv = new FileOutputStream( "privkey.rsa");
			
			ObjectOutputStream pubkey = new ObjectOutputStream(pub);
			ObjectOutputStream privkey = new ObjectOutputStream( priv);
			
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
