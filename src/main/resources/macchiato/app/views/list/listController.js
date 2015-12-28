(function () {
	'use strict';
	
	angular.module('passwordMgrApp').controller('listController', function($scope, $http, $state) {
		$scope.message = 'This is secured Page';

		// TODO: Is there a way not to use this relative path?
		$http.get('../../rest/password/list', {		
		}).
		then(
			//TODO: Do a proper response handling
			function (response) {
				console.log('List request done!');
			}, function  (err) {
				console.log('List request failed!');
				$state.go('login');
			}
		);
	});
})();