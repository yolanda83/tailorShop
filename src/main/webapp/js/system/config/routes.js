/* global tailorShop */

var autenticacionAdministrador = function ($q, $location, $http, sessionService) {
    var deferred = $q.defer();

    $http({
        method: 'GET',
        url: 'http://localhost:8081/tailorShop/json?ob=usuario&op=check'
    }).then(function (response) {
        if (response.data.status === 200) {
            if (response.data.message.obj_tipoUsuario.id === 1) { // 1 = ADMINISTRADOR
//                sessionService.setUserName(response.data.message.login);
//                sessionService.setId(response.data.message.id);
                sessionService.setAdmin();
                deferred.resolve(response.data.message);

            } else {
                sessionService.setUser();
                $location.path('/home');
            }
        } else {
            sessionService.setUser();
            $location.path('/home');
        }
    }, function (response) {
        sessionService.setUser();
        $location.path('/home');
    });

    return deferred.promise;
};


var everyone = function ($q, $location, $http, sessionService) {
    var deferred = $q.defer();
    $http({
        method: 'GET',
        url: 'http://localhost:8081/tailorShop/json?ob=usuario&op=check'
    }).then(function (response) {
        if (response.data.status === 200) {
            if (response.data.message.obj_tipoUsuario.id === 1) { //1 - ADMINISTRADOR
//                sessionService.setSessionActive();
                sessionService.setAdmin();
//                sessionService.setUserName(response.data.message.login);
//                sessionService.setId(response.data.message.id);
            }
            if (response.data.message.obj_tipoUsuario.id === 2) { // 2 - USUARIO
//                sessionService.setSessionActive();
                sessionService.setUser();
//                sessionService.setUserName(response.data.message.login);
//                sessionService.setId(response.data.message.id);
            }
            deferred.resolve();
        } else { // 3 - INVITADO
//            sessionService.setSessionInactive();
//            sessionService.setUser();
            deferred.resolve();
        }
    }, function () {
        sessionService.setSessionInactive();
        sessionService.setUser();
        $location.path('/home');
    });
    return deferred.promise;
};


tailorShop.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/home', {templateUrl: 'js/app/common/home.html', controller: 'homeController', resolve: {auth: everyone}});
//CARRITO
//        $routeProvider.when('/carrito/plist/:id?', {templateUrl: 'js/app/carrito/plist.html', controller: 'carritoPlistController', resolve: {auth: everyone}});
        $routeProvider.when('/carrito/plist', {templateUrl: 'js/app/carrito/plist.html', controller: 'carritoPlistController', resolve: {auth: everyone}});
//FACTURA
        $routeProvider.when('/factura/plist', {templateUrl: 'js/app/factura/plist.html', controller: 'facturaPlistController'});
        $routeProvider.when('/factura/plist/:rpp?/:page?/:order?', {templateUrl: 'js/app/factura/plist.html', controller: 'facturaPlistController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/factura/plist/:rpp?/:page?/:id?/:user?/:order?', {templateUrl: 'js/app/factura/plist.html', controller: 'facturaPlistController'});
        $routeProvider.when('/factura/view/:id?', {templateUrl: 'js/app/factura/view.html', controller: 'facturaViewController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/factura/edit/:id?', {templateUrl: 'js/app/factura/edit.html', controller: 'facturaEditController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/factura/remove/:id?', {templateUrl: 'js/app/factura/remove.html', controller: 'facturaRemoveController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/factura/new', {templateUrl: 'js/app/factura/new.html', controller: 'facturaNewController', resolve: {auth: autenticacionAdministrador}});
//LINEA
        $routeProvider.when('/linea/plist/:rpp?/:page?/:id?/:userid?/:order?', {templateUrl: 'js/app/linea/plist.html', controller: 'lineaPlistController'});
        $routeProvider.when('/linea/view/:id?', {templateUrl: 'js/app/linea/view.html', controller: 'lineaViewController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/linea/edit/:id?', {templateUrl: 'js/app/linea/edit.html', controller: 'lineaEditController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/linea/remove/:id?', {templateUrl: 'js/app/linea/remove.html', controller: 'lineaRemoveController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/linea/new/:id?', {templateUrl: 'js/app/linea/new.html', controller: 'lineaNewController', resolve: {auth: autenticacionAdministrador}});
//PRODUCTO
        $routeProvider.when('/producto/plist/:rpp?/:page?/:order?', {templateUrl: 'js/app/producto/plist.html', controller: 'productoPlistController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/producto/view/:id?', {templateUrl: 'js/app/producto/view.html', controller: 'productoViewController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/producto/edit/:id?', {templateUrl: 'js/app/producto/edit.html', controller: 'productoEditController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/producto/remove/:id?', {templateUrl: 'js/app/producto/remove.html', controller: 'productoRemoveController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/producto/new', {templateUrl: 'js/app/producto/new.html', controller: 'productoNewController', resolve: {auth: autenticacionAdministrador}});
//TIPOPRODUCTO
        $routeProvider.when('/tipoproducto/plist', {templateUrl: 'js/app/tipoproducto/plist.html', controller: 'tipoproductoPlistController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/tipoproducto/plist/:rpp?/:page?/:order?', {templateUrl: 'js/app/tipoproducto/plist.html', controller: 'tipoproductoPlistController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/tipoproducto/view/:id?', {templateUrl: 'js/app/tipoproducto/view.html', controller: 'tipoproductoViewController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/tipoproducto/edit/:id?', {templateUrl: 'js/app/tipoproducto/edit.html', controller: 'tipoproductoEditController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/tipoproducto/remove/:id?', {templateUrl: 'js/app/tipoproducto/remove.html', controller: 'tipoproductoRemoveController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/tipoproducto/new', {templateUrl: 'js/app/tipoproducto/new.html', controller: 'tipoproductoNewController', resolve: {auth: autenticacionAdministrador}});
//TIPOUSUARIO
        $routeProvider.when('/tipousuario/plist', {templateUrl: 'js/app/tipousuario/plist.html', controller: 'tipousuarioPlistController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/tipousuario/plist/:rpp?/:page?/:order?', {templateUrl: 'js/app/tipousuario/plist.html', controller: 'tipousuarioPlistController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/tipousuario/view/:id?', {templateUrl: 'js/app/tipousuario/view.html', controller: 'tipousuarioViewController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/tipousuario/edit/:id?', {templateUrl: 'js/app/tipousuario/edit.html', controller: 'tipousuarioEditController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/tipousuario/remove/:id?', {templateUrl: 'js/app/tipousuario/remove.html', controller: 'tipousuarioRemoveController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/tipousuario/new', {templateUrl: 'js/app/tipousuario/new.html', controller: 'tipousuarioNewController', resolve: {auth: autenticacionAdministrador}});
//USUARIO
        $routeProvider.when('/usuario/plist', {templateUrl: 'js/app/usuario/plist.html', controller: 'usuarioPlistController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/usuario/plist/:rpp?/:page?/:order?', {templateUrl: 'js/app/usuario/plist.html', controller: 'usuarioPlistController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/usuario/view/:id?', {templateUrl: 'js/app/usuario/view.html', controller: 'usuarioViewController'});
        $routeProvider.when('/usuario/edit/:id?', {templateUrl: 'js/app/usuario/edit.html', controller: 'usuarioEditController'});
//        $routeProvider.when('/usuario/editAdmin/:id?', {templateUrl: 'js/app/usuario/editAdmin.html', controller: 'usuarioEditAdminController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/usuario/remove/:id?', {templateUrl: 'js/app/usuario/remove.html', controller: 'usuarioRemoveController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/usuario/new', {templateUrl: 'js/app/usuario/new.html', controller: 'usuarioNewController'});
        $routeProvider.when('/usuario/newAdmin', {templateUrl: 'js/app/usuario/newAdmin.html', controller: 'usuarioNewAdminController', resolve: {auth: autenticacionAdministrador}});
        $routeProvider.when('/usuario/login', {templateUrl: 'js/app/usuario/login.html', controller: 'usuarioLoginController', resolve: {auth: everyone}});
        $routeProvider.when('/usuario/logout', {templateUrl: 'js/app/usuario/logout.html', controller: 'usuarioLogoutController', resolve: {auth: everyone}});
        $routeProvider.when('/usuario/changepass', {templateUrl: 'js/app/usuario/changepass.html', controller: 'usuarioChangepassController'});
//PANTALLAS PARA USUARIO
        $routeProvider.when('/aboutus', {templateUrl: 'js/app/about/aboutus.html', controller: 'aboutController', resolve: {auth: everyone}});
        $routeProvider.when('/contacto', {templateUrl: 'js/app/contacto/contacto.html', controller: 'contactoController', resolve: {auth: everyone}});
        $routeProvider.when('/cursos', {templateUrl: 'js/app/cursos/cursos.html', controller: 'cursosController', resolve: {auth: everyone}});
        $routeProvider.when('/producto/plistUsuario/:rpp?/:page?/:order?/:tipo', {templateUrl: 'js/app/producto/plistUsuario.html', controller: 'productoPlistUsuarioController', resolve: {auth: everyone}});
        $routeProvider.when('/producto/plistUsuarioBusqueda/:rpp?/:page?/:order?/:busqueda', {templateUrl: 'js/app/producto/plistUsuarioBusqueda.html', controller: 'productoPlistUsuarioBusquedaController', resolve: {auth: everyone}});
        $routeProvider.when('/producto/plistUsuarioFav/:rpp?/:page?/:order?/:id/:usuario', {templateUrl: 'js/app/producto/plistUsuarioFav.html', controller: 'productoPlistUsuarioFavController', resolve: {auth: everyone}});


//DEFAULT
        $routeProvider.otherwise({redirectTo: '/home'});
    }]);















// AUTENTICACIoN USUARIO SE HA COMENTADO PORQUE SE HA RESUELTO EN SERVIDOR, PORQUE AQUi NO SOY CAPAZ DE PODER RECOGER EL ID DEL USUARIO/FACTURAS/ETC... SIN HACER OTRA LLAMADA A SERVIDOR
//var autenticacionUsuario = function ($q, $location, $http, sessionService) { 
//    var deferred = $q.defer();
//
//    $http({
//        method: 'GET',
//        url: 'http://localhost:8081/tailorShop/json?ob=usuario&op=check'
//    }).then(function (response) {
//        if (response.data.status === 200) {
//
//            if (response.data.message.obj_tipoUsuario.id === 1) { // 1 = ADMINISTRADOR
//                sessionService.setAdmin();
////                sessionService.setUserName(response.data.message.login);
////                sessionService.setId(response.data.message.id);
//                deferred.resolve(response.data.message);
//            } else if (response.data.message.obj_tipoUsuario.id === 2) { // 2 = CLIENTE
//                sessionService.setUser();
//            } else {
//                $location.path('/home');
//            }
//        } else {
//            $location.path('/home');
//        }
//    }, function (response) {
//        $location.path('/home');
//    });
//
//    return deferred.promise;
//};
