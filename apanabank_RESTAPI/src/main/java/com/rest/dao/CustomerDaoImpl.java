package com.rest.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.rest.model.Customer;
import com.rest.service.MailService;

public class CustomerDaoImpl implements CustomerDao {
	static Logger logger=Logger.getLogger(CustomerDaoImpl.class.getName());
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	private HttpSession session;
	private HttpServletRequest request;

	public long customerRegistration(Customer customer) {
		long account_no = 0;
		int id=0;
		String hash=null;
		String name = customer.getName();
		String password = customer.getPassword();
		String address = customer.getAddress();
		String email=customer.getEmail();
		long phone = customer.getPhone();
		double amount=customer.getAmount();
		try {
			con = DBConnection.getConnection();
			stmt = (Statement) con.createStatement();
			hash=sha256(password);
			
			if(amount == 0){
				amount = 1000;
			}
			if (!name.isEmpty() && !password.isEmpty() && !email.isEmpty()) {
				String sql = "insert into apana_bank.customer(name,password,address,email,phone,amount,email_verification_hash,status) values('" + name + "','"
						+ password + "','" + address + "','" + email + "','" + phone + "','" + amount + "','" + hash + "','Inactive')";
				logger.info(sql);
				int record = stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

				if (record > 0) {
					rs = stmt.getGeneratedKeys();
					rs.next();
					logger.info("Key returned from getGeneratedKeys():" + rs.getInt(1));
					id = rs.getInt(1);
					rs.close();
					AccountDao accountDao = new AccountDaoImpl();
					boolean accountStatus = accountDao.createAccount(id,amount);
					if (accountStatus) {
						account_no = accountDao.fetchAccountNo(id);
						DBConnection.closeConnection();
						MailService mailService=new MailService();
						mailService.sendMail(id,name, email, hash);
						return account_no;
					}
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	private String sha256(String password) throws NoSuchAlgorithmException {
		MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        byte[] result = mDigest.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
	}
	
	public boolean userEmailVerification(String hash,int uid){
		con=DBConnection.getConnection();
		try {
			stmt=(Statement) con.createStatement();
			String sql="select email_verification_hash from apana_bank.customer where id="+uid;
			rs=(ResultSet) stmt.executeQuery(sql);
			rs.next();
			String email_verification_hash=rs.getString("email_verification_hash");
			if(email_verification_hash.equals(hash)){
				System.out.println("String matched");
				return true;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void updateStatus(int uid) {
		con=DBConnection.getConnection();
		try {
			stmt=(Statement) con.createStatement();
			String sql="update apana_bank.customer set status='activate' where id="+uid;
			stmt.executeUpdate(sql);
			System.out.println("status updated to activate");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean checkLogin(Customer customer,HttpServletRequest request){
		con=DBConnection.getConnection();
		String name="";
		String password="";
		String status="Inactivate";
		try {
			stmt=(Statement) con.createStatement();
			String sql="select * from apana_bank.customer where name='"+customer.getName()+"' and password='"+customer.getPassword()+"'";
			rs=(ResultSet) stmt.executeQuery(sql);
			while(rs.next()){
				name=rs.getString("name");
				password=rs.getString("password");
				status=rs.getString("status");
				
			}
			if(!name.isEmpty() && !password.isEmpty()&& status.equalsIgnoreCase("activate") ){
				session=request.getSession();
				session.setAttribute("user_name", name);
				session.setAttribute("user_password", password);
				session.setAttribute("user_status", status);
				logger.info("Session managed");
				System.out.println("String matched");
				return true;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public String getStatus(int userID){
		con=DBConnection.getConnection();
		String status ="inactivate";
		try {
			stmt=(Statement) con.createStatement();
			String sql="select status from apana_bank.customer where id="+userID;
			rs=(ResultSet) stmt.executeQuery(sql);
			rs.next();
			status=rs.getString("status");
			return status;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

}
