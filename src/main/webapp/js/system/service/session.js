/* global moduleService */

'use strict';

moduleService.service('sessionService', [function () {
        var isSessionActive = false;
        var userName = "";
        var id = "";
        var admin;
        var carrito = 0;
        var observerCallbacks = [];
        return {
            getUserName: function () {
                return userName;
            },
            setUserName: function (name) {
                userName = name;
                angular.forEach(observerCallbacks, function (callback) {
                    callback();
                });
            },
            isSessionActive: function () {
                return isSessionActive;
            },
            setSessionActive: function () {
                isSessionActive = true;
                angular.forEach(observerCallbacks, function (callback) {
                    callback();
                });
            },
            setSessionInactive: function () {
                isSessionActive = false;
//                userName = "";
                angular.forEach(observerCallbacks, function (callback) {
                    callback();
                });
            },
            getId: function () {
                return id;
            },
            setId: function (name) {
                id = name;
                angular.forEach(observerCallbacks, function (callback) {
                    callback();
                });
            },
            logOut: function () {
                isSessionActive = false;
//                userName = "";
//                id = "";
//                admin = null;

                angular.forEach(observerCallbacks, function (callback) {
                    callback();
                });
            },
            setCountCarrito: function (cantidad) {
                carrito = cantidad;
                //Para que sirve el callback()
                //https://www.quora.com/What-is-the-call-back-function-in-AngularJS
                angular.forEach(observerCallbacks, function (callback) {
                    callback();
                });
            },
            getCountCarrito: function () {
                return carrito;
            },

            isAdmin: function () {
                return admin;
            },
            setAdmin: function () {
                admin = true;
                angular.forEach(observerCallbacks, function (callback) {
                    callback();
                });
            },
            setUser: function () {
                admin = false;
                angular.forEach(observerCallbacks, function (callback) {
                    callback();
                });
            },
            
                        //register an observer
            //Entiendo que puedo guardar todos los observables en el array observerCallbacks y que cada vez que el objeto 
            // se actualice , angular detectara que observable se ha actualizado y lo actualizara en toda la aplicacion
            registerObserverCallback: function (callback) {
                observerCallbacks.push(callback);
            }
        };

    }
]);