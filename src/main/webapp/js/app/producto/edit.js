/* global moduleProducto */

'use strict';

moduleProducto.controller('productoEditController', ['$scope', '$http', 'toolService', '$routeParams', 'sessionService', '$anchorScroll',
    function ($scope, $http, toolService, $routeParams, oSessionService, $anchorScroll) {

        $anchorScroll();
        $scope.id = $routeParams.id;


        $http({
            method: "GET",
            url: 'http://localhost:8081/tailorShop/json?ob=producto&op=get&id=' + $scope.id
        }).then(function (response) {
            console.log(response);
            $scope.id = response.data.message.id;
            $scope.codigo = response.data.message.codigo;
            $scope.desc = response.data.message.desc;
            $scope.existencias = response.data.message.existencias;
            $scope.precio = response.data.message.precio;
            $scope.foto = response.data.message.foto;
            $scope.obj_tipoProducto_desc = response.data.message.obj_tipoProducto.desc;
            $scope.novedad = response.data.message.novedad;
            $scope.obj_tipoProducto = {
                id: response.data.message.obj_tipoProducto.id,
                desc: response.data.message.obj_tipoProducto.desc
            };
        }), function (response) {
            console.log(response);
        };

        $scope.isActive = toolService.isActive;

        $scope.guardar = function () {
            var foto;
            if ($scope.myFile !== undefined) {
                //Si el nombre de la imagen es "Foto" significa que es la de por defecto, se le deja intacta
                if ($scope.myFile.name === "foto.png") {
                    foto = $scope.myFile.name;
                    //Si la imagen que tenia el producto era la predefinida y me suben una nueva foto diferente.
                } else if ($scope.foto === "foto.png" && $scope.myFile.name !== "foto.png") {
                    foto = guid() + $scope.myFile.name;
                } else {
                    foto = $scope.foto;
                }
                uploadPhoto(foto);
            } else {
                foto = $scope.foto;
            }

            var fecha = new Date().toString();

            var json = {
                id: $scope.id,
                codigo: $scope.codigo,
                desc: $scope.desc,
                existencias: $scope.existencias,
                precio: $scope.precio,
                foto: foto,
                id_tipoProducto: $scope.obj_tipoProducto.id,
                novedad: $scope.novedad,
                fecha: fecha
            };

            $http({
                method: 'GET',
                header: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                url: 'http://localhost:8081/tailorShop/json?ob=producto&op=update',
                params: {json: JSON.stringify(json)}
            }).then(function (response) {
                $scope.status = response.status;
                if ($scope.status === 200) {
                    $scope.resultado = "El Producto ha sido actualizado correctamente.";
                    $scope.edit = true;
                    sleep(10000);
                } else {
                    $scope.resultado = "El Producto no se ha podido actualizar.";
                    $scope.edit = false;
                }
            }), function (response) {
                console.log(response);
            };
        };

//Anyadida esta funcion que realiza un retardo para que no haya problemas al mostrar la foto en la web
        function sleep(milliseconds) {
            var start = new Date().getTime();
            for (var i = 0; i < 1e7; i++) {
                if ((new Date().getTime() - start) > milliseconds) {
                    break;
                }
            }
        }

        $(".fotoEditar").on("click", function () {
            $("#prueba").trigger('click');
        });

        function uploadPhoto(name) {
            //Solucion mas cercana
            //https://stackoverflow.com/questions/37039852/send-formdata-with-other-field-in-angular
            var file = $scope.myFile;
            file = new File([file], name, {type: file.type});
            //Api FormData 
            //https://developer.mozilla.org/es/docs/Web/API/XMLHttpRequest/FormData
            var oFormData = new FormData();
            oFormData.append('file', file);
            $scope.userId = oSessionService.getId();
            $http({
                headers: {'Content-Type': undefined},
                method: 'POST',
                data: oFormData,
                url: `http://localhost:8081/tailorShop/json?ob=producto&op=addimage&id=` + $scope.userId
            });
        }

        function guid() {
            return "ss-s-s-s-sss".replace(/s/g, s4);
        }

        function s4() {
            return Math.floor((1 + Math.random()) * 0x10000)
                    .toString(16)
                    .substring(1);
        }

    }]).directive('fileModel', ['$parse', function ($parse) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                var model = $parse(attrs.fileModel);
                var modelSetter = model.assign;

                element.bind('change', function () {
                    scope.$apply(function () {
                        modelSetter(scope, element[0].files[0]);
                    });
                });
            }
        };
    }]);