var app = angular.module("myBankApp");
app.controller("balanceController",['$scope','customerService',function($scope,customerService){
	var balanceDetails={};
	this.isValidated=false;
	this.isDataSubmitted=false;
	this.isValid=false
	this.getAccount= function(isValid){
		this.isValidated=true;
		if(isValid){
			balanceDetails = {
					"AccountNo": this.accountNo,
					"Username" : this.username,
					"Password" : this.password
			}
			var outputData = validateFun(balanceDetails);
			if(outputData.out){
				this.balanceDetails = outputData.result;
			this.isDataSubmitted=true;
			this.isValid=false;
			} else{
				this.validationMsg = outputData.result;
				this.isValid=true;
			}
		}
	};
	validateFun = function(data){
		return output= customerService.validateAccount(data);
		
	};
	
	/*$scope.hello="hello from world";*/
}]);