var app = angular.module("myBankApp");
app.controller("transferController",['$scope','customerService',function($scope,customerService){
	var withdrawDetails={};
	this.isValidated=false;
	this.isDataSubmitted=false;
	this.isValid=false
	this.transferAccount= function(isValid){
		this.isValidated=true;
		if(isValid){
			transferDetails = {
					"AccountNo": this.accountNo,
					"Username" : this.username,
					"Password" : this.password,
			}
			var outputData = validateFun(transferDetails);
			if(outputData.out){
				if(outputData.result.Amount >= this.amount){
					var validTarget = validateTargetAccount(this.targetAccountNo);
					if(validTarget.out){
						this.transferDetails = transferMoney(outputData.result, this.amount, this.targetAccountNo);
						this.isDataSubmitted=true;
						this.isValid=false;
					}else{
						this.validationMsg = validTarget.result
						this.isValid=true;
					}
				} else{
					this.validationMsg = "Insufficient Balance";
					this.isValid=true;
				}
			}else{
				this.validationMsg = outputData.result;
				this.isValid=true;
			}
		}
	};

	transferMoney = function(data, amount, trgtAccn){
		return output = customerService.transferAmount(data,amount,trgtAccn);
	};

	validateFun = function(data){
		return output= customerService.validateAccount(data);

	};

	validateTargetAccount = function(targetAccountNo){
		return output= customerService.validateTargetAccount(targetAccountNo);

	};

}]);