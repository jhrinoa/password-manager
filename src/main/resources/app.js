var passwordMgrApp = angular.module('passwordMgrApp', ['ngRoute']);

// configure our routes
passwordMgrApp.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {
    $routeProvider
        // route for the home page
        .when('/passwordMgr', {
            templateUrl : 'passwordMgr/pages/home.html',
            controller  : 'mainController'
        })

        // route for the about page
        .when('/passwordMgr/about', {
            templateUrl : 'passwordMgr/pages/about.html',
            controller  : 'aboutController'
        })

        // route for the contact page
        .when('/passwordMgr/contact', {
            templateUrl : 'passwordMgr/pages/contact.html',
            controller  : 'contactController'
        });
    
    $locationProvider.html5Mode(true);
}]);



// create the controller and inject Angular's $scope
passwordMgrApp.controller('mainController', function($scope) {
	// create a message to display in our view
	$scope.message = 'This is Home page!';
});

passwordMgrApp.controller('aboutController', function($scope) {
    $scope.message = 'About what?';
});

passwordMgrApp.controller('contactController', function($scope) {
    $scope.message = 'Dont ever contact us!';
});