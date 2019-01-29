'use strict'

moduleUsuario.controller('usuarioViewController', ['$scope', '$http', 'toolService', '$routeParams', 'sessionService', '$anchorScroll', '$location',
    function ($scope, $http, toolService, $routeParams, oSessionService, $anchorScroll, $location) {

        $anchorScroll();
        $scope.id = $routeParams.id;


        $http({
            method: 'GET',
            //withCredentials: true,
            url: 'http://localhost:8081/tailorShop/json?ob=usuario&op=get&id=' + $scope.id
        }).then(function (response) {
            if (response.data.status == 200) {
                $scope.status = response.status;
                $scope.ajaxData = response.data.message;
                $scope.admin = oSessionService.isAdmin();
            } else {
                $location.path("/home");
            }
        }, function (response) {
            $scope.ajaxData = response.data.message || 'Request failed';
            $scope.status = response.status;
        });



        $scope.isActive = toolService.isActive;

    }
]);