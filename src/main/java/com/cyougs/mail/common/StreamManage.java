package com.cyougs.mail.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamManage extends Thread {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	InputStream inputStream;
	String type;
	public StreamManage(InputStream inputStream,String type) {
		this.inputStream = inputStream;
		this.type = type;
	}
	public void run () {
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String line = null;
		try {
			while((line = bufferedReader.readLine()) !=null ) {
				if(type.equals("Error")) {
					logger.error(line);
				}else {
					logger.debug(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
