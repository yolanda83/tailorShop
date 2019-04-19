'use strict';

moduleService.service('toolService', ['$location', function ($location) {

        return {
            isActive: function (p) {
                return $location.path().startsWith(p);
            },
            objects: {
                usuario: 'usuario',
                tipousuario: 'tipousuario',
                producto: 'producto',
                tipoproducto: 'tipoproducto',
                factura: 'factura',
                linea: 'linea'
            },
            accion: function (tabla, action, id) {
                $location.url(`${tabla}/${action}/${id}`);
            },
            msgError: function (error) {
                console.log('Error en el servidor, reiniciar o revisar mas tarde :' + error);
            }
        }
    }]);