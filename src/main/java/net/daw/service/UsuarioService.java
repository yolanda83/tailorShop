/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.ReplyBean;
import net.daw.bean.UsuarioBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.dao.UsuarioDao;
import net.daw.factory.ConnectionFactory;
import net.daw.helper.EncodingHelper;
import net.daw.helper.ParameterCook;

/**
 *
 * @author Yolanda
 */
public class UsuarioService {

    HttpServletRequest oRequest;
    String ob = null;

    /**
     * Constructor
     *
     * @param oRequest
     */
    public UsuarioService(HttpServletRequest oRequest) {
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
        UsuarioBean oUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user"); //Recojo todos los datos del usuario EN SESIÓN

        switch (operacion) {
            case "remove":
            case "fill":
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
            case "updatePass":
                Integer userId = Integer.parseInt(oRequest.getParameter("userId"));

                if (userId != oUsuarioBean.getId() && oUsuarioBean.getId_tipoUsuario() != 1) {
                    oUsuarioBean = null;
                }
                break;

        }

        return oUsuarioBean != null;

    }

    /**
     * Método GET
     *
     * @return Obtiene datos de un único usuario. Devuelve un ReplyBean con el
     * resultado.
     * @throws Exception
     */
    public ReplyBean get() throws Exception {
        ReplyBean oReplyBean;
        if (checkPermission("get")) {
            ConnectionInterface oConnectionPool = null;
            Connection oConnection;
            try {
                Integer id = Integer.parseInt(oRequest.getParameter("id"));
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
                UsuarioBean oUsuarioBean = oUsuarioDao.get(id, 1);
                Gson oGson = (new GsonBuilder()).create();
                oReplyBean = new ReplyBean(200, oGson.toJson(oUsuarioBean));
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: get method: " + ob + " object", ex);
            } finally {
                oConnectionPool.disposeConnection();
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;

    }

    /**
     * Método REMOVE
     *
     * @return Borra a un usuario. Devuelve un ReplyBean con el resultado.
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
                UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
                int iRes = oUsuarioDao.remove(id);
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
     * @return Cuenta el total de Usuarios. Devuelve un ReplyBean con el
     * resultado.
     * @throws Exception
     */
    public ReplyBean getcount() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
            int registros = oUsuarioDao.getcount();
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
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
     * @return Crea un nuevo usuario. Devuelve un ReplyBean con el resultado.
     * @throws Exception
     */
    public ReplyBean create() throws Exception {
        ReplyBean oReplyBean;

        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            String strJsonFromClient = oRequest.getParameter("json");
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            UsuarioBean oUsuarioBean = new UsuarioBean();
            oUsuarioBean = oGson.fromJson(strJsonFromClient, UsuarioBean.class);
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
            oUsuarioBean = oUsuarioDao.create(oUsuarioBean);
            oReplyBean = new ReplyBean(200, oGson.toJson(oUsuarioBean));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: create method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }

        return oReplyBean;
    }

    /**
     * Método UPDATE
     *
     * @return Edita los datos de un Usuario. Devuelve un ReplyBean con el
     * resultado.
     * @throws Exception
     */
    public ReplyBean update() throws Exception {
        int iRes = 0;
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            String strJsonFromClient = oRequest.getParameter("json");
            Gson oGson = (new GsonBuilder()).create();
            UsuarioBean oUsuarioBean = new UsuarioBean();
            oUsuarioBean = oGson.fromJson(strJsonFromClient, UsuarioBean.class);
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
            iRes = oUsuarioDao.update(oUsuarioBean);
            oReplyBean = new ReplyBean(200, Integer.toString(iRes));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: update method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    /**
     * Método GET PAGE
     *
     * @return Devuelve un listado de Usuarios. Devuelve un ReplyBean con el
     * resultado.
     * @throws Exception
     */
    public ReplyBean getpage() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer iRpp = Integer.parseInt(oRequest.getParameter("rpp"));
            Integer iPage = Integer.parseInt(oRequest.getParameter("page"));
            HashMap<String, String> hmOrder = ParameterCook.getOrderParams(oRequest.getParameter("order"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
            ArrayList<UsuarioBean> alUsuarioBean = oUsuarioDao.getpage(iRpp, iPage, hmOrder, 1);
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(alUsuarioBean));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: get page: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }

        return oReplyBean;

    }

//    /** 
//     *
//     * @return @throws Exception
//     */
//    public ReplyBean fill() throws Exception {
//        ReplyBean oReplyBean;
//        if (checkPermission("remove")) {
//            ConnectionInterface oConnectionPool = null;
//            Connection oConnection;
//            ArrayList<UsuarioBean> alUsuarioBean = new ArrayList<UsuarioBean>();
//
//            try {
//                alUsuarioBean = obtenerDatos();
//
//                Gson oGson = new Gson();
//                UsuarioBean oUsuarioBean = new UsuarioBean();
//                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
//                oConnection = oConnectionPool.newConnection();
//                UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
//
//                for (UsuarioBean usuarios : alUsuarioBean) {
//                    oUsuarioBean = oUsuarioDao.create(usuarios);
//                }
//                oReplyBean = new ReplyBean(200, oGson.toJson("Usuarios creados correctamente"));
//            } catch (Exception ex) {
//                oReplyBean = new ReplyBean(500,
//                        "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
//            } finally {
//                oConnectionPool.disposeConnection();
//            }
//        } else {
//            oReplyBean = new ReplyBean(401, "Unauthorized");
//        }
//        return oReplyBean;
//    }
//
//    /**
//     *
//     * @return
//     */
//    public ArrayList<UsuarioBean> obtenerDatos() {
//        ArrayList<UsuarioBean> alUsuario = new ArrayList<UsuarioBean>();
//        Random randomDni = new Random();
//        Random randomNombre = new Random();
//        Random randomApe1 = new Random();
//        Random randomApe2 = new Random();
//        Random randomLogin = new Random();
//        Random randomPass = new Random();
//        Random randomTipoUsuario = new Random();
//        UsuarioBean oUsuarioBean;
//
//        String[] dni = {"76294479Y", "35015012L", "02562016T", "85478299H", "22910746D",
//            "84459428R", "07424310W", "03146216T", "01715312H", "56338513J",
//            "87911495K", "03309113B", "36646306S", "15928151F", "14973941K", "42402099N", "40274838K",
//            "87430150C", "33081180G", "54757727V"};
//        String[] nombre = {"ANTONIO", "JOSE", "JUAN", "GERMAN", "MIKEL",
//            "GERARDO", "PASCUAL", "INAKI", "LEO", "GINES",
//            "JOSEFA", "LUCIA", "JULIA", "SUSANA", "EVA", "CATALINA", "DANIELA",
//            "LUISA", "ADRIANA", "ESTEFANIA"};
//        String[] ape1 = {"GONZALEZ", "RODRIGUEZ", "FERNANDEZ", "LOPEZ", "MARTINEZ", "SANCHEZ", "PEREZ", "GOMEZ",
//            "MARTIN", "JIMENEZ", "RUIZ", "HERNANDEZ", "DIAZ", "MUNOZ", "ALVAREZ", "ROMERO", "ALONSO", "GUTIERREZ",
//            "RAMOS", "CASTILLO"};
//        String[] ape2 = {"GONZALEZ", "RODRIGUEZ", "FERNANDEZ", "LOPEZ", "MARTINEZ", "SANCHEZ", "PEREZ", "GOMEZ",
//            "MARTIN", "JIMENEZ", "RUIZ", "HERNANDEZ", "DIAZ", "MUNOZ", "ALVAREZ", "ROMERO", "ALONSO", "GUTIERREZ",
//            "RAMOS", "CASTILLO"};
//        String[] login = {"Ton", "Kitty", "Dog", "Rob", "Cat",
//            "Ger", "Pascu", "Isla", "Cinta", "Japan",
//            "Sonar", "Miki", "Cons", "Green", "Black", "Pat", "Azar",
//            "Batik", "Play", "Monster"};
////        String[] pass = {"8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92"};
//        Integer[] tipoUsuario = {1, 2}; //vigilar que existan estos tipo usuario si no no los crear
//
//        for (int i = 0; i < 5; i++) {
//            oUsuarioBean = new UsuarioBean();
//            int randDni = randomDni.nextInt(20);
//            int randNombre = randomNombre.nextInt(20);
//            int randApe1 = randomApe1.nextInt(20);
//            int randApe2 = randomApe2.nextInt(20);
//            int randLogin = randomLogin.nextInt(20);
//            int randPass = randomPass.nextInt(20);
//            int randTipoUsuario = randomTipoUsuario.nextInt(2);
//
//            oUsuarioBean.setDni(dni[randDni]);
//            oUsuarioBean.setNombre(nombre[randNombre]);
//            oUsuarioBean.setApe1(ape1[randApe1]);
//            oUsuarioBean.setApe2((ape2[randApe2]));
//            oUsuarioBean.setLogin(login[randLogin]);
//            oUsuarioBean.setPass("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92");
//            oUsuarioBean.setId_tipoUsuario(tipoUsuario[randTipoUsuario]);
//            alUsuario.add(oUsuarioBean);
//        }
//
//        return alUsuario;
//    }
    /**
     * Método LOGIN
     *
     * @return Loguea a un usuario. Devuelve un ReplyBean con el resultado.
     * @throws Exception
     */
    public ReplyBean login() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        String strLogin = oRequest.getParameter("user");
        String strPassword = oRequest.getParameter("pass");

        oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
        oConnection = oConnectionPool.newConnection();
        UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);

        UsuarioBean oUsuarioBean = oUsuarioDao.login(strLogin, strPassword);
        if (oUsuarioBean != null) {
            oRequest.getSession().setAttribute("user", oUsuarioBean);
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(oUsuarioBean));
        } else {
            oReplyBean = new ReplyBean(401, "Bad Authentication");
        }

        oConnectionPool.disposeConnection();

        return oReplyBean;
    }

    /**
     * Método LOGOUT
     *
     * @return Desloguea al usuario activo. Devuelve un ReplyBean con el
     * resultado.
     * @throws Exception
     */
    public ReplyBean logout() throws Exception {
        oRequest.getSession().invalidate();
        return new ReplyBean(200, EncodingHelper.quotate("OK"));
    }

    /**
     * Método CHECK
     *
     * @return Chequea si hay usuario logueado o no. Devuelve un ReplyBean con
     * el resultado.
     * @throws Exception
     */
    public ReplyBean check() throws Exception {
        ReplyBean oReplyBean;
        UsuarioBean oUsuarioBean;
        oUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user");
        if (oUsuarioBean != null) {
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(oUsuarioBean));
        } else {
            oReplyBean = new ReplyBean(401, "No active session");
        }
        return oReplyBean;
    }

    /**
     * Método UPDATE PASS
     *
     * @return Actualiza la password de un Usuario. Devuelve un ReplyBean con el
     * resultado.
     * @throws Exception
     */
    public ReplyBean updatePass() throws Exception {
        Gson oGson = new Gson();
        ReplyBean oReplyBean = null;
        if (checkPermission("updatePass")) {
            ConnectionInterface oConnectionPool = null;
            Connection oConnection;
            UsuarioBean oUsuarioBeanSession;
            oUsuarioBeanSession = (UsuarioBean) oRequest.getSession().getAttribute("user");
            String lastPass = oRequest.getParameter("lastpass");
            String newPass = oRequest.getParameter("newpass");
            if (oUsuarioBeanSession != null) {
                try {
                    oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                    oConnection = oConnectionPool.newConnection();
                    UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, "usuario");
                    oUsuarioDao.updatePass(lastPass, newPass, oUsuarioBeanSession);
                    oReplyBean = new ReplyBean(200, oGson.toJson("Pass updated"));
                } catch (Exception e) {
                    oReplyBean = new ReplyBean(500, e.getMessage());

                } finally {
                    oConnectionPool.disposeConnection();
                }
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

}
