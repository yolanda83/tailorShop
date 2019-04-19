package net.daw.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.ReplyBean;
import net.daw.bean.ProductoBean;
import net.daw.bean.UsuarioBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.dao.FavoritoDao;
import net.daw.dao.ProductoDao;
import net.daw.factory.ConnectionFactory;
import net.daw.helper.ParameterCook;

public class FavoritoService {

    HttpServletRequest oRequest;
    String ob = null;

    public FavoritoService(HttpServletRequest oRequest) {
        super();
        this.oRequest = oRequest;
        ob = oRequest.getParameter("ob");
    }

    protected Boolean checkPermission(String strMethodName) {

        UsuarioBean oUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user"); //AQUI OBTENEMOS EL USUARIO QUE ESTE LOGUEADO

        switch (strMethodName) {
            case "remove":
            case "update":
            case "create":
            case "add":
                if (oUsuarioBean.getId_tipoUsuario() != 1) {
                    oUsuarioBean = null;
                }
                break;
            case "get":
                Integer id = Integer.parseInt(oRequest.getParameter("id"));

                if (id != oUsuarioBean.getId() && oUsuarioBean.getId_tipoUsuario() != 1) {
                    oUsuarioBean = null;
                }
                break;
        }

        return oUsuarioBean != null;

    }

    public ReplyBean remove() throws Exception {
        ReplyBean oReplyBean;
        if (checkPermission("remove")) {
            ConnectionInterface oConnectionPool = null;
            Connection oConnection;
            try {
                Integer id = Integer.parseInt(oRequest.getParameter("id"));
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                ProductoDao oProductoDao = new ProductoDao(oConnection, ob);
                int iRes = oProductoDao.remove(id);
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

    public ReplyBean getcountfav() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer id = 0;
            if (oRequest.getParameter("id") != null) {
                id = Integer.parseInt(oRequest.getParameter("id"));
            }

            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            FavoritoDao oFavoritoDao = new FavoritoDao(oConnection, ob);
            int registros = oFavoritoDao.getcountfav(id);
            Gson oGson = new Gson();
            oReplyBean = new ReplyBean(200, oGson.toJson(registros));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: getcount method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }

        return oReplyBean;

    }

    public ReplyBean create() throws Exception {
        ReplyBean oReplyBean;
        if (checkPermission("create")) {
            ConnectionInterface oConnectionPool = null;
            Connection oConnection;
            try {
                String strJsonFromClient = oRequest.getParameter("json");
                Gson oGson = new Gson();
                ProductoBean oProductoBean = new ProductoBean();
                oProductoBean = oGson.fromJson(strJsonFromClient, ProductoBean.class);
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                ProductoDao oProductoDao = new ProductoDao(oConnection, ob);
                oProductoBean = oProductoDao.create(oProductoBean);
                oReplyBean = new ReplyBean(200, oGson.toJson(oProductoBean));
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

    public ReplyBean getfavoritos() throws Exception {
        ReplyBean oReplyBean;
        if (checkPermission("get")) {
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
                FavoritoDao oFavoritoDao = new FavoritoDao(oConnection, ob);
                ArrayList<ProductoBean> alProductoBean = oFavoritoDao.getfavoritos(iRpp, iPage, hmOrder, 1, id);
                Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
                oReplyBean = new ReplyBean(200, oGson.toJson(alProductoBean));
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: get page: " + ob + " object", ex);
            } finally {
                oConnectionPool.disposeConnection();
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;

    }

}
