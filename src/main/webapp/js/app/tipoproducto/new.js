'use strict'

moduleTipoproducto.controller('tipoproductoNewController', ['$scope', '$http', 'toolService', '$routeParams', 'sessionService',
    function ($scope, $http, toolService, $routeParams, oSessionService) {

        $scope.numRegistros = 0;

        //Getpage trae todos los tipos de usuarios existentes en la BBDD
        $http({
            method: "GET",
            url: 'http://localhost:8081/tailorShop/json?ob=tipoproducto&op=getpage&rpp=100&page=1'
        }).then(function (response) {
            console.log(response);
            $scope.status = response.status;
            $scope.ajaxData = response.data.message;
            $scope.numRegistros = $scope.ajaxData.length;
            $scope.arrayTipos = [];
            var desc = "";
            for (var i = 0; i < $scope.numRegistros; i++) {
                desc = $scope.ajaxData[i]["desc"];
                $scope.arrayTipos.push(desc);
            }
        }), function (response) {
            console.log(response);
            $scope.ajaxData = response.data.message || 'Request failed';
            $scope.status = response.status;
        };

        $scope.guardar = function () {

            var descTipoProducto = $scope.selectedId;
            for (var i = 0; i < $scope.numRegistros; i++) {
                var descrip = $scope.ajaxData[i]["desc"];
                if (descTipoProducto == descrip) {
                    var idTU = $scope.ajaxData[i]["id"];
                }
            }
            var json = {
                desc: $scope.desc
            }
            $http({
                method: 'GET',
                header: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                url: 'http://localhost:8081/tailorShop/json?ob=tipoproducto&op=create',
                params: {json: JSON.stringify(json)}
            }).then(function (response) {
                $scope.status = response.status;
                if ($scope.status == 200) {
                    $scope.resultado = "Tipo Producto creado correctamente.";
                    $scope.new = true;
                } else {
                    $scope.resultado = "No se ha podido crear el Tipo Producto.";
                    $scope.new = false;
                }
            }), function (response) {
                console.log(response);
                $scope.ajaxDataProducto = response.data.message || 'Request failed';
                $scope.status = response.status;
                $scope.resultado = "No se ha podido crear el Tipo Producto.";
            }
        }

        $scope.isActive = toolService.isActive;

    }]);