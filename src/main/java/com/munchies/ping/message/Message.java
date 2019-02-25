package com.munchies.ping.message;

import java.io.Serializable;

public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8088953035477109486L;

	private String captian;
	private String userFirebaseKey;
	
	public void setCaptian(String captian) {
		this.captian = captian;
	}
	
	public void setUserFirebaseKey(String userFirebaseKey) {
		this.userFirebaseKey = userFirebaseKey;
	}
	
	public String getCaptian() {
		return captian;
	}
	
	public String getUserFirebaseKey() {
		return userFirebaseKey;
	}
}
