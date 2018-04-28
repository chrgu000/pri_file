package com.gzjky.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserToken implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8590590663225641249L;
	private String userId;
	private String aesPrivateKey;
}
