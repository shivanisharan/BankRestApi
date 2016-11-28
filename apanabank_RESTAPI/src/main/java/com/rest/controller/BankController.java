package com.rest.controller;



import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rest.dao.AccountDao;
import com.rest.dao.AccountDaoImpl;
import com.rest.dao.CustomerDao;
import com.rest.dao.CustomerDaoImpl;
import com.rest.model.Account;
import com.rest.model.Customer;
import com.rest.model.RegistrationStatus;
import com.rest.model.Status;

@RestController
@RequestMapping("/customer")
public class BankController {
	static Logger logger=Logger.getLogger(BankController.class.getName());
	@RequestMapping(value = "/register.bank",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody RegistrationStatus registration(@RequestBody Customer customer){
		try{
			logger.info("Registration under process");
			CustomerDao customerDao=new CustomerDaoImpl();
			long account_no=customerDao.customerRegistration(customer);
			if(account_no == 0){
				return new RegistrationStatus(0,"Invalid Input Fields",0);
			}
			return new RegistrationStatus(1,"Please verify your email in order to activate your account! ",account_no);
			
		}catch(Exception e){
			return new RegistrationStatus(0,e.toString(),0);
		}
		
	}
	@RequestMapping(value = "/login.bank",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody Status userLogin(@RequestBody Customer customer,HttpServletRequest request){
		try{
			logger.info("Login under process");
			CustomerDao customerDao=new CustomerDaoImpl();
			boolean status=customerDao.checkLogin(customer,request);
			if(!status){
				return new Status(0,"Invalid Input Fields");
			}
			return new Status(1,"Customer Logged in successfully! ");
			
		}catch(Exception e){
			return new Status(0,e.toString());
		}
		
	}
	
	@RequestMapping(value = "/credit.bank",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody Status credit(@RequestBody Account account){
		try{
			logger.info("Credit amount");
			AccountDao accountDao=new AccountDaoImpl();
			int status=accountDao.creditBalance(account);
			if(status == 0){
				return new Status(0,"Credit Operation Failed!");
			}
			return new Status(1,"Amount Credited successfully!");
			
		}catch(Exception e){
			return new Status(0,e.toString());
		}
		
	}
	
	@RequestMapping(value = "/balance.bank", method = RequestMethod.GET)
	public @ResponseBody Map checkBalance(@RequestParam("accountnumber") long account_no){
		try{
			logger.info("Checking Balance");
			AccountDao accountDao=new AccountDaoImpl();
			System.out.println(account_no);
			Map balance=accountDao.showBalance(account_no);
			return balance;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}
	
	@RequestMapping(value = "/withdraw.bank",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody Status withdrawBalance(@RequestBody Account account){
		try{
			logger.info("withdrawn amount");
			AccountDao accountDao=new AccountDaoImpl();
			int status=accountDao.withdrawBalance(account);
			if(status == 0){
				return new Status(0,"Debit Operation Failed!");
			}
			return new Status(1,"Amount debited successfully!");
			
		}catch(Exception e){
			return new Status(0,e.toString());
		}
		
	}
	
	@RequestMapping(value = "/transfer.bank", method = RequestMethod.GET)
	public @ResponseBody Status transferBalance(@RequestParam("src_acc_no") long src_acc_no,@RequestParam("target_acc_no") long target_acc_no,@RequestParam("amount") double amount){
		try{
			logger.info("withdrawn amount");
			AccountDao accountDao=new AccountDaoImpl();
			boolean status=accountDao.transferbalance(src_acc_no, target_acc_no, amount);
			if(!status){
				return new Status(0,"Amount Transfer Failed!");
			}
			return new Status(1,"Amount Transfered successfully!");
			
		}catch(Exception e){
			return new Status(0,e.toString());
		}
		
	}
	
	@RequestMapping(value = "/closeAccount.bank", method = RequestMethod.GET)
	public @ResponseBody Status closeAccount(@RequestParam("account_no") long account_no){
		try{
			logger.info("withdrawn amount");
			AccountDao accountDao=new AccountDaoImpl();
			boolean status=accountDao.closeAccount(account_no);
			if(!status){
				return new Status(0,"Operation failed");
			}
			return new Status(1,"Account Closed successfully!");
			
		}catch(Exception e){
			return new Status(0,e.toString());
		}
		
	}
	
	@RequestMapping(value = "/logout.bank", method = RequestMethod.GET)
	public @ResponseBody Status userLogout(HttpServletRequest request){
		try{
			HttpSession session=request.getSession();
			logger.info("user name: "+session.getAttribute("userName"));
			session.invalidate();
			logger.info("user name: "+session.getAttribute("userName"));
			logger.info("Logout Customer");
			
			return new Status(1,"Session Invalidate...User logout successfully!");
			
		}catch(Exception e){
			return new Status(0,e.toString());
		}
		
	}
	
}
