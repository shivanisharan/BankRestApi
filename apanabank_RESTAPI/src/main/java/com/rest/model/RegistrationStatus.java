package com.rest.model;

public class RegistrationStatus  {
	private int code;
	private String message;
	private long account_no;
	
	
	public RegistrationStatus(int code, String message, long account_no) {
		super();
		this.code = code;
		this.message = message;
		this.account_no = account_no;
	}
	
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getAccount_no() {
		return account_no;
	}
	public void setAccount_no(long account_no) {
		this.account_no = account_no;
	}
	
	
}
