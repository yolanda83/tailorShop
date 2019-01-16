'use strict'

moduleUsuario.controller('usuarioLoginController', ['$scope', '$http', 'toolService', '$location',
    'sessionService',
    function ($scope, $http, toolService, $location, oSessionService) {

        $scope.isActive = toolService.isActive;


        $scope.log = function () {
            $scope.error = false;

            var login = $scope.login;
            var pass = forge_sha256($scope.pass);

            $http({
                method: 'GET',
                header: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                url: 'http://localhost:8081/tailorShop/json?ob=usuario&op=login&user=' + login + '&pass=' + pass,
            }).then(function (response) {
                $scope.status = response.data.status;
                if ($scope.status == 200) {
                    $scope.logeado = true;
                    oSessionService.setUserName(response.data.message.login);
                    oSessionService.setId(response.data.message.id);
                    $scope.usuario = oSessionService.getUserName();
                    $scope.userId = oSessionService.getId();
                    oSessionService.setSessionActive();
                    $location.path('/home');
                } else {
                    $scope.error = true;
                    $scope.logeado = false;
                    $scope.usuario = "";
                }
            }), function (response) {
                $scope.ajaxDataUsuario = response.data.message || 'Request failed';
                $scope.status = response.status;
            }
        }


    }]);