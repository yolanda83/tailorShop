/* global moduleUsuario */

'use strict';

moduleUsuario.controller('usuarioEditController', ['$scope', '$http', 'toolService',
    '$routeParams', 'sessionService', '$anchorScroll', '$location',
    function ($scope, $http, toolService, $routeParams, oSessionService, $anchorScroll, $location) {

        $anchorScroll();
        $scope.id = $routeParams.id;


        $http({
            method: "GET",
            url: 'http://localhost:8081/tailorShop/json?ob=usuario&op=get&id=' + $scope.id
        }).then(function (response) {
            console.log(response);
            if (response.data.status === 200) {
                $scope.id = response.data.message.id;
                $scope.dni = response.data.message.dni;
                $scope.nombre = response.data.message.nombre;
                $scope.ape1 = response.data.message.ape1;
                $scope.ape2 = response.data.message.ape2;
                $scope.loginUser = response.data.message.login;
                $scope.pass = forge_sha256(response.data.message.pass);
                $scope.foto = response.data.message.foto;
                $scope.obj_tipoUsuario_desc = response.data.message.obj_tipoUsuario.desc;
                $scope.obj_tipoUsuario = {
                    id: response.data.message.obj_tipoUsuario.id,
                    desc: response.data.message.obj_tipoUsuario.desc
                };
                $scope.admin = oSessionService.isAdmin();
            } else {
                $location.path("/home");
            }
        }), function (response) {
            console.log(response);
        };

        $scope.guardar = function () {
            var foto;
            if ($scope.myFile !== undefined) {
                //Si el nombre de la imagen es "Foto" significa que es la de por defecto, se le deja intacta
                if ($scope.myFile.name === "fotoUser.png") {
                    foto = $scope.myFile.name;
                    //Si la imagen que tenía el producto era la predefinida y me suben una nueva foto diferente.
                } else if ($scope.foto === "fotoUser.png" && $scope.myFile.name !== "fotoUser.png") {
                    foto = guid() + $scope.myFile.name;
                } else {
                    foto = $scope.foto;
                }
                uploadPhoto(foto);
            } else {
                foto = $scope.foto;
            }

            var tipoUsuario = 2;
            var admin = oSessionService.isAdmin();

            if (admin == true) {
                var tipoUsuario = 1;
            }

            var json = {
                id: $scope.id,
                dni: $scope.dni,
                nombre: $scope.nombre,
                ape1: $scope.ape1,
                ape2: $scope.ape2,
                login: $scope.loginUser,
                foto: foto,
                id_tipoUsuario: tipoUsuario
            };

            $http({
                method: 'GET',
                header: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                url: 'http://localhost:8081/tailorShop/json?ob=usuario&op=update',
                params: {json: JSON.stringify(json)}
            }).then(function (data, response) {
                console.log(data, response);
                $scope.edit = true;
            }), function (response) {
                console.log(response);
            };
        };

        $(".fotoEditar").on("click", function () {
            $("#prueba").trigger('click');
        });

        function uploadPhoto(name) {
            var file = $scope.myFile;
            file = new File([file], name, {type: file.type});
            var oFormData = new FormData();
            oFormData.append('file', file);
            $http({
                headers: {'Content-Type': undefined},
                method: 'POST',
                data: oFormData,
                url: `http://localhost:8081/tailorShop/json?ob=producto&op=addimage&id=` + $scope.id
            });
        }

        function guid() {
            return "ss-s-s-s-sss".replace(/s/g, s4);
        }

        function s4() {
            return Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
        }

        $scope.isActive = toolService.isActive;

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
    }
]);
