/* global moduleCommon */

'use strict';

moduleCommon.controller('homeController', ['$scope', '$location', 'toolService', '$http',
    function ($scope, $location, toolService, $http) {

        $scope.ruta = $location.path();

//        $scope.logeado = false;
        $scope.isActive = toolService.isActive;


        $(document).ready(function () {
            setTimeout(function () {
                $("#main").removeClass("is-loading");
            }, 100);
        });


        $http({
            method: 'GET',
            //withCredentials: true,
            url: 'http://localhost:8081/tailorShop/json?ob=producto&op=getnovedad'
        }).then(function (response) {
            $scope.status = response.status;
            $scope.ajaxDataNovedades = response.data.message;

            var length = $scope.ajaxDataNovedades.length;
            $scope.ajaxDataTotalNov = [];

            if (length < 8) {

                for (var i = 0; i < length; i++) {
                    $scope.ajaxDataTotalNov.push($scope.ajaxDataNovedades[i]);
                }

            } else {

                for (var i = 0; i <= 7; i++) {
                    $scope.ajaxDataTotalNov.push($scope.ajaxDataNovedades[i]);
                }

            }

        }, function (response) {
            $scope.ajaxDataNovedades = response.data.message || 'Request failed';
            $scope.status = response.status;
        });



    }]);