/* global moduleCommon */

'use strict';

moduleInfo.controller('infoController', ['$scope', '$location', 'toolService', '$anchorScroll',
    function ($scope, $location, toolService, $anchorScroll) {

        $anchorScroll();
        
        $scope.ruta = $location.path();

        $scope.isActive = toolService.isActive;



    }]);