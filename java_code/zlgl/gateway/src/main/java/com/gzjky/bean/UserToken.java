package com.gzjky.bean;

import java.io.Serializable;

public class UserToken implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8590590663225641249L;
	private String userId;
	private String aesPrivateKey;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAesPrivateKey() {
		return aesPrivateKey;
	}
	public void setAesPrivateKey(String aesPrivateKey) {
		this.aesPrivateKey = aesPrivateKey;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
