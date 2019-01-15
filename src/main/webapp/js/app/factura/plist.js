'use strict'

moduleFactura.controller('facturaPlistController', ['$scope', 'toolService', '$http', 'sessionService',
    '$routeParams', '$location',
    function ($scope, toolService, $http, oSessionService, $routeParams, $location) {


        $scope.ob = "factura";
        $scope.op = "plist";
        $scope.id = $routeParams.id;
        $scope.user = $routeParams.user;

//        //Chequeo sesión
//        if (oSessionService.getUserName() !== "") {
//            $scope.usuario = oSessionService.getUserName();
//            $scope.logeado = true;
//            $scope.userId = oSessionService.getId();
//        }


        if (!$routeParams.order) {
            $scope.orderURLServidor = "";
            $scope.orderURLCliente = "";
        } else {
            $scope.orderURLServidor = "&order=" + $routeParams.order;
            $scope.orderURLCliente = $routeParams.order;
        }

        if (!$routeParams.rpp) {
            $scope.rpp = 10;
        } else {
            $scope.rpp = $routeParams.rpp;
        }

        if (!$routeParams.page) {
            $scope.page = 1;
        } else {
            if ($routeParams.page >= 1) {
                $scope.page = $routeParams.page;
            } else {
                $scope.page = 1;
            }
        }

        if ($scope.id == null && $scope.user == null) {

            //getcount
            $http({
                method: 'GET',
                url: 'http://localhost:8081/tailorShop/json?ob=factura&op=getcount'
            }).then(function (response) {
                $scope.status = response.status;
                $scope.ajaxDataFacturaNumber = response.data.message;
                $scope.totalPages = Math.ceil($scope.ajaxDataFacturaNumber / $scope.rpp);
                if ($scope.page > $scope.totalPages) {
                    $scope.page = $scope.totalPages;
                    $scope.update();
                }
                pagination2();
            }, function (response) {
                $scope.ajaxDataFacturaNumber = response.data.message || 'Request failed';
                $scope.status = response.status;
            });

            //Getpage trae todos los registros de factura de la BBDD
            $http({
                method: 'GET',
                //withCredentials: true,
                url: 'http://localhost:8081/tailorShop/json?ob=factura&op=getpage&rpp=' + $scope.rpp + '&page=' + $scope.page + $scope.orderURLServidor
            }).then(function (response) {
                $scope.status = response.status;
                $scope.ajaxDataFactura = response.data.message;
                $scope.hayId = false;
                $scope.admin = oSessionService.isAdmin();

                for (var i = 0; i < $scope.ajaxDataFactura.length; i++) {

                    var fecha = $scope.ajaxDataFactura[i]["fecha"];
                    var diaN = "";
                    var dia = fecha.substr(-8, 2);
                    if (dia.charAt(0) == " ") {
                        diaN = dia.replace(" ", "0");
                    } else {
                        diaN = dia;
                    }
                    var anyo = fecha.substr(-4);
                    var mesN = "";
                    var mesS = fecha.substr(0, 3);
                    switch (mesS) {
                        case "ene":
                            mesN = "01";
                            break;
                        case "feb":
                            mesN = "02";
                            break;
                        case "mar":
                            mesN = "03";
                            break;
                        case "abr":
                            mesN = "04";
                            break;
                        case "may":
                            mesN = "05";
                            break;
                        case "jun":
                            mesN = "06";
                            break;
                        case "jul":
                            mesN = "07";
                            break;
                        case "ago":
                            mesN = "08";
                            break;
                        case "sep":
                            mesN = "09";
                            break;
                        case "oct":
                            mesN = "10";
                            break;
                        case "nov":
                            mesN = "11";
                            break;
                        case "dic":
                            mesN = "12";
                            break;
                        default:

                    }

                    var fechaFormat = diaN + '/' + mesN + '/' + anyo;
                    $scope.ajaxDataFactura[i]["fecha"] = fechaFormat;

                }
            }, function (response) {
                $scope.status = response.status;
                $scope.ajaxDataFactura = response.data.message || 'Request failed';
            });

            //Si el usuario y el id vienen rellenos
        } else {

            //getcount filtrado por ID
            $http({
                method: 'GET',
                url: 'http://localhost:8081/tailorShop/json?ob=factura&op=getcount&id=' + $scope.id
            }).then(function (response) {
                $scope.status = response.status;
                $scope.ajaxDataFacturaNumber = response.data.message;
                $scope.totalPages = Math.ceil($scope.ajaxDataFacturaNumber / $scope.rpp);
                if ($scope.page > $scope.totalPages) {
                    $scope.page = $scope.totalPages;
                    $scope.update();
                }
                pagination2();
            }, function (response) {
                $scope.ajaxDataFacturaNumber = response.data.message || 'Request failed';
                $scope.status = response.status;
            });

            //Getpage trae todos los registros de factura de la BBDD
            $http({
                method: 'GET',
                //withCredentials: true,
                url: 'http://localhost:8081/tailorShop/json?ob=factura&op=getpage&rpp=' + $scope.rpp + '&page=' + $scope.page + '&id=' +
                        $scope.id + $scope.orderURLServidor
            }).then(function (response) {

                if (response.data.status == 200) {

                    $scope.status = response.status;
                    $scope.ajaxDataFactura = response.data.message;
                    $scope.hayId = true;
                    $scope.admin = oSessionService.isAdmin();
                    for (var i = 0; i < $scope.ajaxDataFactura.length; i++) {

                        var fecha = $scope.ajaxDataFactura[i]["fecha"];
                        var diaN = "";
                        var dia = fecha.substr(-8, 2);
                        if (dia.charAt(0) == " ") {
                            diaN = dia.replace(" ", "0");
                        } else {
                            diaN = dia;
                        }
                        var anyo = fecha.substr(-4);
                        var mesN = "";
                        var mesS = fecha.substr(0, 3);
                        switch (mesS) {
                            case "ene":
                                mesN = "01";
                                break;
                            case "feb":
                                mesN = "02";
                                break;
                            case "mar":
                                mesN = "03";
                                break;
                            case "abr":
                                mesN = "04";
                                break;
                            case "may":
                                mesN = "05";
                                break;
                            case "jun":
                                mesN = "06";
                                break;
                            case "jul":
                                mesN = "07";
                                break;
                            case "ago":
                                mesN = "08";
                                break;
                            case "sep":
                                mesN = "09";
                                break;
                            case "oct":
                                mesN = "10";
                                break;
                            case "nov":
                                mesN = "11";
                                break;
                            case "dic":
                                mesN = "12";
                                break;
                            default:

                        }

                        var fechaFormat = diaN + '/' + mesN + '/' + anyo;
                        $scope.ajaxDataFactura[i]["fecha"] = fechaFormat;

                    }

                } else {
                    $location.path("/home");
                }

            }, function (response) {
                $scope.status = response.status;
                $scope.ajaxDataFactura = response.data.message || 'Request failed';
            });
        }

        $scope.resetOrder = function () {

            if ($scope.id == null) {
                $location.url('factura/plist/' + $scope.rpp + '/' + $scope.page);
            } else {
                $location.url('factura/plist/' + $scope.rpp + '/' + $scope.page + '/' + $scope.id + '/' + $scope.user);
            }
        }


        $scope.ordena = function (order, align) {
            if ($scope.orderURLServidor == "") {
                $scope.orderURLServidor = "&order=" + order + "," + align;
                $scope.orderURLCliente = order + "," + align;
            } else {
                $scope.orderURLServidor = $scope.orderURLServidor + "-" + order + "," + align;
                $scope.orderURLCliente = $scope.orderURLCliente + "-" + order + "," + align;
            }
            if ($scope.id == null) {
                $location.url('factura/plist/' + $scope.rpp + '/' + $scope.page + '/' + $scope.orderURLCliente);
            } else {
                $location.url('factura/plist/' + $scope.rpp + '/' + $scope.page + '/' + $scope.id + '/' + $scope.user + '/' + $scope.orderURLCliente);
            }
        }

        //paginacion neighbourhood
        function pagination2() {
            $scope.list2 = [];
            $scope.neighborhood = 3;
            for (var i = 1; i <= $scope.totalPages; i++) {
                if (i === $scope.page) {
                    $scope.list2.push(i);
                } else if (i <= $scope.page && i >= ($scope.page - $scope.neighborhood)) {
                    $scope.list2.push(i);
                } else if (i >= $scope.page && i <= ($scope.page - -$scope.neighborhood)) {
                    $scope.list2.push(i);
                } else if (i === ($scope.page - $scope.neighborhood) - 1) {
                    $scope.list2.push("...");
                } else if (i === ($scope.page - -$scope.neighborhood) + 1) {
                    $scope.list2.push("...");
                }
            }
        }


        $scope.update = function () {
            $location.url('factura/plist/' + $scope.rpp + '/' + $scope.page + '/' + $scope.orderURLCliente);
        }


        $scope.isActive = toolService.isActive;



//HACER PDF
//http://dataurl.net/#dataurlmaker
//https://parall.ax/products/jspdf
        $scope.descargarPDF = function (id) {
            var usuario;
            var fecha;
            var lineasTotales;
            var iva;
            var length = $scope.ajaxDataFacturaNumber;
//            var length = $routeParams.rpp;

            var doc = new jsPDF();
            for (var i = 0; i < length; i++) {
                if ($scope.ajaxDataFactura[i].id == id) {
                    usuario = $scope.ajaxDataFactura[i].obj_usuario.ape1 + ' ' + $scope.ajaxDataFactura[i].obj_usuario.ape2 + ', ' + $scope.ajaxDataFactura[i].obj_usuario.nombre;
                    fecha = $scope.ajaxDataFactura[i].fecha;

                    iva = $scope.ajaxDataFactura[i].iva;
                }
            }
            ;
            $http({
                method: 'GET',
                url: 'http://localhost:8081/tailorShop/json?ob=linea&op=getpage&rpp=' + $scope.rpp + '&page=' + $scope.page + '&id=' +
                        id + $scope.orderURLServidor
            }).then(function (response) {
                $scope.status = response.status;
                lineasTotales = response.data.message.length;
                $scope.ajaxLineasFactura = response.data.message;
                console.log($scope.ajaxLineasFactura);
                var imgData = 'data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/4RDiRXhpZgAATU0AKgAAAAgABAE7AAIAAAAIAAAISodpAAQAAAABAAAIUpydAAEAAAAQAAAQyuocAAcAAAgMAAAAPgAAAAAc6gAAAAgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAFlvbGFuZGEAAAWQAwACAAAAFAAAEKCQBAACAAAAFAAAELSSkQACAAAAAzk2AACSkgACAAAAAzk2AADqHAAHAAAIDAAACJQAAAAAHOoAAAAIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAyMDE4OjEyOjIwIDE1OjEwOjI2ADIwMTg6MTI6MjAgMTU6MTA6MjYAAABZAG8AbABhAG4AZABhAAAA/+ELGmh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8APD94cGFja2V0IGJlZ2luPSfvu78nIGlkPSdXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQnPz4NCjx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iPjxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+PHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9InV1aWQ6ZmFmNWJkZDUtYmEzZC0xMWRhLWFkMzEtZDMzZDc1MTgyZjFiIiB4bWxuczpkYz0iaHR0cDovL3B1cmwub3JnL2RjL2VsZW1lbnRzLzEuMS8iLz48cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0idXVpZDpmYWY1YmRkNS1iYTNkLTExZGEtYWQzMS1kMzNkNzUxODJmMWIiIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyI+PHhtcDpDcmVhdGVEYXRlPjIwMTgtMTItMjBUMTU6MTA6MjYuOTYyPC94bXA6Q3JlYXRlRGF0ZT48L3JkZjpEZXNjcmlwdGlvbj48cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0idXVpZDpmYWY1YmRkNS1iYTNkLTExZGEtYWQzMS1kMzNkNzUxODJmMWIiIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyI+PGRjOmNyZWF0b3I+PHJkZjpTZXEgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj48cmRmOmxpPllvbGFuZGE8L3JkZjpsaT48L3JkZjpTZXE+DQoJCQk8L2RjOmNyZWF0b3I+PC9yZGY6RGVzY3JpcHRpb24+PC9yZGY6UkRGPjwveDp4bXBtZXRhPg0KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICA8P3hwYWNrZXQgZW5kPSd3Jz8+/9sAQwAHBQUGBQQHBgUGCAcHCAoRCwoJCQoVDxAMERgVGhkYFRgXGx4nIRsdJR0XGCIuIiUoKSssKxogLzMvKjInKisq/9sAQwEHCAgKCQoUCwsUKhwYHCoqKioqKioqKioqKioqKioqKioqKioqKioqKioqKioqKioqKioqKioqKioqKioqKioq/8AAEQgAfAB8AwEiAAIRAQMRAf/EAB8AAAEFAQEBAQEBAAAAAAAAAAABAgMEBQYHCAkKC//EALUQAAIBAwMCBAMFBQQEAAABfQECAwAEEQUSITFBBhNRYQcicRQygZGhCCNCscEVUtHwJDNicoIJChYXGBkaJSYnKCkqNDU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6g4SFhoeIiYqSk5SVlpeYmZqio6Slpqeoqaqys7S1tre4ubrCw8TFxsfIycrS09TV1tfY2drh4uPk5ebn6Onq8fLz9PX29/j5+v/EAB8BAAMBAQEBAQEBAQEAAAAAAAABAgMEBQYHCAkKC//EALURAAIBAgQEAwQHBQQEAAECdwABAgMRBAUhMQYSQVEHYXETIjKBCBRCkaGxwQkjM1LwFWJy0QoWJDThJfEXGBkaJicoKSo1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h5eoKDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uLj5OXm5+jp6vLz9PX29/j5+v/aAAwDAQACEQMRAD8A+kaKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKy9V8SaTooI1C8RJMcRL8zn/gI5H41xvjPx3KlxJpuhybNhKzXKnnPdV9Pr+VedszO5Z2LMxySTkk15OIzFQfLTV2fU5fkEq0VUxD5U+nX/gHpN78VYFJXTtNkkHZ53C/oM/zrEuPiZrswIiFrb+hjiJP/AI8TXKQW81zKI7aKSaQ9EjUsT+ArVg8I6/c/6vSrkf8AXRdn/oWK894rFVdm/kfQLLcswy9+K/7ef+ZNL458RyjDam4/3I0X+Qqq/ifXXOTq95+EzD+VWpfA3iKGB5pdP2xxqWZjPHwB1/irArCpKvH42/nc7KNPA1E/Yxi7drfoaP8AwkWt/wDQYv8A/wACn/xo/wCEi1v/AKDF/wD+BT/41RhhkuJ44YV3SSMFRfUk4FdB/wAIB4m/6Bv/AJHj/wDiqUfbT+G7+8dV4Oi0qvLG/eyM3/hItb/6DF//AOBT/wCNH/CRa3/0GL//AMCX/wAa0v8AhAPE3/QN/wDI8f8A8VTW8B+JF66Y34TRn/2ar9nie0vxMvb5d/ND74kFv4w8QWxzHqtwf+uhD/8AoWa7Hwh4+udR1GLTtXVGeX5Y50XaS3ow6c+2K4uXwnr0P39Juj/uR7v5V0Xgvwdqa65b6hqNs9rBbneBKMM7dhjqPxrow8sSqqSv8zhzCnlssNOT5b20ate/TY9Sooor6M/PArH8Wak2leGLy5iOJNmxD6FjjP65rYrlfiOpbwdKQM7ZUJ9uaxxEnGlJrsdmBhGpiqcJbNr8zx4nJyetdb4G8Jx6/cSXV/u+xwELsBx5jemfSuSr1b4Xzo/h24hU/PHcEsPYgY/ka+dwVONSslI/QM5r1MPg5TpaPRX7HXWlla2EAhsreOCMfwxqFFT0UV9OkkrI/NJScnd7mF41vPsXhC+cHDSJ5Q/4EcfyJrxGvUfild+Xo1pag8zTFz9FH/168ur53Mp81a3ZH6Bw7S5MHz/zN/5G94ItPtnjCxUjKxuZT/wEZ/nivba8t+Ftr5mtXd0RxDCFB92P/wBavUq9HLY2o37s8DiKrz4zk/lS/wAwooor0j50KKKKACiiigAqlrGnJq+j3NjIcCZCoPoex/PFXaKUkpKzKhOUJKUd0fPd7Zz6feS2t3GY5om2sprtPhfa339q3F0istl5ZR2I4ds8Ae45/wAmvRLzSNO1CVZb6xt7iRRgNLEGOPTJ7VaiijgiWOFFjjUYVEGAB7CvLo5f7Orz82iPp8Zn6xGFdFQ1e/b5DqKKK9U+WPKvihdeb4gt7YHiGDJHoWJ/oBXE1u+NLr7X4wv3zkJJ5Y/4CAP6VhV8liZc9aT8z9Vy6n7LB04+S/HU9T+Ftr5eh3VyRzNPtB9lH+JNdxWB4Itfsvg6wXGDIhkP/AiT/LFb9fS4WPJRivI/Ocyqe1xlSXm/w0Ciiiug4AooooAKKKKACiiigAooooAKRmCqWPQDJpao65cfZNAvp848u3cg++DilJ8qbLhFzmorqeE305udQuJ25MsrP+ZzUKqWYKOpOBSVf0G2+1+IbCAjIe4QH6Z5r45JzlbufrkmqVNvol+R7pYW4tNNtrcdIolT8hirFFFfYpWVj8hlJybbCiiimIKKKKACiiigAooooAKKKKACua+IFz9m8G3QBwZmWMficn9BXS1wnxUuNmkWVuP+Wkxc/wDAR/8AXrmxUuWhJ+R6OV0/aY2nHzv92p5fXSeAbf7R4ytMjIjDSfkprm67b4XQb/EFzNj/AFduR+bD/CvnMLHmrxXmfoWZz9ngqkvJ/joeq0UUV9YflgUUUUAFFFFABRRRQAUUUUAFFFFABXmXxVm3alYQZ4SJnx9Tj+lem1598StBvLyS31KziedI4zHKqDJXnIOPTmuLHKUqDUT2cknCGOi5u2/32PNa9H+FMPy6lN7xp/M155DbT3Mwht4ZJZScBEUkn8BXsHgTQbjQ9DYXo2XFw/mMmfuDGAD715GX05SrqVtEfV5/XhDBum3rK2nzudPRRRX0h+dhRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAf/2Q==';

                //CABECERA
                doc.setFont('times');
                doc.setFontType('italic');
                doc.setFillColor(215, 215, 215);
                doc.rect(0, 0, 210, 70, 'F');
                doc.setFillColor(255, 0, 0);
                doc.rect(0, 0, 210, 5, 'F');
//                doc.rect(8, 10, 105, 70);
                doc.addImage(imgData, 'JPEG', 10, 12, 42, 45);
                doc.setFontSize(15);
                doc.setFontType('bold');
                doc.text(55, 20, 'Trolleyess, S.A.');
                doc.setFontSize(11);
                doc.setFontType('normal');
                doc.text(55, 30, 'CIF: B1234567');
                doc.text(55, 36, 'Tel.: 912 34 56 78');
                doc.text(55, 42, 'tailorShops@gmail.com');
                doc.text(55, 48, 'C/ Trolleyes núm. 123 Pta 12');
                doc.text(55, 54, '47910 Valencia (Valencia)');
                doc.setFontSize(25);
                doc.setFontType('bold');
                doc.text(120, 23, 'Factura núm. ' + id);
                doc.setFontSize(13);
                doc.setFontType('normal');
                doc.text(120, 48, 'Cliente: ' + usuario);
                doc.text(120, 54, 'Fecha: ' + fecha);
//                doc.rect(8, 80, 195, 210);
                doc.setFontSize(15);
                doc.setFontType('bold');
                doc.text(17, 90, 'Código');
                doc.text(55, 90, 'Descripción');
                doc.text(130, 90, 'Cantidad');
                doc.text(175, 90, 'Precio');
                doc.setLineWidth(0.5);
                doc.line(8, 95, 203, 95);

                doc.setFontSize(15);

                //LINEAS DE LA FACTURA
                var linea = 107;
                var precio = 0;
                var cantidad = 0;
                for (var x = 0; x <= lineasTotales - 1; x++) {
                    if (x % 12 === 0 && x !== 0) {
                        doc.addPage('a4', 1);
                        doc.rect(8, 10, 195, 70);
                        doc.rect(8, 10, 110, 70);
                        doc.addImage(imgData, 'JPEG', 10, 12, 42, 45);
                        doc.setFontSize(15);
                        doc.setFontType('bold');
                        doc.text(55, 20, 'TROLLEYES, S.A.');
                        doc.setFontSize(11);
                        doc.setFontType('normal');
                        doc.text(55, 30, 'CIF: B1234567');
                        doc.text(55, 36, 'Tel.: 912 34 56 78');
                        doc.text(55, 44, 'Email tailorShops@gmail.com');
                        doc.text(55, 48, 'C/ Trolleyes núm. 123 Pta 12');
                        doc.text(55, 54, '47910 Valencia (Valencia)');
                        doc.setFontSize(30);
                        doc.setFontType('bold');
                        doc.text(125, 23, 'Factura núm. ' + id);
                        doc.setFontSize(12);
                        doc.setFontType('normal');
                        doc.text(125, 48, 'Cliente: ' + usuario);
                        doc.text(125, 54, 'Fecha: ' + fecha);
                        doc.rect(8, 80, 195, 210);
                        doc.setFontSize(15);
                        doc.setFontType('bold');
                        doc.text(17, 90, 'Codigo');
                        doc.text(55, 90, 'Descripcion');
                        doc.text(130, 90, 'Cantidad');
                        doc.text(175, 90, 'Precio');
                        doc.setLineWidth(0.5);
                        doc.line(8, 95, 203, 95);

                        doc.setFontSize(15);
                        linea = 107;
                    }
                    doc.setFontType('normal');
                    doc.setFontSize(12);
                    doc.text(17, linea, $scope.ajaxLineasFactura[x].obj_producto.codigo);
                    doc.text(55, linea, $scope.ajaxLineasFactura[x].obj_producto.desc);
                    doc.text(140, linea, ($scope.ajaxLineasFactura[x].cantidad).toString());
                    doc.text(175, linea, (($scope.ajaxLineasFactura[x].obj_producto.precio).toFixed(2).toString()));
                    linea = linea + 13;
                    precio = (precio + ($scope.ajaxLineasFactura[x].obj_producto.precio * $scope.ajaxLineasFactura[x].cantidad));
                    cantidad = cantidad + $scope.ajaxLineasFactura[x].cantidad;
                }

                //FOOTER DE FACTURA
                doc.setFillColor(255, 0, 0);
                doc.rect(0, 292, 297, 5, 'F')

                doc.setFillColor(156, 156, 156);
//                doc.rect(9, 260, 193, 5, 'F');
                doc.setLineWidth(0.5);
                doc.line(8, 277, 203, 277);
                doc.setFontSize(15);
                doc.setFontType('bold');
                doc.text(17, 273, 'Cantidad Total');
                doc.text(75, 273, 'Precio');
                doc.text(120, 273, 'IVA');
                doc.text(168, 273, 'Precio Total');

                doc.setFontType('normal');
                doc.setFontSize(12);
                doc.text(27, 285, cantidad.toString());
                doc.text(75, 285, (precio.toFixed(2).toString()));
                doc.text(120, 285, (precio * (iva / 100)).toFixed(2).toString());
                doc.text(178, 285, (precio * (iva / 100 + 1)).toFixed(2).toString());

                doc.output('save', 'Factura-' + id + '-' + fecha + '.pdf'); //Try to save PDF as a file (not works on ie before 10, and some mobile devices)
//                doc.output('datauristring');        //returns the data uri string
//                doc.output('datauri');              //opens the data uri in current window
//                doc.output('dataurlnewwindow');     //opens the data uri in new window
                ;
            }, function (response) {
                $scope.status = response.status;
                $scope.ajaxLineasFactura = response.data.message || 'Request failed';
            });
        };





    }
])
