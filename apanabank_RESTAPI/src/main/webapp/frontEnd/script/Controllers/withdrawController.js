var app = angular.module("myBankApp");
app.controller("withdrawController",['$scope','customerService',function($scope,customerService){
	var withdrawDetails={};
	this.isValidated=false;
	this.isDataSubmitted=false;
	this.isValid=false
	this.withdrawAccount= function(isValid){
		this.isValidated=true;
		if(isValid){
			withdrawDetails = {
					"AccountNo": this.accountNo,
					"Username" : this.username,
					"Password" : this.password,
			}
			var outputData = validateFun(withdrawDetails);
			if(outputData.out){

				this.withdrawDetails = withdrawMoney(outputData.result, this.amount);
				this.isDataSubmitted=true;
				this.isValid=false;
			} else{
				this.validationMsg = outputData.result;
				this.isValid=true;
			}
		}
	};

	withdrawMoney = function(data, amount){
		return output =customerService.withdrawAmount(data,amount);
	};

	validateFun = function(data){
		return output= customerService.validateAccount(data);

	};

}]);