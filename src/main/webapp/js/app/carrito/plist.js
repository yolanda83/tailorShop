'use strict'

moduleCarrito.controller('carritoPlistController', ['$scope', '$http', '$location', 'toolService',
    'sessionService', '$routeParams', "countcarritoService", "$mdDialog",
    function ($scope, $http, $location, toolService, oSessionService, $routeParams, countcarritoService, $mdDialog) {

        $scope.ruta = $location.path();
        $scope.ob = "linea";
        $scope.op = "plist";
        $scope.totalPages = 1;
//        $scope.id = $routeParams.id;
        $scope.compra = true;
//        $scope.warning = null;
        $scope.carritoVacio = false;
        $scope.carrito = parseInt(oSessionService.getCountCarrito());


        if (isNaN($scope.carrito) || $scope.carrito == 0) {
            $scope.carrito = 0;
        } else {

            oSessionService.registerObserverCallback(function () {
                $scope.carrito = oSessionService.getCountCarrito();
            });
        }

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
                $scope.compra = false;
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
                $scope.total = $scope.total.toFixed(2);
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
                $scope.compra = true;
                $scope.ajaxDataCarrito = 0;
//                $scope.ajaxDataCarrito = "";
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
                $scope.compra = true;
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
                    var auxCant = 0;
                    var auxPrecio = 0;
                    var totalInicial = 0;

                    if ($scope.ajaxDataCarrito.length != null && $scope.ajaxDataCarrito != "Unauthorized") {
                        for (var i = 0; i < $scope.ajaxDataCarrito.length; i++) {
                            auxCant = $scope.ajaxDataCarrito[i].cantidad;
                            auxPrecio = $scope.ajaxDataCarrito[i].obj_producto.precio;

                            totalInicial += auxCant * auxPrecio;
                        }
                    }
                    $scope.total = Math.round(totalInicial * 100) / 100;
                    $scope.total = $scope.total.toFixed(2);


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

                if ($scope.ajaxDataCarrito == null) { //borrar el último producto que queda en el carrito
//                    $scope.warning = response.data.message;
                    $scope.compra = true;
                } else {
//                    $scope.warning = response.data.message;
                    var auxCant = 0;
                    var auxPrecio = 0;
                    var totalInicial = 0;

                    if ($scope.ajaxDataCarrito.length != null && $scope.ajaxDataCarrito != "Unauthorized") {
                        for (var i = 0; i < $scope.ajaxDataCarrito.length; i++) {
                            auxCant = $scope.ajaxDataCarrito[i].cantidad;
                            auxPrecio = $scope.ajaxDataCarrito[i].obj_producto.precio;

                            totalInicial += auxCant * auxPrecio;
                        }
                    }
                    $scope.total = Math.round(totalInicial * 100) / 100;
                    $scope.total = $scope.total.toFixed(2);
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

                if ($scope.ajaxDataCarrito == null) {
                    $scope.compra = true;
                } else {
                    //calculamos el total actualizado
                    var auxCant = 0;
                    var auxPrecio = 0;
                    var totalInicial = 0;

                    if ($scope.ajaxDataCarrito.length != null && $scope.ajaxDataCarrito != "Unauthorized") {
                        for (var i = 0; i < $scope.ajaxDataCarrito.length; i++) {
                            auxCant = $scope.ajaxDataCarrito[i].cantidad;
                            auxPrecio = $scope.ajaxDataCarrito[i].obj_producto.precio;

                            totalInicial += auxCant * auxPrecio;
                        }
                    }
                    $scope.total = Math.round(totalInicial * 100) / 100;
                    $scope.total = $scope.total.toFixed(2);
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
                    $scope.showAlert('Muchas gracias!', 'Se ha realizado la compra correctamente');
                    $scope.compra = true;
//                    $scope.warning = "Oh no. ¡Tu carro de la compra está vacío!";                    
                } else if (response.data.status == 400) {
                    $scope.warning = response.data.message;
                }

            }), function (response) {
                console.log(response);
            }
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


    }
]);
