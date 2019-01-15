'use strict'

moduleFactura.controller('facturaNewController', ['$scope', '$http', 'toolService', '$routeParams', 'sessionService',
    function ($scope, $http, toolService, $routeParams, oSessionService) {

        $scope.numRegistros = 0;

//        //Chequeo sesión
//        if (oSessionService.getUserName() !== "") {
//            $scope.usuario = oSessionService.getUserName();
//            $scope.logeado = true;
//            $scope.userId = oSessionService.getId();
//        }

        $scope.isActive = toolService.isActive;


        $scope.guardar = function () {           
            
            if($scope.fecha == undefined){
                $scope.fecha = new Date();
            }

            var json = {
                fecha: $scope.fecha,
                iva: $scope.iva,
                obj_usuario: {
                    id: $scope.obj_usuario.id
                }
            }
            $http({
                method: 'GET',
                header: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                url: 'http://localhost:8081/tailorShop/json?ob=factura&op=create',
                params: {json: JSON.stringify(json)}
            }).then(function (response) {
                console.log(response);
                $scope.resultado = "Factura creada correctamente.";
                $scope.new = true;
            }), function (response) {
                console.log(response);
                $scope.ajaxDataUsuario = response.data.message || 'Request failed';
                $scope.status = response.status;
                $scope.resultado = "No se ha podido crear la factura.";
            }
        }


    }]);