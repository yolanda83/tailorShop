/* global moduleCommon */

'use strict';

moduleContacto.controller('contactoController', ['$scope', '$location', 'toolService', "$mdDialog",
    function ($scope, $location, toolService, $mdDialog) {

        $scope.ruta = $location.path();

        $scope.isActive = toolService.isActive;

        $scope.enviar = function () {
            $scope.showAlert('Contacto', 'E-Mail enviado correctamente :)');
            $location.path('/home');
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