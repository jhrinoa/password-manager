var passwordMgrApp = angular.module('passwordMgrApp', ['ui.router']);

// configure our routes
passwordMgrApp.config(['$locationProvider', '$stateProvider', '$httpProvider',
	function($locationProvider, $stateProvider, $httpProvider) {
		$locationProvider.html5Mode({
		    enabled: true,
		    requireBase: false
	    });
		$stateProvider
			// route for the home page
			.state('home', {
				url: '/',
				templateUrl : 'pages/home.html',
				controller : 'mainController'
			})
	
			// route for the about page
			.state('about', {
				url: '/about',
				templateUrl : 'pages/about.html',
				controller : 'aboutController'
			})
	
			// route for the contact page
			.state('contact', {
				url: '/contact',
				templateUrl : 'pages/contact.html',
				controller : 'contactController'
			})
	
			// route for the contact page
			.state('login', {
				url: '/login',
				templateUrl : 'pages/login.html',
				controller : 'loginController'
			});
	}
]);

// create the controller and inject Angular's $scope
passwordMgrApp.controller('mainController', function($scope) {
	// create a message to display in our view
	$scope.message = 'Keep it up!!!';
});

passwordMgrApp.controller('aboutController', function($scope) {
	$scope.message = 'About what?';
});

passwordMgrApp.controller('contactController', function($scope) {
	$scope.message = 'Dont ever contact us!';
});

passwordMgrApp.factory('Login', loginFactory);

passwordMgrApp.controller('loginController', function($scope, $http, $state) {
	console.log('jlee');
	$scope.signIn = function() {
		console.log('jlee-signin');
		console.log('jlee-username:' + $scope.username);
		console.log('jlee-pw:' + $scope.password);

		// TODO: handle JWT
		$http.post('rest/auth/login', {
			username : $scope.username,
			password : $scope.password
		}).
		then(
			function (response) {
				debugger;
				console.log('Login successful!');
				$state.go('home');
			}, function  (err) {
				console.log('Login failed');
				$state.go('login');
			}
		);

	}
});

function loginFactory($window) {
	var isLoggedIn;
	if ($window.localStorage.token) {
		isLoggedIn = true;
	}
	return {
		isLoggedIn : isLoggedIn
	};
}