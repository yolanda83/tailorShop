'use strict'

moduleProducto.controller('productoPlistController', ['$scope', '$http', '$location', 'toolService',
    'sessionService', '$routeParams',
    function ($scope, $http, $location, toolService, oSessionService, $routeParams) {

        $scope.ruta = $location.path();
        $scope.ob = "producto";
        $scope.op = "plist";
        $scope.totalPages = 1;


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

        //Getpage trae todos los registros de productos de la BBDD
        $http({
            method: 'GET',
            //withCredentials: true,
            url: 'http://localhost:8081/tailorShop/json?ob=producto&op=getpage&rpp=' + $scope.rpp + '&page=' + $scope.page + $scope.orderURLServidor
        }).then(function (response) {
            $scope.status = response.status;
            $scope.ajaxDataProductos = response.data.message;
        }, function (response) {
            $scope.ajaxDataProductos = response.data.message || 'Request failed';
            $scope.status = response.status;
        });
//        }



        $scope.resetOrder = function () {
            $location.url('producto/plist/' + $scope.rpp + '/' + $scope.page);
        }


        $scope.ordena = function (order, align) {
            if ($scope.orderURLServidor == "") {
                $scope.orderURLServidor = "&order=" + order + "," + align;
                $scope.orderURLCliente = order + "," + align;
            } else {
                $scope.orderURLServidor = $scope.orderURLServidor + "-" + order + "," + align;
                $scope.orderURLCliente = $scope.orderURLCliente + "-" + order + "," + align;
            }
            $location.url('producto/plist/' + $scope.rpp + '/' + $scope.page + '/' + $scope.orderURLCliente);
        }


        //getcount
        $http({
            method: 'GET',
            url: 'http://localhost:8081/tailorShop/json?ob=producto&op=getcount'
        }).then(function (response) {
            $scope.status = response.status;
            $scope.ajaxDataProductosNumber = response.data.message;
            $scope.totalPages = Math.ceil($scope.ajaxDataProductosNumber / $scope.rpp);
            if ($scope.page > $scope.totalPages) {
                $scope.page = $scope.totalPages;
                $scope.update();
            }
            pagination2();
        }, function (response) {
            $scope.ajaxDataProductosNumber = response.data.message || 'Request failed';
            $scope.status = response.status;
        });


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
            $location.url('producto/plist/' + $scope.rpp + '/' + $scope.page + '/' + $scope.orderURLCliente);
        }


        $scope.isActive = toolService.isActive;

    }
]);
