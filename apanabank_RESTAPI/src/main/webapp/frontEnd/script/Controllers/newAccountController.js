var app = angular.module("myBankApp");
app.controller("newAccountController",['$scope','customerService',function($scope,customerService){
	var accountDetails={};
	var accnNo;
	this.isValidated=false;
	this.isDataSubmitted=false;

	this.addAccount= function(isValid){
		this.isValidated=true;
		if(isValid && this.isPassValidated){
			accountNo=getAccountNumber();
			if(accountNo==""){
				accnNo=1;
			}else{
				accnNo = accountNo+1;
			}
			accountDetails={
					"AccountNo": accnNo, 
					"Username" : this.username,
					"Password" : this.password,
					"Confirm_Password" : this.cPassword,
					"Amount" : this.amount,
					"Address" : this.address,
					"email" : this.email,
					"Phone" : this.phone,
			}

			this.accountDetails = customerService.setNewAccountDetails(accountDetails);
			this.isDataSubmitted=true;
		}
	};
	 this.passMatch = function() {
	if	(this.password == this.cPassword) {
		this.isPassValidated=true;
	} else { this.isPassValidated=false;
	}
	}
	getAccountNumber= function(){	
		var accounts= customerService.getNewAccountDetails();
		if(accounts.length==0){
			return "";
		}else {
			return accounts[accounts.length-1].AccountNo;
		}
	}

	/*$scope.hello="hello from world";*/
}]);