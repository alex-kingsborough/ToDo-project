package client;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//CITE: https://www.mkyong.com/java/java-sha-hashing-example/
public class Encrypt {
	
	private static MessageDigest md;
	private static StringBuffer sb;
	
	static {
		try {
			md = MessageDigest.getInstance("SHA-1"); //40 character
		} catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		}
		sb = new StringBuffer();
	}
	
	public static String SHA1(String password) {
		md.update(password.getBytes());
		byte[] byteData = md.digest();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
		String encrypted = sb.toString();
		sb.setLength(0);
		return encrypted;
	}
}
