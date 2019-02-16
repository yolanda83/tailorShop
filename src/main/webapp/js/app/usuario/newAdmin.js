'use strict'

moduleUsuario.controller('usuarioNewAdminController', ['$scope', '$http', 'toolService', '$routeParams', 'sessionService',
    function ($scope, $http, toolService, $routeParams, oSessionService) {

        $scope.numRegistros = 0;


        $scope.isActive = toolService.isActive;


        $scope.guardar = function () {

//            if ($scope.myFile == undefined) {
//                $scope.foto = "fotoUser.png";
//            } else {
//                $scope.foto = guid() + $scope.myFile.name;
//                $scope.fileNameChanged();
//            }

            var foto;
            if ($scope.myFile !== undefined) {
                foto = guid() + $scope.myFile.name;
                uploadPhoto(foto);
                $scope.foto = foto;
            } else {
                $scope.foto = "fotoUser.png";
            }

            var json = {
                dni: $scope.dni,
                nombre: $scope.nombre,
                ape1: $scope.ape1,
                ape2: $scope.ape2,
                login: $scope.loginAdmin,
                pass: forge_sha256($scope.passAdmin),
                foto: $scope.foto,
                id_tipoUsuario: $scope.obj_tipoUsuario.id
            }
            $http({
                method: 'GET',
                header: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                url: 'http://localhost:8081/tailorShop/json?ob=usuario&op=create',
                params: {json: JSON.stringify(json)}
            }).then(function (response) {
                $scope.resultado = "Usuario creado correctamente.";
                $scope.new = true;
            }), function (response) {
                $scope.ajaxDataUsuario = response.data.message || 'Request failed';
                $scope.status = response.status;
                $scope.resultado = "No se ha podido crear el usuario.";
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

//        $scope.fileNameChanged = function () {
//            //Solucion mas cercana
//            //https://stackoverflow.com/questions/37039852/send-formdata-with-other-field-in-angular
//            var file = $scope.myFile;
//            file = new File([file], $scope.foto, {type: file.type});
//            //Api FormData 
//            //https://developer.mozilla.org/es/docs/Web/API/XMLHttpRequest/FormData
//            var oFormData = new FormData();
//            oFormData.append('file', file);
//            $http({
//                headers: {'Content-Type': undefined},
//                method: 'POST',
//                data: oFormData,
//                url: `http://localhost:8081/tailorShop/json?ob=producto&op=addimage`
//            }).then(function (response) {
//                console.log(response);
//            }, function (response) {
//                console.log(response)
//            });
//        }


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