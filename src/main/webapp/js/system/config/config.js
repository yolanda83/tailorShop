'use strict'

tailorShop.config(['$locationProvider', function ($locationProvider) {
        $locationProvider.html5Mode(true);
    }]);
tailorShop.config(['$httpProvider', function ($httpProvider) {
        $httpProvider.defaults.withCredentials = true;
    }]);