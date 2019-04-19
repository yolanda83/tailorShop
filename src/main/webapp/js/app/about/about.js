/* global moduleCommon */

'use strict';

moduleAbout.controller('aboutController', ['$scope', '$location', 'toolService',
    function ($scope, $location, toolService) {

        $scope.ruta = $location.path();

        $scope.isActive = toolService.isActive;

    }]);