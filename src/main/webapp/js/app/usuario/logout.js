/* global moduleUsuario */

'use strict';

moduleUsuario.controller('usuarioLogoutController', ['$scope', '$http', 'toolService', '$location', 'sessionService',
    function ($scope, $http, toolService, $location, oSessionService) {

        $http({
            method: "GET",
            url: 'http://localhost:8081/tailorShop/json?ob=usuario&op=logout'
        }).then(function (response) {

            $scope.status = response.status;
            oSessionService.setSessionInactive();
        }), function (response) {
            $scope.ajaxData = response.data.message || 'Request failed';
            $scope.status = response.status;
        };

        $scope.isActive = toolService.isActive;


        $scope.volver = function () {

            $location.path('/home');

        };

    }]);