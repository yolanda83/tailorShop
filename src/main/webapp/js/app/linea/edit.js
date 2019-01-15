'use strict'

moduleLinea.controller('lineaEditController', ['$scope', '$http', 'toolService', '$routeParams', 'sessionService', '$anchorScroll',
    function ($scope, $http, toolService, $routeParams, oSessionService, $anchorScroll) {

        $anchorScroll();
        $scope.id = $routeParams.id;


//        //Chequeo sesión
//        if (oSessionService.getUserName() !== "") {
//            $scope.usuario = oSessionService.getUserName();
//            $scope.logeado = true;
//            $scope.userId = oSessionService.getId();
//        }


        $http({
            method: "GET",
            url: 'http://localhost:8081/tailorShop/json?ob=linea&op=get&id=' + $scope.id
        }).then(function (response) {
            console.log(response);
            $scope.id = response.data.message.id;
            $scope.cantidad = response.data.message.cantidad;
            $scope.obj_producto = response.data.message.obj_producto;
            $scope.id_factura = response.data.message.obj_factura.id;
        }), function (response) {
            console.log(response);
        };

        $scope.guardar = function () {
            var json = {
                id: $scope.id,
                cantidad: $scope.cantidad,
                id_producto: $scope.obj_producto.id,
                id_factura: $scope.id_factura,
            }

            $http({
                method: 'GET',
                header: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                url: 'http://localhost:8081/tailorShop/json?ob=linea&op=update',
                params: {json: JSON.stringify(json)}
            }).then(function (response) {
                $scope.status = response.status;
                if ($scope.status == 200) {
                    $scope.resultado = "La línea ha sido actualizada correctamente."
                    $scope.edit = true;
                } else {
                    $scope.resultado = "La línea no se ha podido actualizar."
                    $scope.edit = false;
                }
            }), function (response) {
                console.log(response);
            }
        }


        $scope.isActive = toolService.isActive;
    }]);