'use strict'

moduleLinea.controller('lineaViewController', ['$scope', '$http', 'toolService', '$routeParams', 'sessionService', '$anchorScroll',
    function ($scope, $http, toolService, $routeParams, oSessionService, $anchorScroll) {
        
        $anchorScroll();
        $scope.id = $routeParams.id;

        $http({
            method: 'GET',
            //withCredentials: true,
            url: 'http://localhost:8081/tailorShop/json?ob=linea&op=get&id=' + $scope.id
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