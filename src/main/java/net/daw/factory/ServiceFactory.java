package net.daw.factory;

import javax.servlet.http.HttpServletRequest;

import net.daw.bean.ReplyBean;
import net.daw.service.CarritoService;
import net.daw.service.FacturaService;
import net.daw.service.FavoritoService;
import net.daw.service.LineaService;
import net.daw.service.ProductoService;
import net.daw.service.TipoproductoService;
import net.daw.service.TipousuarioService;
import net.daw.service.UsuarioService;

/**
 *
 * @author Yolanda
 */
public class ServiceFactory {

    /**
     * MÉTODO EXECUTE SERVICE
     *
     * @param oRequest
     * @return Devuelve un ReplyBean que se manda al servlet y al Cliente
     * @throws Exception
     */
    public static ReplyBean executeService(HttpServletRequest oRequest) throws Exception {

        String ob = oRequest.getParameter("ob");
        String op = oRequest.getParameter("op");
        ReplyBean oReplyBean = null;

        switch (ob) {
            case "carrito":
                CarritoService oCarritoService = new CarritoService(oRequest);
                switch (op) {
                    case "add":
                        oReplyBean = oCarritoService.add();
                        break;
                    case "reduce":
                        oReplyBean = oCarritoService.reduce();
                        break;
                    case "remove":
                        oReplyBean = oCarritoService.remove();
                        break;
                    case "empty":
                        oReplyBean = oCarritoService.empty();
                        break;
                    case "show":
                        oReplyBean = oCarritoService.show();
                        break;
                    case "buy":
                        oReplyBean = oCarritoService.buy();
                        break;
                    default:
                        oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                        break;
                }
                break;
            case "tipousuario":
                TipousuarioService oTipousuarioService = new TipousuarioService(oRequest);
                switch (op) {
                    case "get":
                        oReplyBean = oTipousuarioService.get();
                        break;
                    case "create":
                        oReplyBean = oTipousuarioService.create();
                        break;
                    case "update":
                        oReplyBean = oTipousuarioService.update();
                        break;
                    case "remove":
                        oReplyBean = oTipousuarioService.remove();
                        break;
                    case "getcount":
                        oReplyBean = oTipousuarioService.getcount();
                        break;
                    case "getpage":
                        oReplyBean = oTipousuarioService.getpage();
                        break;
                    default:
                        oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                        break;
                }
                break;
            case "usuario":
                UsuarioService oUsuarioService = new UsuarioService(oRequest);
                switch (op) {
                    case "get":
                        oReplyBean = oUsuarioService.get();
                        break;
                    case "create":
                        oReplyBean = oUsuarioService.create();
                        break;
                    case "update":
                        oReplyBean = oUsuarioService.update();
                        break;
                    case "remove":
                        oReplyBean = oUsuarioService.remove();
                        break;
                    case "getcount":
                        oReplyBean = oUsuarioService.getcount();
                        break;
                    case "getpage":
                        oReplyBean = oUsuarioService.getpage();
                        break;
//                    case "fill":
//                        oReplyBean = oUsuarioService.fill();
//                        break;
                    case "login":
                        oReplyBean = oUsuarioService.login();
                        break;
                    case "logout":
                        oReplyBean = oUsuarioService.logout();
                        break;
                    case "check":
                        oReplyBean = oUsuarioService.check();
                        break;
                    case "updatepass":
                        oReplyBean = oUsuarioService.updatePass();
                        break;
                    default:
                        oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                        break;
                }
                break;
            case "factura":
                FacturaService oFacturaService = new FacturaService(oRequest);
                switch (op) {
                    case "get":
                        oReplyBean = oFacturaService.get();
                        break;
                    case "create":
                        oReplyBean = oFacturaService.create();
                        break;
                    case "update":
                        oReplyBean = oFacturaService.update();
                        break;
                    case "remove":
                        oReplyBean = oFacturaService.remove();
                        break;
                    case "getcount":
                        oReplyBean = oFacturaService.getcount();
                        break;
                    case "getcountspecific":
                        oReplyBean = oFacturaService.getcountspecific();
                        break;
                    case "getpage":
                        oReplyBean = oFacturaService.getpage();
                        break;
                    default:
                        oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                        break;
                }
                break;
            case "linea":
                LineaService oLineaService = new LineaService(oRequest);
                switch (op) {
                    case "get":
                        oReplyBean = oLineaService.get();
                        break;
                    case "create":
                        oReplyBean = oLineaService.create();
                        break;
                    case "update":
                        oReplyBean = oLineaService.update();
                        break;
                    case "remove":
                        oReplyBean = oLineaService.remove();
                        break;
                    case "getcount":
                        oReplyBean = oLineaService.getcount();
                        break;
                    case "getcountspecific":
                        oReplyBean = oLineaService.getcountspecific();
                        break;
                    case "getpage":
                        oReplyBean = oLineaService.getpage();
                        break;
                    default:
                        oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                        break;
                }
                break;

            case "producto":
                ProductoService oProductoService = new ProductoService(oRequest);
                switch (op) {
                    case "get":
                        oReplyBean = oProductoService.get();
                        break;
                    case "create":
                        oReplyBean = oProductoService.create();
                        break;
                    case "update":
                        oReplyBean = oProductoService.update();
                        break;
                    case "remove":
                        oReplyBean = oProductoService.remove();
                        break;
                    case "getcount":
                        oReplyBean = oProductoService.getcount();
                        break;
                    case "getcounttipo":
                        oReplyBean = oProductoService.getcounttipo();
                        break;
                    case "getcountbusqueda":
                        oReplyBean = oProductoService.getcountbusqueda();
                        break;
                    case "getpage":
                        oReplyBean = oProductoService.getpage();
                        break;
                    case "getnovedad":
                        oReplyBean = oProductoService.getnovedad();
                        break;
                    case "getbusqueda":
                        oReplyBean = oProductoService.getbusqueda();
                        break;
//                    case "loaddata":
//                        oReplyBean = oProductoService.loaddata();
//                        break;
                    case "addimage":
                        oReplyBean = oProductoService.addimage();
                        break;
                    //favoritos
                    case "getfavoritos":
                        oReplyBean = oProductoService.getfavoritos();
                        break;
                    case "getcountfav":
                        oReplyBean = oProductoService.getcountfav();
                        break;
                    case "addFav":
                        oReplyBean = oProductoService.addFav();
                        break;
                    case "checkFav":
                        oReplyBean = oProductoService.checkFav();
                        break;
                    case "removeFav":
                        oReplyBean = oProductoService.removeFav();
                        break;
                    default:
                        oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                        break;
                }
                break;
            case "tipoproducto":
                TipoproductoService oTipoproductoService = new TipoproductoService(oRequest);
                switch (op) {
                    case "get":
                        oReplyBean = oTipoproductoService.get();
                        break;
                    case "create":
                        oReplyBean = oTipoproductoService.create();
                        break;
                    case "update":
                        oReplyBean = oTipoproductoService.update();
                        break;
                    case "remove":
                        oReplyBean = oTipoproductoService.remove();
                        break;
                    case "getcount":
                        oReplyBean = oTipoproductoService.getcount();
                        break;
                    case "getpage":
                        oReplyBean = oTipoproductoService.getpage();
                        break;
                    default:
                        oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                        break;
                }
                break;
            default:
                oReplyBean = new ReplyBean(500, "Object doesn't exist");
                break;
        }
        return oReplyBean;
    }

}
