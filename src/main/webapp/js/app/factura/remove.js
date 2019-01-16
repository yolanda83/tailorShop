'use strict'

moduleFactura.controller('facturaRemoveController', ['$scope', '$http', 'toolService', '$routeParams', 'sessionService', '$anchorScroll',
    function ($scope, $http, toolService, $routeParams, oSessionService, $anchorScroll) {
        
        $anchorScroll();
        $scope.id = $routeParams.id;


//        //Chequeo sesión
//        if (oSessionService.getUserName() !== "") {
//            $scope.usuario = oSessionService.getUserName();
//            $scope.logeado = true;
//            $scope.userId = oSessionService.getId();
//        }
        
        $scope.deleted = false;

        //Muestra los datos del id factura indicado de la BBDD
        $http({
            method: 'GET',
            //withCredentials: true,
            url: 'http://localhost:8081/tailorShop/json?ob=factura&op=get&id=' + $scope.id
        }).then(function (response) {
            $scope.status = response.status;
            $scope.ajaxData = response.data.message;

            //FORMATEO FECHAS A FORMATO ESPAÑOL
            var fecha = $scope.ajaxData["fecha"];
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
            $scope.ajaxData["fecha"] = fechaFormat;

        }, function (response) {
            $scope.ajaxData = response.data.message || 'Request failed';
            $scope.status = response.status;
        });


        $scope.eliminar = function () {
            $http({
                method: "GET",
                url: `http://localhost:8081/tailorShop/json?ob=factura&op=remove&id=` + $scope.id
            }).then(function (response) {
                $scope.deleted = true;
            })
        }

        $scope.isActive = toolService.isActive;

    }
]);