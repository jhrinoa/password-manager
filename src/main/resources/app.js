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
				controller : 'homeController'
			})
	
			// route for the about page
			.state('about', {
				url: '/',
				templateUrl : 'pages/about.html',
				controller : 'aboutController'
			})
	
			// route for the contact page
			.state('contact', {
				url: '/',
				templateUrl : 'pages/contact.html',
				controller : 'contactController'
			})
	
			// route for the contact page
			.state('login', {
				url: '/',
				templateUrl : 'pages/login.html',
				controller : 'loginController'
			})
			
			.state('logout', {
				url : '/',
				controller : function ($scope, store, $state) {
					store.remove('jwt');
					$state.go('home');
				}
			})
		
			// route for the list page
			.state('list', {
				url: '/',
				templateUrl: 'pages/list.html',
				controller: 'listController',
			    data: {
			        requiresLogin: true
			    }
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
passwordMgrApp.controller('mainController', function($scope, store, jwtHelper) {	
	$scope.isUserLoggedIn = function () {
		return (store.get('jwt') && !jwtHelper.isTokenExpired(store.get('jwt')));
	}
});

passwordMgrApp.controller('aboutController', function($scope, $http) {
	$scope.message = 'About what?';
});

passwordMgrApp.controller('listController', function($scope, $http, $state) {
	$scope.message = 'This is secured Page';

	$http.get('rest/password/list', {		
	}).
	then(
		function (response) {
			console.log('List request done!');
		}, function  (err) {
			console.log('List request failed!');
			$state.go('login');
		}
	);
});

passwordMgrApp.controller('contactController', function($scope) {
	$scope.message = 'Dont ever contact us!';
});

passwordMgrApp.controller('loginController', function($scope, $http, store, $state) {
	$scope.username = 'asd@asd.com';
	
	$scope.signIn = function() {
		// TODO: handle JWT
		$http.post('rest/auth/login', {
			username : $scope.username,
			password : $scope.password
		}).
		then(
			function (response) {
				console.log('Login successful!');
				store.set('jwt', response.data);
				$state.go('home');
			}, function  (err) {
				alert('Login failed');
				$state.go('login');
			}
		);

	}
});

passwordMgrApp.controller('homeController', function($scope) {
	// create a message to display in our view
	$scope.message = 'Fancy Home!!!';
});