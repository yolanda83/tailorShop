/* global moduleCommon */

'use strict';

moduleContacto.controller('contactoController', ['$scope', '$location', 'toolService',
    function ($scope, $location, toolService) {

        $scope.ruta = $location.path();

        $scope.isActive = toolService.isActive;



    }]);