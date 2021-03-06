package net.daw.service;

import com.google.gson.Gson;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.FacturaBean;
import net.daw.bean.ReplyBean;
import net.daw.bean.UsuarioBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.dao.FacturaDao;
import net.daw.factory.ConnectionFactory;
import net.daw.helper.ParameterCook;

/**
 *
 * @author Yolanda
 */
public class FacturaService {

    HttpServletRequest oRequest;
    String ob = null;

    /**
     * Constructor
     *
     * @param oRequest
     */
    public FacturaService(HttpServletRequest oRequest) {
        super();
        this.oRequest = oRequest;
        ob = oRequest.getParameter("ob");
    }

    /**
     * Método CHECK PERMISSION
     *
     * @param operacion
     * @return Devuelve TRUE si los permisos son correctos. FALSE si no hay
     * autorización.
     */
    protected Boolean checkPermission(String operacion) {
        UsuarioBean oUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user");
        Integer id = 0;
        switch (operacion) {
            case "remove":
            case "update":
            case "create":
                if (oUsuarioBean.getId_tipoUsuario() != 1) {
                    oUsuarioBean = null;
                }
                break;
            case "getpage":

                if (oRequest.getParameter("id") != null) {
                    id = Integer.parseInt(oRequest.getParameter("id"));

                }

                if (id != oUsuarioBean.getId() && oUsuarioBean.getId_tipoUsuario() != 1) {
                    oUsuarioBean = null;
                }
                break;
        }

        return oUsuarioBean != null;

    }

    /**
     * Método GET
     *
     * @return Obtiene una única factura. Devuelve un ReplyBean con el
     * resultado.
     * @throws Exception
     */
    public ReplyBean get() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer id = Integer.parseInt(oRequest.getParameter("id"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            FacturaDao oFacturaDao = new FacturaDao(oConnection, ob);
            FacturaBean oFacturaBean = oFacturaDao.get(id, 1);
            Gson oGson = new Gson();
            oReplyBean = new ReplyBean(200, oGson.toJson(oFacturaBean));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: get method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }

        return oReplyBean;

    }

    /**
     * Método REMOVE
     *
     * @return Borra una factura. Devuelve un ReplyBean con el resultado.
     * @throws Exception
     */
    public ReplyBean remove() throws Exception {
        ReplyBean oReplyBean;
        if (checkPermission("remove")) {
            ConnectionInterface oConnectionPool = null;
            Connection oConnection;
            try {
                Integer id = Integer.parseInt(oRequest.getParameter("id"));
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                FacturaDao oFacturaDao = new FacturaDao(oConnection, ob);
                int iRes = oFacturaDao.remove(id);
                oReplyBean = new ReplyBean(200, Integer.toString(iRes));
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: remove method: " + ob + " object", ex);
            } finally {
                oConnectionPool.disposeConnection();
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;

    }

    /**
     * Método GET COUNT
     *
     * @return Cuenta el nº de Facturas existentes. Devuelve un ReplyBean con el
     * resultado.
     * @throws Exception
     */
    public ReplyBean getcount() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        Integer id = 0;
        try {
            if (oRequest.getParameter("id") != null) {
                id = Integer.parseInt(oRequest.getParameter("id"));
            }
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            FacturaDao oFacturaDao = new FacturaDao(oConnection, ob);
            int registros = oFacturaDao.getcount(id);
            Gson oGson = new Gson();
            oReplyBean = new ReplyBean(200, oGson.toJson(registros));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: getcount method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }

        return oReplyBean;

    }

    /**
     * Método GET COUNT SPECIFIC
     *
     * @return Cuenta el nº de Facturas existentes. Devuelve un ReplyBean con el
     * resultado.
     * @throws Exception
     */
    public ReplyBean getcountspecific() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer id = Integer.parseInt(oRequest.getParameter("id"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            FacturaDao oFacturaDao = new FacturaDao(oConnection, ob);
            int registros = oFacturaDao.getcountspecific(id);
            Gson oGson = new Gson();
            oReplyBean = new ReplyBean(200, oGson.toJson(registros));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: getcount method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }

        return oReplyBean;

    }

    /**
     * Método CREATE
     *
     * @return Crea una factura. Devuelve un ReplyBean con el resultado.
     * @throws Exception
     */
    public ReplyBean create() throws Exception {
        ReplyBean oReplyBean;
        if (checkPermission("create")) {
            ConnectionInterface oConnectionPool = null;
            Connection oConnection;
            try {
                String strJsonFromClient = oRequest.getParameter("json");
                Gson oGson = new Gson();
                FacturaBean oFacturaBean = new FacturaBean();
                oFacturaBean = oGson.fromJson(strJsonFromClient, FacturaBean.class);
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                FacturaDao oFacturaDao = new FacturaDao(oConnection, ob);
                oFacturaBean = oFacturaDao.create(oFacturaBean);
                oReplyBean = new ReplyBean(200, oGson.toJson(oFacturaBean));
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: create method: " + ob + " object", ex);
            } finally {
                oConnectionPool.disposeConnection();
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    /**
     * Método UPDATE
     *
     * @return Actualiza datos de una factura. Devuelve un ReplyBean con el
     * resultado.
     * @throws Exception
     */
    public ReplyBean update() throws Exception {
        int iRes = 0;
        ReplyBean oReplyBean;
        if (checkPermission("update")) {
            ConnectionInterface oConnectionPool = null;
            Connection oConnection;
            try {
                String strJsonFromClient = oRequest.getParameter("json");
                Gson oGson = new Gson();
                FacturaBean oFacturaBean = new FacturaBean();
                oFacturaBean = oGson.fromJson(strJsonFromClient, FacturaBean.class);
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                FacturaDao oFacturaDao = new FacturaDao(oConnection, ob);
                iRes = oFacturaDao.update(oFacturaBean);
                oReplyBean = new ReplyBean(200, Integer.toString(iRes));
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: update method: " + ob + " object", ex);
            } finally {
                oConnectionPool.disposeConnection();
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    /**
     * Método GETPAGE
     *
     * @return Trae un listado de facturas en la BBDD. Devuelve un ReplyBean con
     * el resultado.
     * @throws Exception
     */
    public ReplyBean getpage() throws Exception {
        ReplyBean oReplyBean;
        if (checkPermission("getpage")) {
            ConnectionInterface oConnectionPool = null;
            Connection oConnection;
            try {
                Integer id = 0;
                Integer iRpp = Integer.parseInt(oRequest.getParameter("rpp"));
                Integer iPage = Integer.parseInt(oRequest.getParameter("page"));
                HashMap<String, String> hmOrder = ParameterCook.getOrderParams(oRequest.getParameter("order"));

                if (oRequest.getParameter("id") != null) {
                    id = Integer.parseInt(oRequest.getParameter("id"));
                }

                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                FacturaDao oFacturaDao = new FacturaDao(oConnection, ob);
                ArrayList<FacturaBean> alFacturaBean = oFacturaDao.getpage(iRpp, iPage, id, hmOrder, 1);
                Gson oGson = new Gson();
                oReplyBean = new ReplyBean(200, oGson.toJson(alFacturaBean));
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: getpage method: " + ob + " object", ex);
            } finally {
                oConnectionPool.disposeConnection();
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;

    }
}
