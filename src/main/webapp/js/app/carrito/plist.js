'use strict'

moduleCarrito.controller('carritoPlistController', ['$scope', '$http', '$location', 'toolService',
    'sessionService', '$routeParams', "countcarritoService", "$mdDialog",
    function ($scope, $http, $location, toolService, oSessionService, $routeParams, countcarritoService, $mdDialog) {

        $scope.ruta = $location.path();
        $scope.ob = "linea";
        $scope.op = "plist";
        $scope.totalPages = 1;
//        $scope.id = $routeParams.id;
        $scope.warning = null;
        $scope.carritoVacio = false;


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



        $http({
            method: 'GET',
            url: `http://localhost:8081/tailorShop/json?ob=carrito&op=show`
        }).then(function (response) {
            if (response.data.message != null) {
                $scope.productos = response.data.message;
                $scope.total = 0;
                var auxCant = 0;
                var auxPrecio = 0;
                var totalInicial = 0;
                var respuesta = response.data.message;
                if (response.data.message.length != null && respuesta != "Unauthorized") {
                    for (var i = 0; i < response.data.message.length; i++) {
                        auxCant = response.data.message[i].cantidad;
                        auxPrecio = response.data.message[i].obj_producto.precio;

                        totalInicial += auxCant * auxPrecio;
                    }
                } else {
                    $scope.carritoVacio = true;
                }
                $scope.total = Math.round(totalInicial * 100) / 100;
            } else {
                $scope.carritoVacio = true;
            }
        }, function (response) {
            $scope.status = response.status;
            $scope.ajaxDataUsuarios = response.data.message || 'Request failed';
        });


        $scope.advancedSearch = function () {
            if ($scope.advanced == false) {
                $scope.advanced = true;
            } else {
                $scope.advanced = false;
            }
        }

////TRAER DATOS USUARIO
//        $http({
//            method: 'GET',
//            //withCredentials: true,
//            url: 'http://localhost:8081/tailorShop/json?ob=usuario&op=get&id=' + $scope.id
//        }).then(function (response) {
//            $scope.status = response.status;
//            $scope.ajaxDataUsuario = response.data.message;
//        }, function (response) {
//            $scope.ajaxDataUsuario = response.data.message || 'Request failed';
//            $scope.status = response.status;
//        });

//MOSTRAR CARRITO
        $http({
            method: 'GET',
            //withCredentials: true,
            url: 'http://localhost:8081/tailorShop/json?ob=carrito&op=show'
        }).then(function (response) {
            $scope.status = response.status;

            if (response.data.message == "Unauthorized") {
                $scope.ajaxDataCarrito = "";
            } else {
                $scope.ajaxDataCarrito = response.data.message;
            }
        }, function (response) {
            $scope.ajaxDataCarrito = response.data.message || 'Request failed';
            $scope.status = response.status;
        });



//VACIAR CARRITO
        $scope.empty = function () {
            $http({
                method: 'GET',
                header: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                url: 'http://localhost:8081/tailorShop/json?ob=carrito&op=empty',
            }).then(function (response) {
                countcarritoService.updateCarrito();
                $scope.ajaxDataCarrito = response.data.message;
                console.log(response);
            }), function (response) {
                console.log(response);
            }
        }

        //AÑADIR CANTIDAD A UN PRODUCTO
        $scope.add = function (producto, cantidad) {

            $http({
                method: 'GET',
                header: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                url: `http://localhost:8081/tailorShop/json?ob=carrito&op=add&id=` + producto + `&cant=` + cantidad
            }).then(function (response) {
                countcarritoService.updateCarrito();
                if (response.data.status == 200 && response.data.message !== null) {
                    $scope.ajaxDataCarrito = response.data.message;
                } else if (response.data.status == 400) {
                    $scope.warning = response.data.message;
                }

            }), function (response) {
                console.log(response);
            }
        }

        //REDUCIR CANTIDAD A UN PRODUCTO
        $scope.reduce = function (producto, cantidad) {

            $http({
                method: 'GET',
                header: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                url: `http://localhost:8081/tailorShop/json?ob=carrito&op=reduce&id=` + producto + `&cant=` + cantidad
            }).then(function (response) {
                console.log(response);
                $scope.ajaxDataCarrito = response.data.message;
                countcarritoService.updateCarrito();
                if (response.data.status === 200) { //borrar el último producto que queda en el carrito
                    $scope.warning = response.data.message;
                } else {
                    $scope.warning = response.data.message;
                }

            }), function (response) {
                console.log(response);
            };
        };

        //BORRAR UN PRODUCTO
        $scope.borrar = function (producto) {

            $http({
                method: 'GET',
                header: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                url: `http://localhost:8081/tailorShop/json?ob=carrito&op=remove&id=` + producto
            }).then(function (response) {
                countcarritoService.updateCarrito();
                console.log(response);
                if (response.data.status == 200) {
                    $scope.ajaxDataCarrito = response.data.message;
                } else if (response.data.status == 201) { //borrar el último producto que queda en el carrito
                    $scope.ajaxDataCarrito = null;
                    $scope.warning = response.data.message;
                } else if (response.data.status == 400) {
                    $scope.warning = response.data.message;
                }

            }), function (response) {
                console.log(response);
            }
        }

        //COMPRAR PRODUCTOS
        $scope.buy = function (producto) {

            $http({
                method: 'GET',
                header: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                url: 'http://localhost:8081/tailorShop/json?ob=carrito&op=buy'
            }).then(function (response) {
                countcarritoService.updateCarrito();
                console.log(response);
                if (response.data.status == 200) {
                    $scope.ajaxDataCarrito = response.data.message;
                    $scope.warning = "Productos comprados correctamente. Gracias por tu pedido :)";
                    $scope.compra = true;
                } else if (response.data.status == 400) {
                    $scope.warning = response.data.message;
                }

            }), function (response) {
                console.log(response);
            }
        }

//
//        $scope.resetOrder = function () {
//            if ($scope.id == null) {
//                $location.url('linea/plist/' + $scope.rpp + '/' + $scope.page);
//            } else {
//                $location.url('linea/plist/' + $scope.rpp + '/' + $scope.page + '/' + $scope.id + '/' + $scope.user);
//            }
//        }

//
//        $scope.ordena = function (order, align) {
//            if ($scope.orderURLServidor == "") {
//                $scope.orderURLServidor = "&order=" + order + "," + align;
//                $scope.orderURLCliente = order + "," + align;
//            } else {
//                $scope.orderURLServidor = $scope.orderURLServidor + "-" + order + "," + align;
//                $scope.orderURLCliente = $scope.orderURLCliente + "-" + order + "," + align;
//            }
//
//            if ($scope.id == null) {
//                $location.url('linea/plist/' + $scope.rpp + '/' + $scope.page + '/' + $scope.orderURLCliente);
//            } else {
//                $location.url('linea/plist/' + $scope.rpp + '/' + $scope.page + '/' + $scope.id + '/' + $scope.user + '/' + $scope.orderURLCliente);
//            }
//        }


        //getcount
//        $http({
//            method: 'GET',
//            url: 'http://localhost:8081/tailorShop/json?ob=linea&op=getcount'
//        }).then(function (response) {
//            $scope.status = response.status;
//            $scope.ajaxDataLineaNumber = response.data.message;
//            $scope.totalPages = Math.ceil($scope.ajaxDataLineaNumber / $scope.rpp);
//            if ($scope.page > $scope.totalPages) {
//                $scope.page = $scope.totalPages;
//                $scope.update();
//            }
//            pagination2();
//        }, function (response) {
//            $scope.ajaxDataLineaNumber = response.data.message || 'Request failed';
//            $scope.status = response.status;
//        });


//        //paginacion neighbourhood
//        function pagination2() {
//            $scope.list2 = [];
//            $scope.neighborhood = 3;
//            for (var i = 1; i <= $scope.totalPages; i++) {
//                if (i === $scope.page) {
//                    $scope.list2.push(i);
//                } else if (i <= $scope.page && i >= ($scope.page - $scope.neighborhood)) {
//                    $scope.list2.push(i);
//                } else if (i >= $scope.page && i <= ($scope.page - -$scope.neighborhood)) {
//                    $scope.list2.push(i);
//                } else if (i === ($scope.page - $scope.neighborhood) - 1) {
//                    $scope.list2.push("...");
//                } else if (i === ($scope.page - -$scope.neighborhood) + 1) {
//                    $scope.list2.push("...");
//                }
//            }
//        }



        $scope.isActive = toolService.isActive;

//---------------------------------------------------------------------------
//
//        $scope.ruta = $location.path();
//        $scope.id = $routeParams.id;
//        $scope.user = $routeParams.user;


//        $scope.lineaLimpiar = function () {
//            $scope.ajaxDataLinea = "";
//        }

//        $scope.crearLinea = function () {
//            $http({
//                method: 'GET',
//                withCredentials: true,
//                url: 'http://localhost:8081/tailorShop/json?ob=linea&op=create'
//            }).then(function (response) {
//                $scope.status = response.status;
//                $scope.ajaxDataProductos = response.data.message;
//            }, function (response) {
//                $scope.ajaxDataProductos = response.data.message || 'Request failed';
//                $scope.status = response.status;
//            });
//        }

//        $http({
//            method: 'GET',
//            //withCredentials: true,
//            url: 'http://localhost:8081/tailorShop/json?ob=linea&op=getcount'
//        }).then(function (response) {
//            $scope.status = response.status;
//            $scope.ajaxDataFacturaNumber = response.data.message;
//        }, function (response) {
//            $scope.ajaxDataFacturaNumber = response.data.message || 'Request failed';
//            $scope.status = response.status;
//        });





    }
]);
