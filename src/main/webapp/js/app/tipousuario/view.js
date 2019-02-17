'use strict'

moduleTipousuario.controller('tipousuarioViewController', ['$scope', '$http', 'toolService', 
    '$routeParams', 'sessionService',
    function ($scope, $http, toolService, $routeParams, oSessionService) {
        
        $scope.id = $routeParams.id;

        $http({
            method: 'GET',
            //withCredentials: true,
            url: 'http://localhost:8081/tailorShop/json?ob=tipousuario&op=get&id=' + $scope.id
        }).then(function (response) {
            $scope.status = response.status;
            $scope.ajaxData = response.data.message;
        }, function (response) {
            $scope.ajaxData = response.data.message || 'Request failed';
            $scope.status = response.status;
        });
        
        
        $scope.isActive = toolService.isActive;

    }
]);