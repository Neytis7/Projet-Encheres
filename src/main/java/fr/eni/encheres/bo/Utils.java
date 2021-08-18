package fr.eni.encheres.bo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	final public static boolean isBlankString(String string) {
		return string == null || string.trim().isEmpty();
	}

	final public static boolean isEmailAdress(String email){
		Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
		Matcher m = p.matcher(email.toUpperCase());
		return m.matches();
	}
}