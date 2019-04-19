'use strict'

moduleLinea.controller('lineaPlistController', ['$scope', '$http', '$location', 'toolService',
    'sessionService', '$routeParams',
    function ($scope, $http, $location, toolService, oSessionService, $routeParams) {

//Codigo que se ejecuta al arrancar la pagina
        $scope.ruta = $location.path();
        $scope.ob = "linea";
        $scope.op = "plist";
        $scope.id = $routeParams.id;
        $scope.user = $routeParams.userid;
        $scope.admin = oSessionService.isAdmin();

        if (!$routeParams.order) {
            $scope.orderURLServidor = "";
            $scope.orderURLCliente = "";
        } else {
            $scope.orderURLServidor = "&order=" + $routeParams.order;
            $scope.orderURLCliente = $routeParams.order;
        }

        if (!$routeParams.rpp) {
            $scope.rpp = 10;
        } else {
            $scope.rpp = $routeParams.rpp;
        }

        if (!$routeParams.page) {
            $scope.page = 1;
        } else {
            if ($routeParams.page >= 1) {
                $scope.page = $routeParams.page;
            } else {
                $scope.page = 1;
            }
        }

        //llamada a servidor con $http, trae el usuario completo sobre el cual estamos viendo sus lineas
        $http({
            method: 'GET',
            //withCredentials: true,
            url: 'http://localhost:8081/tailorShop/json?ob=usuario&op=get&id=' + $scope.user
        }).then(function (response) {
            $scope.status = response.status;
            $scope.ajaxDataUsuario = response.data.message; //Aqui vienen todos los datos del cliente en formato json
        }, function (response) {
            $scope.ajaxDataLinea = response.data.message || 'Request failed';
            $scope.status = response.status;
        });

        //getcount
        $http({
            method: 'GET',
            url: 'http://localhost:8081/tailorShop/json?ob=linea&op=getcount&id=' + $scope.id
        }).then(function (response) {
            $scope.status = response.status;
            $scope.ajaxDataLineaNumber = response.data.message;
            $scope.totalPages = Math.ceil($scope.ajaxDataLineaNumber / $scope.rpp);

            if ($scope.page > $scope.totalPages && $scope.totalPages > 0) {
                $scope.page = $scope.totalPages;
                $scope.update();
            }

            pagination2();
        }, function (response) {
            $scope.ajaxDataLineaNumber = response.data.message || 'Request failed';
            $scope.status = response.status;
        });

        //Getpage trae todos los registros de linea de la BBDD con ID de la factura relleno
//        if ($scope.id != null) {
        $http({
            method: 'GET',
            //withCredentials: true,
            url: 'http://localhost:8081/tailorShop/json?ob=linea&op=getpage&rpp=' + $scope.rpp + '&page=' + $scope.page + '&id=' + $scope.id + '&userId=' + $scope.user + $scope.orderURLServidor
        }).then(function (response) {
            if (response.data.status == 200) {
                $scope.status = response.status;
                $scope.ajaxDataLinea = response.data.message;
            } else {
                $location.path("/home");
            }
        }, function (response) {
            $scope.ajaxDataLinea = response.data.message || 'Request failed';
            $scope.status = response.status;
        });

//A PARTIR DE AQUI SON FUNCIONES QUE SE EJECUTAN CUANDO SE PULSA EL BOTÓN ADECUADO EN EL HTML
        $scope.resetOrder = function () {
            if ($scope.id == null) {
                $location.url('linea/plist/' + $scope.rpp + '/' + $scope.page);
            } else {
                $location.url('linea/plist/' + $scope.rpp + '/' + $scope.page + '/' + $scope.id + '/' + $scope.user);
            }
        }

        $scope.ordena = function (order, align) {
            if ($scope.orderURLServidor == "") {
                $scope.orderURLServidor = "&order=" + order + "," + align;
                $scope.orderURLCliente = order + "," + align;
            } else {
                $scope.orderURLServidor = $scope.orderURLServidor + "-" + order + "," + align;
                $scope.orderURLCliente = $scope.orderURLCliente + "-" + order + "," + align;
            }

            if ($scope.id == null) {
                $location.url('linea/plist/' + $scope.rpp + '/' + $scope.page + '/' + $scope.orderURLCliente);
            } else {
                $location.url('linea/plist/' + $scope.rpp + '/' + $scope.page + '/' + $scope.id + '/' + $scope.user + '/' + $scope.orderURLCliente);
            }
        }


        //paginacion neighbourhood
        function pagination2() {
            $scope.list2 = [];
            $scope.neighborhood = 3;
            for (var i = 1; i <= $scope.totalPages; i++) {
                if (i === $scope.page) {
                    $scope.list2.push(i);
                } else if (i <= $scope.page && i >= ($scope.page - $scope.neighborhood)) {
                    $scope.list2.push(i);
                } else if (i >= $scope.page && i <= ($scope.page - -$scope.neighborhood)) {
                    $scope.list2.push(i);
                } else if (i === ($scope.page - $scope.neighborhood) - 1) {
                    $scope.list2.push("...");
                } else if (i === ($scope.page - -$scope.neighborhood) + 1) {
                    $scope.list2.push("...");
                }
            }
        }

        $scope.update = function () {
            if ($scope.orderURLCliente != "") {
                $location.url('linea/plist/' + $scope.rpp + '/' + $scope.page + '/' + $scope.id + '/' + $scope.user + '/' + $scope.orderURLCliente);
            } else {
                $location.url('linea/plist/' + $scope.rpp + '/' + $scope.page + '/' + $scope.id + '/' + $scope.user);
            }
        }

        $scope.isActive = toolService.isActive;

    }
]);
