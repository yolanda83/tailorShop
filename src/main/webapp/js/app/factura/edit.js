'use strict'

moduleFactura.controller('facturaEditController', ['$scope', '$http', 'toolService', '$routeParams', 'sessionService', '$anchorScroll',
    function ($scope, $http, toolService, $routeParams, oSessionService, $anchorScroll) {

        $anchorScroll();
        $scope.id = $routeParams.id;


        $http({
            method: "GET",
            url: 'http://localhost:8081/tailorShop/json?ob=factura&op=get&id=' + $scope.id
        }).then(function (response) {
            $scope.id = response.data.message.id;
            $scope.fecha = response.data.message.fecha;
            $scope.iva = response.data.message.iva;
            $scope.user = response.data.message.obj_usuario.login;
            $scope.obj_usuario = response.data.message.obj_usuario;
            $scope.lineas = response.data.message.numLineas;


            //FORMATEO FECHAS A FORMATO ESPAÑOL
            var fecha = $scope.fecha;
            var diaN = "";
            var dia = fecha.substr(-8, 2);
            if (dia.charAt(0) == " ") {
                diaN = dia.replace(" ", "0");
            } else {
                diaN = dia;
            }
            var anyo = fecha.substr(-4);
            var mesN = "";
            var mesS = fecha.substr(0, 3);
            switch (mesS) {
                case "ene":
                    mesN = "01";
                    break;
                case "feb":
                    mesN = "02";
                    break;
                case "mar":
                    mesN = "03";
                    break;
                case "abr":
                    mesN = "04";
                    break;
                case "may":
                    mesN = "05";
                    break;
                case "jun":
                    mesN = "06";
                    break;
                case "jul":
                    mesN = "07";
                    break;
                case "ago":
                    mesN = "08";
                    break;
                case "sep":
                    mesN = "09";
                    break;
                case "oct":
                    mesN = "10";
                    break;
                case "nov":
                    mesN = "11";
                    break;
                case "dic":
                    mesN = "12";
                    break;
                default:

            }

            var fechaFormat = diaN + '/' + mesN + '/' + anyo;
            $scope.fecha = fechaFormat;


        }), function (response) {
            console.log(response);
        };


        $scope.guardar = function () {

//            var json = {
//                id: $scope.id,
//                fecha: $scope.fecha,
//                iva: $scope.iva,
//                obj_usuario: $scope.obj_usuario
//            }
            var json = {
                id: $scope.id,
                fecha: null,
                iva: $scope.iva,
                obj_usuario: {
                    id: $scope.obj_usuario.id
                },
                numLineas: $scope.lineas
            }
            $http({
                method: 'GET',
                header: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                url: 'http://localhost:8081/tailorShop/json?ob=factura&op=update',
                params: {json: JSON.stringify(json)}
            }).then(function (response) {
                if (response.status == 200) {
                    $scope.edit = true;
                } else {
                    $scope.edit = false;
                }
            }), function (response) {
                console.log(response);
            }
        }



        $scope.isActive = toolService.isActive;
    }]);