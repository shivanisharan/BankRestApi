package com.rest.dao;

import javax.servlet.http.HttpServletRequest;

import com.rest.model.Customer;

public interface CustomerDao {
	public long customerRegistration(Customer customer);
	public boolean userEmailVerification(String hash,int uid);
	public void updateStatus(int uid);
	public boolean checkLogin(Customer customer,HttpServletRequest request);
	public String getStatus(int userID);
}
