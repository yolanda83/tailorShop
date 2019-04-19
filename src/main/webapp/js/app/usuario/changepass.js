"use strict";

moduleUsuario.controller("usuarioChangepassController", [
    "$scope",
    "$http",
    "$mdDialog",
    function ($scope, $http, $mdDialog) {
        $scope.changed = true;

        $scope.update = function () {
            var last_pass = forge_sha256($scope.last_pass);
            var new_pass = forge_sha256($scope.new_pass);
            var new_pass_verify = forge_sha256($scope.new_pass_verify);

            if (new_pass !== new_pass_verify) {
                $scope.showAlert('Error', 'El nuevo password no coincide.');
            } else {
                $http({
                    method: 'GET',
                    header: {
                        'Content-Type': 'application/json;charset=utf-8'
                    },
                    url: `http://localhost:8081/tailorShop/json?ob=usuario&op=updatepass&newpass=${new_pass}&lastpass=${last_pass}`
                }).then(function (response) {
                    if (response.data.status == 500) {
                        $scope.showAlert('Error', 'Tu password actual no coincide.');
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