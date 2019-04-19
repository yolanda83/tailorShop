/* global moduleCommon */

'use strict';

modulecursos.controller('cursosController', ['$scope', '$location', 'toolService',
    function ($scope, $location, toolService) {

        $scope.ruta = $location.path();

        $scope.isActive = toolService.isActive;



    }]);