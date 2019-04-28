/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.service;

import com.google.gson.Gson;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.LineaBean;
import net.daw.bean.ReplyBean;
import net.daw.bean.UsuarioBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.dao.LineaDao;
import net.daw.factory.ConnectionFactory;
import net.daw.helper.ParameterCook;

/**
 *
 * @author Yolanda
 */
public class LineaService {

    HttpServletRequest oRequest;
    String ob = null;

    /**
     * Constructor
     *
     * @param oRequest
     */
    public LineaService(HttpServletRequest oRequest) {
        super();
        this.oRequest = oRequest;
        ob = oRequest.getParameter("ob");
    }

    /**
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

                if (oRequest.getParameter("userId") != null) {
                    id = Integer.parseInt(oRequest.getParameter("userId"));

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
     * @return Obtiene una Línea de una Factura. Devuelve un ReplyBean con el
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
            LineaDao oLineaDao = new LineaDao(oConnection, ob);
            LineaBean oLineaBean = oLineaDao.get(id, 1, 1);
            Gson oGson = new Gson();
            oReplyBean = new ReplyBean(200, oGson.toJson(oLineaBean));
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
     * @return Borra una línea de una factura. Devuelve un ReplyBean con el
     * resultado.
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
                LineaDao oLineaDao = new LineaDao(oConnection, ob);
                int iRes = oLineaDao.remove(id);
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
     * @return Cuenta el total de Líneas de una factura. Devuelve un ReplyBean
     * con el resultado.
     * @throws Exception
     */
    public ReplyBean getcount() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer id = Integer.parseInt(oRequest.getParameter("id"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            LineaDao oLineaDao = new LineaDao(oConnection, ob);
            int registros = oLineaDao.getcount(id);
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
     * @return Cuenta el total de líneas de una factura. Devuelve un ReplyBean
     * con el resultado.
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
            LineaDao oLineaDao = new LineaDao(oConnection, ob);
            int registros = oLineaDao.getcountspecific(id);
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
     * @return Añade una línea a una factura. Devuelve un ReplyBean con el
     * resultado.
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
                LineaBean oLineaBean = new LineaBean();
                oLineaBean = oGson.fromJson(strJsonFromClient, LineaBean.class);
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                LineaDao oLineaDao = new LineaDao(oConnection, ob);
                oLineaBean = oLineaDao.create(oLineaBean);
                oReplyBean = new ReplyBean(200, oGson.toJson(oLineaBean));
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
     * @return Edita una línea de una factura. Devuelve un ReplyBean con el
     * resultado.
     * @throws Exception
     */
    public ReplyBean update() throws Exception {
        int iRes = 0;
        ReplyBean oReplyBean = null;
        if (checkPermission("update")) {
            ConnectionInterface oConnectionPool = null;
            Connection oConnection;
            try {
                String strJsonFromClient = oRequest.getParameter("json");
                Gson oGson = new Gson();
                LineaBean oLineaBean = new LineaBean();
                oLineaBean = oGson.fromJson(strJsonFromClient, LineaBean.class);
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                LineaDao oLineaDao = new LineaDao(oConnection, ob);
                iRes = oLineaDao.update(oLineaBean);
                oReplyBean = new ReplyBean(200, oGson.toJson(iRes));
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
     * Método GET PAGE
     *
     * @return Devuelve un listado de líneas de una factura. Devuelve un
     * ReplyBean con el resultado.
     * @throws Exception
     */
    public ReplyBean getpage() throws Exception {
        ReplyBean oReplyBean;
        if (checkPermission("getpage")) {
            ConnectionInterface oConnectionPool = null;
            Connection oConnection;
            try {
                Integer iRpp = Integer.parseInt(oRequest.getParameter("rpp"));
                Integer iPage = Integer.parseInt(oRequest.getParameter("page"));
                Integer id = Integer.parseInt(oRequest.getParameter("id"));
                HashMap<String, String> hmOrder = ParameterCook.getOrderParams(oRequest.getParameter("order"));
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                LineaDao oLineaDao = new LineaDao(oConnection, ob);
                ArrayList<LineaBean> alLineaBean = oLineaDao.getpage(iRpp, iPage, id, hmOrder, 1, 1);
                Gson oGson = new Gson();
                oReplyBean = new ReplyBean(200, oGson.toJson(alLineaBean));
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
