'use strict'

moduleUsuario.controller('usuarioEditAdminController', ['$scope', '$http', 'toolService',
    '$routeParams', 'sessionService', '$anchorScroll',
    function ($scope, $http, toolService, $routeParams, oSessionService, $anchorScroll) {

        $anchorScroll();
        $scope.id = $routeParams.id;

        $http({
            method: "GET",
            url: 'http://localhost:8081/tailorShop/json?ob=usuario&op=get&id=' + $scope.id
        }).then(function (response) {
            console.log(response);
            $scope.id = response.data.message.id;
            $scope.dni = response.data.message.dni;
            $scope.nombre = response.data.message.nombre;
            $scope.ape1 = response.data.message.ape1;
            $scope.ape2 = response.data.message.ape2;
            $scope.loginAdmin = response.data.message.login;
            $scope.pass = forge_sha256(response.data.message.pass);
            $scope.obj_tipoUsuario_desc = response.data.message.obj_tipoUsuario.desc;
            $scope.obj_tipoUsuario = {
                id: response.data.message.obj_tipoUsuario.id,
                desc: response.data.message.obj_tipoUsuario.desc
            }
        }), function (response) {
            console.log(response);
        };


        $scope.guardar = function () {

            var json = {
                id: $scope.id,
                dni: $scope.dni,
                nombre: $scope.nombre,
                ape1: $scope.ape1,
                ape2: $scope.ape2,
                login: $scope.loginAdmin,
                pass: forge_sha256($scope.pass),
                id_tipoUsuario: $scope.obj_tipoUsuario.id
            }
            $http({
                method: 'GET',
                header: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                url: 'http://localhost:8081/tailorShop/json?ob=usuario&op=update',
                params: {json: JSON.stringify(json)}
            }).then(function (data, response) {
                console.log(data, response);
                $scope.edit = true;
            }), function (response) {
                console.log(response);
            }
        }

        $scope.isActive = toolService.isActive;

    }]);
