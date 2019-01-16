'use strict'

moduleCommon.controller('homeController', ['$scope', '$location', 'toolService', 'sessionService',
    function ($scope, $location, toolService, oSessionService) {

        $scope.ruta = $location.path();

        $scope.logeado = false;
        $scope.isActive = toolService.isActive;

//        //Chequeo sesión
//        if (oSessionService.getUserName() !== "") {
//            $scope.usuario = oSessionService.getUserName();
//            $scope.logeado = true;
//            $scope.userId = oSessionService.getId();
//        }


        $(document).ready(function () {
            setTimeout(function () {
                $("#main").removeClass("is-loading");
            }, 100)
        });

    }]);