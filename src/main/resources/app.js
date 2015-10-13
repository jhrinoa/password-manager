var passwordMgrApp = angular.module('passwordMgrApp', ['ui.router', 'angular-jwt', 'angular-storage']);

// configure our routes
passwordMgrApp.config(['$locationProvider', '$stateProvider', 'jwtInterceptorProvider', '$httpProvider',
	function($locationProvider, $stateProvider, jwtInterceptorProvider, $httpProvider) {
		$locationProvider.html5Mode({
		    enabled: true,
		    requireBase: true
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
		
		  jwtInterceptorProvider.tokenGetter = function(store) {
			    return store.get('jwt');
			  };
			  
		  $httpProvider.interceptors.push('jwtInterceptor');
	}
]);

passwordMgrApp.run(function($rootScope, $state, store, jwtHelper) {
	$rootScope.$on('$stateChangeStart',
			function(e, to) {
				if (to.data && to.data.requiresLogin) {
					if (!store.get('jwt')
							|| jwtHelper.isTokenExpired(store.get('jwt'))) {
						e.preventDefault();
						$state.go('login');
					}
				}
			});
});

// create the controller and inject Angular's $scope
passwordMgrApp.controller('mainController', function($scope) {
	// create a message to display in our view
	$scope.message = 'Keep it up!!!';
});

passwordMgrApp.controller('aboutController', function($scope, $http) {
	$scope.message = 'About what?';

	//TODO: This is just for testing list API. Put this logic in the list.html
	$http.get('rest/password/list', {		
	}).
	then(
		function (response) {
			debugger;
			console.log('List request done!');
		}, function  (err) {
			console.log('List request failed!');
		}
	);
});

passwordMgrApp.controller('contactController', function($scope) {
	$scope.message = 'Dont ever contact us!';
});

passwordMgrApp.factory('Login', loginFactory);

passwordMgrApp.controller('loginController', function($scope, $http, store, $state) {
	$scope.username = 'asd@asd.com';
	
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
				store.set('jwt', response.data);
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