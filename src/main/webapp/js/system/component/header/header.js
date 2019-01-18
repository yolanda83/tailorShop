moduleComponent.component('headerComponent', {
    //restrict: 'A',
    templateUrl: 'js/system/component/header/header.html',
    bindings: {
        // data: '=',
        // tabla: '<'
        eventlistener: '&'
    },
    controllerAs: 'c',
    controller: js
});

function js(toolService, sessionService, $scope, $http, $location, $mdDialog) {
    var self = this;


    self.logeado = sessionService.isSessionActive();
    self.usuario = sessionService.getUserName();
    self.userId = sessionService.getId();
    self.isActive = toolService.isActive;
    self.isAdmin = sessionService.isAdmin();
    self.carrito = sessionService.getCountCarrito();



    sessionService.registerObserverCallback(function () {
        self.usuario = sessionService.getUserName();
    })
    sessionService.registerObserverCallback(function () {
        self.isAdmin = sessionService.isAdmin();
    })
    sessionService.registerObserverCallback(function () {
        self.carrito = sessionService.getCountCarrito();
    })
    sessionService.registerObserverCallback(function () {
        self.logeado = sessionService.isSessionActive();
    })



    $scope.log = function () {
        $scope.error = false;

        var login = $scope.login;
        var pass = forge_sha256($scope.pass);

        $http({
            method: 'GET',
            header: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            url: 'http://localhost:8081/tailorShop/json?ob=usuario&op=login&user=' + login + '&pass=' + pass,
        }).then(function (response) {
            $scope.status = response.data.status;
            if ($scope.status == 200) {
                $scope.logeado = true;
                sessionService.setUserName(response.data.message.login);
                sessionService.setId(response.data.message.id);
                self.usuario = sessionService.getUserName();
                self.userId = sessionService.getId();
                sessionService.setSessionActive();

                //Seteamos si es ADMIN o USUARIO
                if (response.data.message.obj_tipoUsuario.id == 1) { //ADMIN
                    sessionService.setAdmin();
                } else {
                    sessionService.setUser();
                }

                $location.path('/home');
            } else {
                $scope.error = true;
                $scope.showAlert('Error', 'Datos erroneos.');
                $scope.logeado = false;
                self.usuario = "";
            }
        }), function (response) {
            $scope.ajaxDataUsuario = response.data.message || 'Request failed';
            $scope.status = response.status;
        }
    }


    $scope.showAlert = function (titulo, description) {
        $mdDialog.show(
                $mdDialog.alert()
                .clickOutsideToClose(false)
                .title(titulo)
                .textContent(description)
                .ariaLabel('Alert Dialog Demo')
                .ok('OK!')
                );
    };





}

        // Navbar Dropdown
       
        $scope.cliqui = $(window).resize(function () {
            if ($(window).width() >= 980) {
                // Cuando pasas por encima de un elemento, se muestra un menú dropdown
                $(".navbar .dropdown-toggle").hover(function () {
                    $(this)
                            .parent()
                            .toggleClass("show");
                    $(this)
                            .parent()
                            .find(".dropdown-menu")
                            .toggleClass("show");
                });

                // Esconde el menú cuando el ratón ya no hace hover sobre el elemento
                $(".navbar .dropdown-menu").mouseleave(function () {
                    $(this).removeClass("show");
                });
            }
        });