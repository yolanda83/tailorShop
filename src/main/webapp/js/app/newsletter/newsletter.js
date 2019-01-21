/* global moduleCommon */

'use strict';

moduleNewsletter.controller('newsletterController', ['$scope', '$location', 'toolService',
    function ($scope, $location, toolService) {

        $scope.ruta = $location.path();

        $scope.logeado = false;
        $scope.isActive = toolService.isActive;



    }]);