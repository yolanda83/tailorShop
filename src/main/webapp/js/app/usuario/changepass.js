"use strict";

moduleUsuario.controller("usuarioChangepassController", [
    "$scope",
    "$http",
    "$mdDialog",
    '$routeParams',
    function ($scope, $http, $mdDialog, $routeParams) {
        $scope.changed = true;
        $scope.userId = $routeParams.id;

        $scope.update = function () {
            var last_pass = forge_sha256($scope.last_pass);
            var new_pass = forge_sha256($scope.new_pass);
            var new_pass_verify = forge_sha256($scope.new_pass_verify);
            var userId = $scope.userId;

            if (new_pass !== new_pass_verify) {
                $scope.showAlert('Error', 'El nuevo password no coincide.');
            } else {
                $http({
                    method: 'GET',
                    header: {
                        'Content-Type': 'application/json;charset=utf-8'
                    },
                    url: `http://localhost:8081/tailorShop/json?ob=usuario&op=updatepass&newpass=${new_pass}&lastpass=${last_pass}&userId=${userId}`
                }).then(function (response) {
                    if (response.data.status == 500) {
                        $scope.showAlert('Error', 'Tu password actual no coincide.');
                    } else if (response.data.status == 401) {
                        $scope.showAlert('Error', 'Usuario no autorizado.');
                    } else {
                        $scope.changed = false;
                    }
                }), function (response) {

                }
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

    }])