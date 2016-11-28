var app = angular.module("myBankApp", ["ngRoute"]);
// app.controller("myCtrl", function($scope) {
   
// });

app.config(function($routeProvider) {
    $routeProvider
    .when("/", {
        templateUrl : "frontEnd/views/home.html"
    })
    .when("/newAccount", {
    	controller: 'newAccountController as accnCtrl',
    	templateUrl : "frontEnd/views/newAccount.html"
    })
    .when("/balance", {
    	controller: 'balanceController as blncCtrl',
        templateUrl : "frontEnd/views/balance.html"
    })
    .when("/deposit", {
    	controller: 'depositController as dpstCtrl',
        templateUrl : "frontEnd/views/deposit.html"
    })
    .when("/withdraw", {
    	controller: 'withdrawController as wdrCtrl',
        templateUrl : "frontEnd/views/withdraw.html"
    })
    .when("/transfer", {
    	controller: 'transferController as tnfrCtrl',
        templateUrl : "frontEnd/views/transfer.html"
    })
    .when("/closeAc", {
    	controller: 'closeController as closeCtrl',
        templateUrl : "frontEnd/views/closeAC.html"
    })
    .when("/aboutUs", {
        templateUrl : "frontEnd/views/home.html"
    });
});