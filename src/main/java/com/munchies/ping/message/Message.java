package com.munchies.ping.message;

import java.io.Serializable;

public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8088953035477109486L;

	private String captain;
	private String userFirebaseKey;
	
	public void setCaptain(String captain) {
		this.captain = captain;
	}
	
	public void setUserFirebaseKey(String userFirebaseKey) {
		this.userFirebaseKey = userFirebaseKey;
	}
	
	public String getCaptain() {
		return captain;
	}
	
	public String getUserFirebaseKey() {
		return userFirebaseKey;
	}
}
