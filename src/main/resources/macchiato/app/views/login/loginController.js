(function () {
	'use strict';
	
	angular.module('passwordMgrApp').controller('loginController', function($scope, $http, store, $state) {
		$scope.username = 'asd@asd.com';
		
		$scope.signIn = function() {
			// TODO: handle JWT
			$http.post('../../rest/auth/login', {
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
})();