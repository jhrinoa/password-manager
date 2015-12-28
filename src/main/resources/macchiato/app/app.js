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
				templateUrl : 'views/home/home.html',
				controller : 'homeController'
			})
	
			// route for the about page
			.state('about', {
				url: '/',
				templateUrl : 'views/about/about.html',
				controller : 'aboutController'
			})
	
			// route for the contact page
			.state('contact', {
				url: '/',
				templateUrl : 'views/contact/contact.html',
				controller : 'contactController'
			})
	
			// route for the contact page
			.state('login', {
				url: '/',
				templateUrl : 'views/login/login.html',
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
				templateUrl: 'views/list/list.html',
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