/* global moduleCommon */

'use strict';
moduleCommon.controller('homeController', ['$scope', '$location', 'toolService', '$http', "$mdDialog", "countcarritoService",
    function ($scope, $location, toolService, $http, $mdDialog, countcarritoService) {

        $scope.ruta = $location.path();
//        $scope.logeado = false;
        $scope.isActive = toolService.isActive;
        $(document).ready(function () {
            setTimeout(function () {
                $("#main").removeClass("is-loading");
            }, 100);
        });
        $http({
            method: 'GET',
            //withCredentials: true,
            url: 'http://localhost:8081/tailorShop/json?ob=producto&op=getnovedad'
        }).then(function (response) {
            $scope.status = response.status;
            $scope.ajaxDataNovedades = response.data.message;
            var length = $scope.ajaxDataNovedades.length;
            $scope.ajaxDataTotalNov = [];
            if (length < 8) {

                for (var i = 0; i < length; i++) {
                    $scope.ajaxDataTotalNov.push($scope.ajaxDataNovedades[i]);
                }

            } else {

                for (var i = 0; i <= 7; i++) {
                    $scope.ajaxDataTotalNov.push($scope.ajaxDataNovedades[i]);
                }

            }

        }, function (response) {
            $scope.ajaxDataNovedades = response.data.message || 'Request failed';
            $scope.status = response.status;
        });

        $scope.save = function (producto) {
            $http({
                method: 'GET',
                url: `http://localhost:8081/tailorShop/json?ob=carrito&op=add&id=${producto.id}&cant=1`
            }).then(function (response) {
                if (response.data.status == 200) {
                    $scope.showAlert('Carrito', 'Producto agregado correctamente :)');
                    countcarritoService.updateCarrito();
                } else if (response.data.status == 401) {
                    $scope.showAlert('Carrito', 'Debes loguearte para agregar productos al carrito.')
                }
            }, function (response) {
                $scope.showAlert('Error', response.data.message);
            });
        }

        $scope.checkFav = function (producto) {
            $http({
                method: 'GET',
                url: `http://localhost:8081/tailorShop/json?ob=producto&op=checkFav&id=${producto.id}`
            }).then(function (response) {
                if (response.data.status == 200) {
                    saveFav(producto);
                } else if (response.data.status == 500) {
                    $scope.showAlert('Favorito', 'Este producto ya estaba en tu Lista de Favoritos :)');
                } else {
                    $scope.showAlert('Favorito', 'Debes loguearte para agregar favoritos :)');
                }
            }, function (response) {
                $scope.showAlert('Error', response.data.message);
            });
        }

        function saveFav(producto) {
            $http({
                method: 'GET',
                url: `http://localhost:8081/tailorShop/json?ob=producto&op=addFav&id=${producto.id}`
            }).then(function (response) {
                if (response.data.status == 200) {
                    $scope.showAlert('Favorito', 'Producto agregado correctamente a la Lista de Favoritos :)');
                } else {
                    $scope.showAlert('Favorito', 'Debes loguearte para agregar favoritos :)');
                }
            }, function (response) {
                $scope.showAlert('Error', response.data.message);
            });
        }

        //Este mensaje se puede mejorar, buscar info en la api oficial de angular material
        //https://material.angularjs.org/latest/api/service/$mdDialog
        //https://ajax.googleapis.com/ajax/libs/angular_material/1.1.8/angular-material.css
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