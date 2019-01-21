/* global moduleCommon */

'use strict';

moduleAbout.controller('aboutController', ['$scope', '$location', 'toolService',
    function ($scope, $location, toolService) {

        $scope.ruta = $location.path();

        $scope.logeado = false;
        $scope.isActive = toolService.isActive;


//        $(document).ready(function () {
//            setTimeout(function () {
//                $("#main").removeClass("is-loading");
//            }, 100);
//        });

    }]);