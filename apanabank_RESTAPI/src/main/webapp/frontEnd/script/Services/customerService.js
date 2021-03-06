var app = angular.module("myBankApp");
app.service('customerService',['$http',function($http){
	var newAccountDetailsArray=[];
	var balDetails={};

	this.getNewAccountDetails = function(){
		return newAccountDetailsArray;
	}
	this.setNewAccountDetails = function(obj){
		newAccountDetailsArray.push(obj);
		return newAccountDetailsArray;
	}


	this.validateAccount = function(obj){
		if(newAccountDetailsArray.length==0){
			balDetails={"out":false,"result":"No Account"};
			return balDetails;
		}else{
			for(var i=0; i<newAccountDetailsArray.length;i++){
				if((obj.AccountNo==newAccountDetailsArray[i].AccountNo)
						&&(obj.Username===newAccountDetailsArray[i].Username)
						&&(obj.Password===newAccountDetailsArray[i].Password)){
					balDetails={"out":true,"result":newAccountDetailsArray[i]};
					return balDetails;
				}
			}
			balDetails={"out":false,"result":"No Match"};
			return balDetails;
		}
	}
	
	this.closeAccount = function(par) {
		for(var i=0; i < newAccountDetailsArray.length;i++){
			if((par.AccountNo==newAccountDetailsArray[i].AccountNo)
					&&(par.Username===newAccountDetailsArray[i].Username)
					&&(par.Password===newAccountDetailsArray[i].Password)){
				var removedAcc = newAccountDetailsArray[i];
				newAccountDetailsArray.splice(i,1);
				return removedAcc;
			}
		}
}

	this.depositAmount = function(obj, amount){
		for (var i=0; i< newAccountDetailsArray.length; i++) {
			if (newAccountDetailsArray[i].AccountNo == obj.AccountNo) {
				newAccountDetailsArray[i].Amount = parseInt(newAccountDetailsArray[i].Amount)+parseInt(amount);
				return newAccountDetailsArray[i];
			}
		}
	}

	this.withdrawAmount = function(obj, amount){
		for (var i=0; i< newAccountDetailsArray.length; i++) {
			if (newAccountDetailsArray[i].AccountNo == obj.AccountNo) {
				newAccountDetailsArray[i].Amount = parseInt(newAccountDetailsArray[i].Amount)-parseInt(amount);
				return newAccountDetailsArray[i];
			}
		}
	}

	this.validateTargetAccount = function(accountNo){
		for(var i=0; i<newAccountDetailsArray.length;i++){
			if(accountNo==newAccountDetailsArray[i].AccountNo){
				balDetails={"out":true,"result":newAccountDetailsArray[i]};
				return balDetails;
			}
		}
		balDetails={"out":false,"result":"Target account does not exists"};
		return balDetails;
	}

	this.transferAmount = function(obj, amount, trgtAccn){
		var transfer={};
		for (var i=0; i< newAccountDetailsArray.length; i++) {
			if (newAccountDetailsArray[i].AccountNo == obj.AccountNo) {
				for(var j=0; j< newAccountDetailsArray.length; j++){
					if(newAccountDetailsArray[j].AccountNo == trgtAccn){
						newAccountDetailsArray[i].Amount = parseInt(newAccountDetailsArray[i].Amount)-parseInt(amount);
						newAccountDetailsArray[j].Amount = parseInt(newAccountDetailsArray[j].Amount)+parseInt(amount);
						transfer = {"From":newAccountDetailsArray[i],"To":newAccountDetailsArray[j] };
						return transfer;
					}
				}
			}

		}
	}
}]);