package com.cyougs.mail.common;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SHAUtil {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	public static String shaEncode(String inStr) throws Exception {
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA");
		} catch (Exception e) {
			return "";
		}
		byte[] byteArray = inStr.getBytes("UTF-8");
		return "";
	}
}
