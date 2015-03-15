package project;

import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.lang.ClassNotFoundException;

public class TestDigitalSignature{
  public static void main( String[] args ){


    try {
      ObjectInputStream pub = new ObjectInputStream( 
                                       new FileInputStream("../../pubkey.rsa"));
      // read and print what we wrote before
      BigInteger e = (BigInteger) pub.readObject();
      BigInteger n = (BigInteger) pub.readObject();

      
      ObjectInputStream pub2 = new ObjectInputStream(
                                      new FileInputStream("../../privkey.rsa"));
      // read and print what we wrote before
      BigInteger d = (BigInteger) pub2.readObject();

/**
      System.out.println("e: " + e);
      System.out.println("n: " + n);
      System.out.println("d: " + d);
*/
      BigInteger p = BigInteger.valueOf( 25 );        
      
      BigInteger encrypted = p.modPow( e, n );
      BigInteger unencrypted = encrypted.modPow( d, n );

      if( p.equals( unencrypted ) )
        System.out.println( "they equal" );
      else
        System.out.println( "they aint" );
        	

      pub.close();
      pub2.close();

    }//try
    catch( IOException error ){
	System.out.println("IO exception");
    }//
    catch( ClassNotFoundException error ){
	System.out.println("not found1");
    }//catch

  }//main
}//class
