moduloDirectivas.component('caddModalPro', {
    //restrict: 'A',
    templateUrl: 'js/system/component/modal/cmodalPro.html',
    bindings: {
        data: '=',
        tabla: '<'
    },
    controllerAs: 'c',
    controller: addModalVarController
});


function addModalVarController($http) {
    var self = this;

    self.$onInit = function () {
        console.log('Modal cargado');
        //page y rpp por defecto
        if (!self.rpp) {
            self.rpp = 10;
        }
        if (!self.page) {
            self.page = 1;
        }
        self.orderURLServidor = "";
        self.modal_data()
    }

    //paginacion neighbourhood
    function pagination2() {
        self.list2 = [];
        self.neighborhood = 2;
        for (var i = 1; i <= self.totalPages; i++) {
            if (i === self.page) {
                self.list2.push(i);
            } else if (i <= self.page && i >= (self.page - self.neighborhood)) {
                self.list2.push(i);
            } else if (i >= self.page && i <= (self.page - -self.neighborhood)) {
                self.list2.push(i);
            } else if (i === (self.page - self.neighborhood) - 1) {
                self.list2.push("...");
            } else if (i === (self.page - -self.neighborhood) + 1) {
                self.list2.push("...");
            }
        }
    }

    //Ordena asc o desc
    self.ordena = function (order, align) {
        if (order != 'id') {
            order = self.tabla + '.desc';
        }
        if (self.orderURLServidor == "") {
            self.orderURLServidor = "&order=" + order + "," + align;
            self.orderURLCliente = order + "," + align;
        } else {
            self.orderURLServidor = "";
            self.orderURLCliente = "";
            self.orderURLServidor = "&order=" + order + "," + align;
            self.orderURLCliente = order + "," + align;
        }
        self.modal_data();
    }
    
    
    //Search
    self.modal_data = function () {
        //getcount
        $http({
            method: 'GET',
            url: `http://localhost:8081/tailorShop/json?ob=${self.tabla}&op=getcount`
        }).then(function (response) {
            self.status = response.status;
            self.ajaxDataUsuariosNumber = response.data.message;
            self.totalPages = Math.ceil(self.ajaxDataUsuariosNumber / self.rpp);
            if (self.page > self.totalPages) {
                self.page = self.totalPages;
            }
            pagination2();
        }, function (response) {
            self.ajaxDataUsuariosNumber = response.data.message || 'Request failed';
            self.status = response.status;
        });
        //10 RPPs botonera al iniciar
        $http({
            method: 'GET',
            url: `http://localhost:8081/tailorShop/json?ob=${self.tabla}&op=getpage&rpp=${self.rpp}&page=${self.page}${self.orderURLServidor}`
        }).then(function (response) {
            self.modal = response.data.message;
        }, function (response) {
            self.status = response.status;
            self.ajaxDataUsuarios = response.data.message || 'Request failed';
        });
    }

    //visualización de datos según página
    self.pagination_destino = function (pagina) {
        self.page = pagina;
        self.modal_data();
    }

   //actualización de RPPs de la botonera
    self.update_registro = function (rpp) {
        self.rpp = rpp;
        self.page = 1;
        self.modal_data();
    }

    //Selección del dato para mostrarlo al input del edit
    self.selected = function (id, fila) {
        self.data = {
            id: id,
            desc: fila
        };
        console.log(self.data);
    }

    self.resetAll = function () {
        self.rpp = "";
        self.page = "";

    }
}