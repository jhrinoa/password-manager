var app = angular.module('password-mgr', []);

app.controller('hello', function($scope, $http) {
	$http.get('rest/auth/login').success(
			function(data) {
				$scope.greeting = data;
			});
});