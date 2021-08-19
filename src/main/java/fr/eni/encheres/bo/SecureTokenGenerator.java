package fr.eni.encheres.bo;

import java.security.SecureRandom;

public class SecureTokenGenerator {

	public static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	public static final int SECURE_TOKEN_LENGTH = 20;
	private static final SecureRandom random = new SecureRandom();
	private static final char[] symbols = CHARACTERS.toCharArray();
	private static final char[] buf = new char[SECURE_TOKEN_LENGTH];
	
	public static String nextToken() {
		for (int i = 0; i < buf.length; ++i)
			buf[i] = symbols[random.nextInt(symbols.length)];
		return new String(buf);
	}
}