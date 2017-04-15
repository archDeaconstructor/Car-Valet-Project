package carParking;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class UserInformationForParking {

	public static void main(String[] args) {
		//Scanner
		Scanner sc = new Scanner(System.in);
		int cardnumber =0;
		String username = new String();
		System.out.print("Please enter your card \n");
		
		//System.out.print(splitString());
		
		String test = sc.next();
		String[] piece1 = test.split(Pattern.quote("^"));
		
		String cardNumber = piece1[0];
		cardNumber = cardNumber.substring(2, 17);
		
		String FullName	  = piece1[1];
		String[] piece2 = FullName.split(Pattern.quote("/"));
		String firstName  = piece2[0];
		String lastName   = piece2[1];
		
		System.out.print("Thank you "+firstName+" "+lastName+"\nFor the sake of Demonstration "
				+ "your card number is "+cardNumber);
		
		timeStaying();
		
	}

	private static String splitString(){
		return "Hello World";
	}
	
	private static int timeStaying(){
		System.out.print("");
		return 15;
	}
}
