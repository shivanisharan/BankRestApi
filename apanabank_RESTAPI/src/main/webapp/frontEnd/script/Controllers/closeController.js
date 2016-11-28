var app = angular.module("myBankApp");
app.controller("closeController",['$scope','customerService',function($scope,customerService){
	var closeDetails={};
	var accnNo;
	this.isValidated=false;
	this.isDataSubmitted=false;
	this.isValid=false;
	this.closeAccount= function(isValid){
		this.isValidated=true;
		if(isValid){
			closeDetails = {
					"AccountNo": this.accountNo,
					"Username" : this.username,
					"Password" : this.password
			}
			var outputData = validateFun(closeDetails);
			if(outputData.out){
				this.closeDetails = closeAccountFun(outputData.result);
				
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
	
	closeAccountFun = function(data){
		return output= customerService.closeAccount(data);
		
	};
	
	/*$scope.hello="hello from world";*/
}]);