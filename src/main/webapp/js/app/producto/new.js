'use strict'

moduleProducto.controller('productoNewController', ['$scope', '$http', 'toolService', '$routeParams', 'sessionService', '$timeout',
    function ($scope, $http, toolService, $routeParams, oSessionService, $timeout) {

        $scope.numRegistros = 0;
        $scope.novedad = true;
        $scope.obj_tipoProducto = {
            id: null,
            desc: null
        }

        $scope.isActive = toolService.isActive;

        $scope.guardar = function () {

            var foto;
            if ($scope.myFile !== undefined) {
                foto = guid() + $scope.myFile.name;
                uploadPhoto(foto);
                $scope.foto = foto;
            } else {
                $scope.foto = "foto.png";
            }

            var fecha = new Date().toString();

            var json = {
                id: $scope.id,
                codigo: $scope.codigo,
                desc: $scope.desc,
                existencias: $scope.existencias,
                precio: $scope.precio,
                foto: $scope.foto,
                id_tipoProducto: $scope.obj_tipoProducto.id,
                novedad: $scope.novedad,
                fecha: fecha
            }
            
            $http({
                method: 'GET',
                header: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                url: 'http://localhost:8081/tailorShop/json?ob=producto&op=create',
                params: {json: JSON.stringify(json)}
            }).then(function (data, response) {
                console.log(data, response);
                $scope.resultado = "Producto creado correctamente.";
                $scope.new = true;
                sleep(10000);
            }), function (response) {
                console.log(response);
                $scope.ajaxDataUsuario = response.data.message || 'Request failed';
                $scope.status = response.status;
                $scope.resultado = "No se ha podido crear el producto.";
            }
        }

//Anyadida esta funcion que realiza un retardo para que no haya problemas al mostrar la foto en la web
        function sleep(milliseconds) {
            var start = new Date().getTime();
            for (var i = 0; i < 1e7; i++) {
                if ((new Date().getTime() - start) > milliseconds) {
                    break;
                }
            }
        }

        $scope.tipoProductoRefresh = function (f, consultar) {
            var form = f;
            if (consultar) {
                $http({
                    method: 'GET',
                    url: 'http://localhost:8081/tailorShop/json?ob=tipoproducto&op=get&id=' + $scope.obj_tipoProducto.id
                }).then(function (response) {
                    $scope.obj_tipoProducto = response.data.message;
                    form.userForm.obj_tipoProducto.$setValidity('valid', true);
                }, function (response) {
                    form.userForm.obj_tipoProducto.$setValidity('valid', false);
                });
            } else {
                form.userForm.obj_tipoProducto.$setValidity('valid', true);
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
            $scope.userId = oSessionService.getId();
            //Api FormData 
            //https://developer.mozilla.org/es/docs/Web/API/XMLHttpRequest/FormData
            var oFormData = new FormData();
            oFormData.append('file', file);
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
        }
    }]);