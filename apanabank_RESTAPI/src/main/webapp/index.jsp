<!DOCTYPE html>
<html lang="en">
<head>
<title>Finance System</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="frontEnd/css/styles.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-route.js"></script>




</head>
<body>
	<div class="container-fluid" ng-app="myBankApp">
		<!--<div class="row mainPage">
			<div class="col-sm-12">
				<div class=" crousal">
				<a href="#/">
					<div class="container-fluid">
						<img class="imgMain"
							src="frontEnd/img/globe_03.gif" />

					</div>
					 </a>
				</div>-->
			Welcome	<%=request.getSession().getAttribute("userName") %>
				<nav class="navbar navbar-inverse navbarStyle">
					<div class=" col-sm-12 container-fluid navigation">
						<div class="navbar-header">
							<button type="button" class="navbar-toggle navInnerStyle"
								data-toggle="collapse" data-target="#myNavbar">
								<span class="icon-bar"></span> <span class="icon-bar"></span> <span
									class="icon-bar"></span>
							</button>
						</div>
						<div class="col-sm-12 collapse navbar-collapse menuBar"
							id="myNavbar">
							<ul class="col-sm-12 nav navbar-nav "
								">
								<li class="col-sm-1 itemAlign"><a href="#newAccount"
									class="menuItem" style="padding-left: 0; padding-right: 0;">NEW
										ACCOUNT</a></li>
								<li class="col-sm-1 itemAlign"><a href="#balance"
									class="menuItem">BALANCE</a></li>

								<li class="col-sm-1 itemAlign"><a href="#deposit"
									class="menuItem">DEPOSIT</a></li>
								<li class="col-sm-1 itemAlign"><a href="#withdraw"
									class="menuItem">WITHDRAW</a></li>
								<li class="col-sm-1 itemAlign"><a href="#transfer"
									class="menuItem">TRANSFER</a></li>
								<li class="col-sm-1 itemAlign"><a href="#closeAc"
									class="menuItem">CLOSE A/C</a></li>
									<li class="col-sm-1 itemAlign"><a href="#login"
									class="menuItem">LOG-IN</a></li>
									

								<li class="col-sm-1 itemAlign"><a href="#aboutUs"
									class="menuItem">CONTACT US</a></li>
							</ul>
							
						</div>
					</div>
				</nav>
				<div ng-view></div>

			</div>

		</div>
	</div>
	<script src="frontEnd/script/app.js"></script>
	<script src="frontEnd/script/Services/customerService.js"></script>
	<script src="frontEnd/script/Controllers/newAccountController.js"></script>
	<script src="frontEnd/script/Controllers/balanceController.js"></script>
	<script src="frontEnd/script/Controllers/depositController.js"></script>
	<script src="frontEnd/script/Controllers/withdrawController.js"></script>
	<script src="frontEnd/script/Controllers/transferController.js"></script>
	<script src="frontEnd/script/Controllers/closeController.js"></script>

	<script src="frontEnd/script/Directive/numberOnly.js"></script>
</body>
<!--<footer class="container-fluid col-sm-12" >
	<p>by-VINAY SONI.</p>
</footer>-->
</html>

