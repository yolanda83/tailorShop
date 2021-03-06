tailorShop.run(['$rootScope', 'sessionService', '$location', '$http', 'countcarritoService',
    function ($rootScope, oSessionService, $location, $http, countcarritoService) {
        $rootScope.$on("$routeChangeStart", function (event, next, current) {

            if (next.$$route != null) {
                var nextUrl = next.$$route.originalPath;
            }

            $http({
                method: 'GET',
                url: 'http://localhost:8081/tailorShop/json?ob=usuario&op=check'
            }).then(function (response) {
                if (response.data.status === 200) {
                    oSessionService.setSessionActive();
                    oSessionService.setUserName(response.data.message.login);
                    oSessionService.setId(response.data.message.id);
                } else {
                    oSessionService.setSessionInactive();
                    oSessionService.setUserName();
                    oSessionService.setId();
                }
            }, function (response) {
                oSessionService.setSessionInactive();
                if (nextUrl !== '/home' && nextUrl !== '/usuario/new/'
                        && nextUrl !== '/usuario/new' && nextUrl != '/usuario/new/') {
                    $location.path("/home");
                }
            });
            countcarritoService.updateCarrito();
        })
    }]);