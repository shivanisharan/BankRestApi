package com.rest.dao;

import java.util.Map;

import com.rest.model.Account;

public interface AccountDao {
	public boolean createAccount(int id,double amount);
	public long fetchAccountNo(int id);
	public int creditBalance(Account account);
	public Map showBalance(long account_no);
	public int withdrawBalance(Account account);
	public boolean transferbalance(long src_acc_no ,long target_acc_no,double amount);
	public boolean closeAccount(long account_no) ;
}
