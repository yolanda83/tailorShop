'use strict'

moduleUsuario.controller('usuarioViewController', ['$scope', '$http', 'toolService', '$routeParams', 'sessionService', '$anchorScroll', '$location',
    function ($scope, $http, toolService, $routeParams, oSessionService, $anchorScroll, $location) {

        $anchorScroll();
        $scope.id = $routeParams.id;

        if ($scope.id == null) {

            $location.path("/usuario/new");

        } else {

            $http({
                method: 'GET',
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
                $location.path("/home");
            });

        }

        $scope.isActive = toolService.isActive;

    }
]);