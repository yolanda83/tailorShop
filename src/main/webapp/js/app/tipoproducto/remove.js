'use strict'

moduleTipoproducto.controller('tipoproductoRemoveController', ['$scope', '$http', 'toolService', 
    '$routeParams', 'sessionService',
    function ($scope, $http, toolService, $routeParams, oSessionService) {
        $scope.id = $routeParams.id;

        $scope.deleted = false;

        $http({
            method: 'GET',
            url: 'http://localhost:8081/tailorShop/json?ob=tipoproducto&op=get&id=' + $scope.id
        }).then(function (response) {
            $scope.status = response.status;
            $scope.ajaxData = response.data.message;
        }, function (response) {
            $scope.ajaxData = response.data.message || 'Request failed';
            $scope.status = response.status;
        });

        $scope.eliminar = function () {
            $http({
                method: "GET",
                url: `http://localhost:8081/tailorShop/json?ob=tipoproducto&op=remove&id=${$routeParams.id}`
            }).then(function (response) {
                $scope.deleted = true;
            })
        }

        $scope.isActive = toolService.isActive;

    }
]);