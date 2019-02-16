package net.daw.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.ReplyBean;
import net.daw.bean.ProductoBean;
import net.daw.bean.UsuarioBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.dao.FavoritoDao;
import net.daw.dao.ProductoDao;
import net.daw.factory.ConnectionFactory;
import net.daw.helper.EncodingHelper;
import net.daw.helper.ParameterCook;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class ProductoService {

    HttpServletRequest oRequest;
    String ob = null;

    public ProductoService(HttpServletRequest oRequest) {
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
            case "fill":
            case "load":
                if (oUsuarioBean.getId_tipoUsuario() != 1) {
                    oUsuarioBean = null;
                }
                break;
            case "get":
            case "add":
                Integer id = Integer.parseInt(oRequest.getParameter("id"));

                if (id != oUsuarioBean.getId() && oUsuarioBean.getId_tipoUsuario() != 1) {
                    oUsuarioBean = null;
                }
                break;
        }

        return oUsuarioBean != null;

    }

    public ReplyBean get() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer id = Integer.parseInt(oRequest.getParameter("id"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            ProductoDao oProductoDao = new ProductoDao(oConnection, ob);
            ProductoBean oProductoBean = oProductoDao.get(id, 1);
            Gson oGson = new Gson();
            oReplyBean = new ReplyBean(200, oGson.toJson(oProductoBean));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: get method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }

        return oReplyBean;

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

    public ReplyBean getcount() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            ProductoDao oProductoDao = new ProductoDao(oConnection, ob);
            int registros = oProductoDao.getcount();
            Gson oGson = new Gson();
            oReplyBean = new ReplyBean(200, oGson.toJson(registros));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: getcount method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }

        return oReplyBean;

    }

    public ReplyBean getcounttipo() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer tipo = 0;
            if (oRequest.getParameter("tipo") != null) {
                tipo = Integer.parseInt(oRequest.getParameter("tipo"));
            }

            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            ProductoDao oProductoDao = new ProductoDao(oConnection, ob);
            int registros = oProductoDao.getcounttipo(tipo);
            Gson oGson = new Gson();
            oReplyBean = new ReplyBean(200, oGson.toJson(registros));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: getcount method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }

        return oReplyBean;

    }

    public ReplyBean getcountbusqueda() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            String busqueda = oRequest.getParameter("busqueda");
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            ProductoDao oProductoDao = new ProductoDao(oConnection, ob);
            int registros = oProductoDao.getcountbusqueda(busqueda);
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

    public ReplyBean fill() throws Exception {
        ReplyBean oReplyBean;
        if (checkPermission("fill")) {
            ConnectionInterface oConnectionPool = null;
            Connection oConnection;
            ArrayList<ProductoBean> alProductoBean = new ArrayList<ProductoBean>();

            try {
//                alProductoBean = obtenerDatos();

                //String strJsonFromClient = oRequest.getParameter("json");
                Gson oGson = new Gson();
                ProductoBean oProductoBean = new ProductoBean();
                //oProductoBean = oGson.fromJson(strJsonFromClient, ProductoBean.class);
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                ProductoDao oProductoDao = new ProductoDao(oConnection, ob);

                for (ProductoBean productos : alProductoBean) {
                    oProductoBean = oProductoDao.create(productos);
                }
//            oProductoBean = oProductoDao.create(oProductoBean);
//            oReplyBean = new ReplyBean(200, oGson.toJson(oProductoBean));
                oReplyBean = new ReplyBean(200, oGson.toJson("Productos creados correctamente"));
            } catch (Exception ex) {
                oReplyBean = new ReplyBean(500,
                        "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
            } finally {
                oConnectionPool.disposeConnection();
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    public ReplyBean update() throws Exception {
        int iRes = 0;
        ReplyBean oReplyBean;
        if (checkPermission("update")) {
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
                iRes = oProductoDao.update(oProductoBean);
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

    public ReplyBean getpage() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer tipo = 0;
            Integer iRpp = Integer.parseInt(oRequest.getParameter("rpp"));
            Integer iPage = Integer.parseInt(oRequest.getParameter("page"));
            HashMap<String, String> hmOrder = ParameterCook.getOrderParams(oRequest.getParameter("order"));

            if (oRequest.getParameter("tipo") != null) {
                tipo = Integer.parseInt(oRequest.getParameter("tipo"));
            }

            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            ProductoDao oProductoDao = new ProductoDao(oConnection, ob);
            ArrayList<ProductoBean> alProductoBean = oProductoDao.getpage(iRpp, iPage, hmOrder, 1, tipo);
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(alProductoBean));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: get page: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }

        return oReplyBean;

    }

    public ReplyBean getbusqueda() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer iRpp = Integer.parseInt(oRequest.getParameter("rpp"));
            Integer iPage = Integer.parseInt(oRequest.getParameter("page"));
            HashMap<String, String> hmOrder = ParameterCook.getOrderParams(oRequest.getParameter("order"));
            String busqueda = oRequest.getParameter("busqueda");

            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            ProductoDao oProductoDao = new ProductoDao(oConnection, ob);
            ArrayList<ProductoBean> alProductoBean = oProductoDao.getbusqueda(iRpp, iPage, hmOrder, 1, busqueda);
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(alProductoBean));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: get page: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }

        return oReplyBean;

    }

    public ReplyBean getnovedad() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            ProductoDao oProductoDao = new ProductoDao(oConnection, ob);
            ArrayList<ProductoBean> alProductoBean = oProductoDao.getnovedad(1);
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(alProductoBean));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: get page: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }

        return oReplyBean;

    }

    //IMAGENES
    public ReplyBean loaddata() throws Exception {
        ReplyBean oReplyBean;
        if (checkPermission("load")) {
            ConnectionInterface oConnectionPool = null;
            Connection oConnection;
            ArrayList<ProductoBean> productos = new ArrayList<>();
            RellenarService oRellenarService = new RellenarService();
            try {
                Integer number = Integer.parseInt(oRequest.getParameter("number"));
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                ProductoDao oProductoDao = new ProductoDao(oConnection, ob);
                productos = oRellenarService.RellenarProducto(number);
                for (ProductoBean producto : productos) {
                    oProductoDao.create(producto);
                }
                Gson oGson = new Gson();
                oReplyBean = new ReplyBean(200, oGson.toJson("Productos creados: " + number));
            } catch (Exception ex) {
                oReplyBean = new ReplyBean(500,
                        "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
            } finally {
                oConnectionPool.disposeConnection();
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    public ReplyBean addimage() throws Exception {

        String name = "";
        ReplyBean oReplyBean;
        if (checkPermission("add")) {
            Gson oGson = new Gson();

            HashMap<String, String> hash = new HashMap<>();

            if (ServletFileUpload.isMultipartContent(oRequest)) {
                try {
                    List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(oRequest);
                    for (FileItem item : multiparts) {
                        if (!item.isFormField()) {
                            name = new File(item.getName()).getName();
                            item.write(new File(".//..//webapps//ROOT//imagenes//" + name));
                        } else {
                            hash.put(item.getFieldName(), item.getString());
                        }
                    }
                    oReplyBean = new ReplyBean(200, oGson.toJson("File upload: " + name));
                } catch (Exception ex) {
                    oReplyBean = new ReplyBean(500, oGson.toJson("Error while uploading file: " + name));
                }
            } else {
                oReplyBean = new ReplyBean(500, oGson.toJson("Can't read image"));
            }

        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    //FAVORITOS
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

    public ReplyBean checkFav() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        UsuarioBean oUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user");
        if (oUsuarioBean == null) {
            oReplyBean = new ReplyBean(401, "Unauthorized"); //No hay sesion iniciada
        } else {
            try {
                Integer idProducto = 0;
                if (oRequest.getParameter("id") != null) {
                    idProducto = Integer.parseInt(oRequest.getParameter("id"));
                }
                int idUsuario = oUsuarioBean.getId();
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                FavoritoDao oFavoritoDao = new FavoritoDao(oConnection, ob);
                int result = oFavoritoDao.get(idProducto, idUsuario);

                if (result == 0) {
                    oReplyBean = new ReplyBean(200, Integer.toString(result)); //Se ha anyadido a la lista de favoritos
                } else {
                    oReplyBean = new ReplyBean(500, Integer.toString(result)); //Ya lo tenemos en lista de favoritos
                }
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: get method: " + ob + " object", ex);
            } finally {
                oConnectionPool.disposeConnection();
            }
        }
        return oReplyBean;

    }

    public ReplyBean addFav() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer idProducto = 0;
            UsuarioBean oUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user");
            if (oUsuarioBean == null) {
                oReplyBean = new ReplyBean(401, "Unauthorized");
            } else {
                int idUsuario = oUsuarioBean.getId();
                if (oRequest.getParameter("id") != null) {
                    idProducto = Integer.parseInt(oRequest.getParameter("id"));
                }
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                FavoritoDao oFavoritoDao = new FavoritoDao(oConnection, ob);
                int iRes = oFavoritoDao.create(idProducto, idUsuario);
                oReplyBean = new ReplyBean(200, Integer.toString(iRes));
            }

        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: create method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
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

    public ReplyBean removeFav() throws Exception {
        ReplyBean oReplyBean;
//        if (checkPermission("remove")) {
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer idProducto = 0;
            if (oRequest.getParameter("id") != null) {
                idProducto = Integer.parseInt(oRequest.getParameter("id"));
            }
            UsuarioBean oUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user");
            int idUsuario = oUsuarioBean.getId();
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            FavoritoDao oFavoritoDao = new FavoritoDao(oConnection, ob);
            int iRes = oFavoritoDao.remove(idProducto, idUsuario);
            oReplyBean = new ReplyBean(200, Integer.toString(iRes));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: remove method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }
//        } else {
//            oReplyBean = new ReplyBean(401, "Unauthorized");
//        }
        return oReplyBean;

    }

}
