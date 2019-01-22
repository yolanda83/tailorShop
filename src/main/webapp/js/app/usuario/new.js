'use strict'

moduleUsuario.controller('usuarioNewController', ['$scope', '$http', 'toolService', '$routeParams', 'sessionService',
    function ($scope, $http, toolService, $routeParams, oSessionService) {

        $scope.numRegistros = 0;
        $scope.isActive = toolService.isActive;


        $scope.guardar = function () {

            var json = {
                dni: $scope.dni,
                nombre: $scope.nombre,
                ape1: $scope.ape1,
                ape2: $scope.ape2,
                login: $scope.loginUser,
                pass: forge_sha256($scope.pass),
                id_tipoUsuario: 2
            }
            $http({
                method: 'GET',
                header: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                url: 'http://localhost:8081/tailorShop/json?ob=usuario&op=create',
                params: {json: JSON.stringify(json)}
            }).then(function (response) {
                $scope.resultado = "Usuario creado correctamente.";
                $scope.new = true;
            }), function (response) {
                $scope.ajaxDataUsuario = response.data.message || 'Request failed';
                $scope.status = response.status;
                $scope.resultado = "No se ha podido crear el usuario.";
            }
        }


    }]);