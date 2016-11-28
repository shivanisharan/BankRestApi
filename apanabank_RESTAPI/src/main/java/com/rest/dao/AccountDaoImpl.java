package com.rest.dao;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.rest.model.Account;

public class AccountDaoImpl implements AccountDao {
	static Logger logger=Logger.getLogger(AccountDaoImpl.class.getName());
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	public boolean createAccount(int id,double amount) {
		try{
			con=DBConnection.getConnection();
			stmt=(Statement) con.createStatement();
			Test test=new Test();
			long account_no=test.generateAccountNum();
			String sql="insert into apana_bank.account(account_no,amount,id,status) values('"+account_no+"','"+amount+"','"+id+"','Active')";
			logger.info(sql);
			int record=stmt.executeUpdate(sql);
			if(record>0){
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	public long fetchAccountNo(int id){
		try{
			long account_no=0;
			con=DBConnection.getConnection();
			stmt=(Statement) con.createStatement();
			String sql="select account_no from apana_bank.account where id="+id;
			logger.info(sql);
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				account_no=rs.getLong("account_no");
				System.out.println("account no is "+account_no);
				logger.info("account no is "+account_no);
			}
			return account_no;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return 0;
		
	}
	
	public int creditBalance(Account account){
		try{
			double initialAmount=0;
			con=DBConnection.getConnection();
			stmt=(Statement) con.createStatement();
			long account_no = account.getAccount_no();
			double creditAmount=account.getAmount();
			if(creditAmount == 0)return 0;
			String sql="select amount from apana_bank.account where account_no="+account_no;
			logger.info(sql);
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				initialAmount=rs.getDouble("amount");
				System.out.println("initialAmount  is "+initialAmount);
				logger.info("initialAmount  is "+initialAmount);
			}
			double actualAmount=initialAmount+creditAmount;
			if( actualAmount>0){
				String sql1="update apana_bank.account set amount='"+actualAmount+"' where account_no="+account_no;
				System.out.println(sql1);
				int status =stmt.executeUpdate(sql1);
				return status;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return 0;
		
	}
	
	public Map showBalance(long account_no){
		try{
			//List balance=new ArrayList();
			Map balance = new HashMap();
			String name=null;
			long account_num=0;
			double amount=0.0;
			con=DBConnection.getConnection();
			stmt=(Statement) con.createStatement();
			String sql="select u.name,a.account_no,a.amount from account a inner join customer u on a.id=u.id where a.account_no="+account_no;
			logger.info(sql);
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				name=rs.getString(1);
				balance.put("Customer_name", name);
				account_num=rs.getLong(2);
				balance.put("account_number", account_num);
				amount=rs.getDouble(3);
				balance.put("amount", amount);
				System.out.println(rs.getString(1)+":"+rs.getLong(2)+":"+rs.getDouble(3));
			}
			return balance;
			
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public int withdrawBalance(Account account){
		try{
			double initialAmount=0;
			con=DBConnection.getConnection();
			stmt=(Statement) con.createStatement();
			long account_no = account.getAccount_no();
			double debitAmount=account.getAmount();
			if(debitAmount == 0)return 0;
			String sql="select amount from apana_bank.account where account_no="+account_no;
			logger.info(sql);
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				initialAmount=rs.getDouble("amount");
				System.out.println("initialAmount  is "+initialAmount);
				logger.info("initialAmount  is "+initialAmount);
			}
			double actualAmount=initialAmount-debitAmount;
			if( actualAmount>0){
				String sql1="update apana_bank.account set amount='"+actualAmount+"' where account_no="+account_no;
				System.out.println(sql1);
				int status =stmt.executeUpdate(sql1);
				return status;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return 0;
		
	}
	
	public boolean transferbalance(long src_acc_no ,long target_acc_no,double amount){
		try{
			con=DBConnection.getConnection();
			stmt=(Statement) con.createStatement();
			double src_amount=0;
			double target_amount=0;
			String sql="select amount from apana_bank.account where account_no="+src_acc_no;
			logger.info(sql);
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				src_amount=rs.getDouble("amount");
			}
			if(src_amount>0 && src_amount>=amount){
				src_amount=src_amount-amount;
				String sql1="update apana_bank.account set amount='"+src_amount+"' where account_no="+src_acc_no ;
				logger.info(sql1);
				int src_update=stmt.executeUpdate(sql1);
				if(src_update>0){
					String sql2="select amount from apana_bank.account where account_no="+target_acc_no;
					logger.info(sql2);
					rs=stmt.executeQuery(sql2);
					while(rs.next()){
						target_amount=rs.getDouble("amount");
					}
					if(src_amount>0){
						target_amount=target_amount+amount;
						String sql3="update apana_bank.account set amount='"+target_amount+"' where account_no="+target_acc_no ;
						logger.info(sql3);
						int target_update=stmt.executeUpdate(sql3);
						if(target_update>0)return true;
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
		
	}
	
	public boolean closeAccount(long account_no) {
		try{
			con=DBConnection.getConnection();
			stmt=(Statement) con.createStatement();
			String sql="update apana_bank.account set status='Inactive' where account_no="+account_no;
			logger.info(sql);
			int record=stmt.executeUpdate(sql);
			if(record>0){
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
	

}
