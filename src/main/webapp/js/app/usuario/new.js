/* global moduleUsuario, self */

'use strict';

moduleUsuario.controller('usuarioNewController', [
    '$scope', '$http', 'toolService', '$routeParams', 'sessionService', '$mdDialog', '$location',
    function ($scope, $http, toolService, $routeParams, sessionService, $mdDialog, $location) {

        $scope.numRegistros = 0;
        $scope.isActive = toolService.isActive;
        $scope.radiob = "tab-2";
        $scope.usuCreado = null;
        var self = this;


        $scope.log = function () {
            $scope.error = false;
            var login = $scope.login1;
            var pass = forge_sha256($scope.passAdmin);
            $http({
                method: 'GET',
                header: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                url: 'http://localhost:8081/tailorShop/json?ob=usuario&op=login&user=' + login + '&pass=' + pass
            }).then(function (response) {
                $scope.status = response.data.status;
                if ($scope.status === 200) {
                    $scope.logeado = true;
                    sessionService.setUserName(response.data.message.login);
                    sessionService.setId(response.data.message.id);
                    self.usuario = sessionService.getUserName();
                    self.userId = sessionService.getId();
                    sessionService.setSessionActive();
                    //Seteamos si es ADMIN o USUARIO
                    if (response.data.message.obj_tipoUsuario.id === 1) { //ADMIN
                        sessionService.setAdmin();
                    } else {
                        sessionService.setUser();
                    }
                    $location.path("/home");
                } else {
                    $scope.error = true;
                    $scope.showAlert('Error', 'Datos erroneos.');
                    $scope.logeado = false;
                    self.usuario = "";
                }
            }), function (response) {
                $scope.ajaxDataUsuario = response.data.message || 'Request failed';
                $scope.status = response.status;
            };
        };


        $scope.guardar = function () {

//            var pass1 = forge_sha256($scope.pass1);
//            var pass2 = forge_sha256($scope.pass2);
//
//            if (pass1 !== pass2) {
//                $scope.showAlert('Error', 'Los passwords no coinciden.');
//            }
//
            var foto = 'fotoUser.png';

            var json = {
                dni: $scope.dni,
                nombre: $scope.nombre,
                ape1: $scope.ape1,
                ape2: $scope.ape2,
                foto: foto,
                login: $scope.loginUser,
                pass: forge_sha256($scope.pass2),
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
//                $scope.showAlert('Registro', 'Usuario creado correctamente. Inicia sesion.');
                $scope.radiob = "tab-1";
                $scope.usuCreado = "Usuario creado correctamente. Inicia sesion.";
            }), function (response) {
                $scope.ajaxDataUsuario = response.data.message || 'Request failed';
                $scope.status = response.status;
                $scope.resultado = "No se ha podido crear el usuario.";
            }
        }

        $scope.showAlert = function (titulo, description) {
            $mdDialog.show(
                    $mdDialog.alert()
                    .clickOutsideToClose(false)
                    .title(titulo)
                    .textContent(description)
                    .ariaLabel('Alert Dialog Demo')
                    .ok('OK!')
                    );
        };

    }]);