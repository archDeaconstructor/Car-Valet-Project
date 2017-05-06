package maintenanceStaffGUI;

import java.util.Scanner;
import java.util.regex.Pattern;

public class UserInformationForParking {
/*
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		String input = sc.next();
		
		System.out.print(returnString(input));
		
	}*/
	String first, last;
	public String returnString(String username){
		
				String[] piece1 = username.split(Pattern.quote("^"));
				
				String cardNumber = piece1[0];
				cardNumber = cardNumber.substring(2, 17);
				
				String FullName	  = piece1[1];
				String[] piece2 = FullName.split(Pattern.quote("/"));
				first  = piece2[0];
				last = piece2[1];
				
				String Value = cardNumber + " - " + first+" "+last;
				
				//charge with card number
				//save fullname into members?
		return Value;
	}
	
}