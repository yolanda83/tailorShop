package net.daw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import net.daw.bean.UsuarioBean;
import net.daw.helper.SqlBuilder;

/**
 *
 * @author Yolanda
 */
public class UsuarioDao {

    Connection oConnection;
    String ob = null;
    String ob2 = "tipoUsuario";

    /**
     *
     * @param oConnection
     * @param ob
     */
    public UsuarioDao(Connection oConnection, String ob) {
        super();
        this.oConnection = oConnection;
        this.ob = ob;
    }

    /**
     *
     * @param id
     * @param expand
     * @return
     * @throws Exception
     */
    public UsuarioBean get(int id, Integer expand) throws Exception {
        String strSQL = "SELECT * FROM " + ob + " WHERE id=?";
        UsuarioBean oUsuarioBean;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, id);
            oResultSet = oPreparedStatement.executeQuery();
            if (oResultSet.next()) {
                oUsuarioBean = new UsuarioBean();
                oUsuarioBean.fill(oResultSet, oConnection, expand);

            } else {
                oUsuarioBean = null;
            }
        } catch (SQLException e) {
            throw new Exception("Error en Dao get de " + ob, e);
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return oUsuarioBean;
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public int remove(int id) throws Exception {
        int iRes = 0;
        String strSQL = "DELETE FROM " + ob + " WHERE id=?";
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, id);
            iRes = oPreparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error en Dao remove de " + ob, e);
        } finally {
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return iRes;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public int getcount() throws Exception {
        String strSQL = "SELECT COUNT(id) FROM " + ob;
        int res = 0;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oResultSet = oPreparedStatement.executeQuery();
            if (oResultSet.next()) {
                res = oResultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new Exception("Error en Dao get de " + ob, e);
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return res;
    }

    /**
     *
     * @param oUsuarioBean
     * @return
     * @throws Exception
     */
    public UsuarioBean create(UsuarioBean oUsuarioBean) throws Exception {
        String strSQL = "INSERT INTO " + ob
                + " (id,dni,nombre,ape1,ape2,login,pass,foto,id_tipoUsuario) VALUES (NULL, ?,?,?,?,?,?,?,?); ";
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setString(1, oUsuarioBean.getDni());
            oPreparedStatement.setString(2, oUsuarioBean.getNombre());
            oPreparedStatement.setString(3, oUsuarioBean.getApe1());
            oPreparedStatement.setString(4, oUsuarioBean.getApe2());
            oPreparedStatement.setString(5, oUsuarioBean.getLogin());
            oPreparedStatement.setString(6, oUsuarioBean.getPass());
            oPreparedStatement.setString(7, oUsuarioBean.getFoto());
            oPreparedStatement.setInt(8, oUsuarioBean.getId_tipoUsuario());
            oPreparedStatement.executeUpdate();
            oResultSet = oPreparedStatement.getGeneratedKeys();
            if (oResultSet.next()) {
                oUsuarioBean.setId(oResultSet.getInt(1));
                oUsuarioBean.setPass(null);
            } else {
                oUsuarioBean.setId(0);
                oUsuarioBean.setPass(null);
            }
        } catch (SQLException e) {
            throw new Exception("Error en Dao create de " + ob, e);
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return oUsuarioBean;
    }

    /**
     *
     * @param oUsuarioBean
     * @return
     * @throws Exception
     */
    public int update(UsuarioBean oUsuarioBean) throws Exception {
        int iResult = 0;
        String strSQL = "UPDATE " + ob
                + " SET dni = ?, nombre = ?, ape1 = ?, ape2 = ?, login = ?, foto = ?, id_tipoUsuario = ? WHERE id = ? ;";

        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setString(1, oUsuarioBean.getDni());
            oPreparedStatement.setString(2, oUsuarioBean.getNombre());
            oPreparedStatement.setString(3, oUsuarioBean.getApe1());
            oPreparedStatement.setString(4, oUsuarioBean.getApe2());
            oPreparedStatement.setString(5, oUsuarioBean.getLogin());
//            oPreparedStatement.setString(6, oUsuarioBean.getPass());
            oPreparedStatement.setString(6, oUsuarioBean.getFoto());
            oPreparedStatement.setInt(7, oUsuarioBean.getId_tipoUsuario());
            oPreparedStatement.setInt(8, oUsuarioBean.getId());
            iResult = oPreparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Error en Dao update de " + ob, e);
        } finally {
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return iResult;
    }

    /**
     *
     * @param iRpp
     * @param iPage
     * @param hmOrder
     * @param expand
     * @return
     * @throws Exception
     */
    public ArrayList<UsuarioBean> getpage(int iRpp, int iPage, HashMap<String, String> hmOrder, Integer expand) throws Exception {
        String strSQL = "SELECT * FROM " + ob;
        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        ArrayList<UsuarioBean> alUsuarioBean;
        if (iRpp > 0 && iRpp < 100000 && iPage > 0 && iPage < 100000000) {
            strSQL += " LIMIT " + (iPage - 1) * iRpp + ", " + iRpp;
            ResultSet oResultSet = null;
            PreparedStatement oPreparedStatement = null;
            try {
                oPreparedStatement = oConnection.prepareStatement(strSQL);
                oResultSet = oPreparedStatement.executeQuery();
                alUsuarioBean = new ArrayList<UsuarioBean>();
                while (oResultSet.next()) {
                    UsuarioBean oUsuarioBean = new UsuarioBean();

                    oUsuarioBean.fill(oResultSet, oConnection, expand);

                    alUsuarioBean.add(oUsuarioBean);
                }
            } catch (SQLException e) {
                throw new Exception("Error en Dao getpage de " + ob, e);
            } finally {
                if (oResultSet != null) {
                    oResultSet.close();
                }
                if (oPreparedStatement != null) {
                    oPreparedStatement.close();
                }
            }
        } else {
            throw new Exception("Error en Dao getpage de " + ob);
        }
        return alUsuarioBean;
    }

    /**
     *
     * @param strUserName
     * @param strPassword
     * @return
     * @throws Exception
     */
    public UsuarioBean login(String strUserName, String strPassword) throws Exception {
        String strSQL = "SELECT * FROM " + ob + " WHERE login=? AND pass=?";
        UsuarioBean oUsuarioBean;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setString(1, strUserName);
            oPreparedStatement.setString(2, strPassword);
            oResultSet = oPreparedStatement.executeQuery();
            if (oResultSet.next()) {
                oUsuarioBean = new UsuarioBean();
                oUsuarioBean.fill(oResultSet, oConnection, 1);
            } else {
                oUsuarioBean = null;
            }
        } catch (SQLException e) {
            throw new Exception("Error en Dao get de " + ob, e);
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return oUsuarioBean;
    }

    /**
     *
     * @param lastPass
     * @param newPass
     * @param usuarioSession
     * @throws Exception
     */
    public void updatePass(String lastPass, String newPass, UsuarioBean usuarioSession) throws Exception {
        String strSQL = "SELECT * FROM " + ob + " WHERE id=" + usuarioSession.getId();
        UsuarioBean oUsuarioBean = null;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oResultSet = oPreparedStatement.executeQuery();
            while (oResultSet.next()) {
                oUsuarioBean = new UsuarioBean();
                oUsuarioBean.fill(oResultSet, oConnection, 1);
            }

        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        if (!lastPass.equals(oUsuarioBean.getPass())) {
            throw new Exception("El password anterior no coincide.");
        } else if (newPass.equals(usuarioSession.getPass())) {
            throw new Exception("Password nuevo igual a password antiguo");
        } else {
            usuarioSession.setPass(newPass);
            updateconpass(usuarioSession);
        }
    }

    /**
     *
     * @param oUsuarioBean
     * @return
     * @throws Exception
     */
    public int updateconpass(UsuarioBean oUsuarioBean) throws Exception {
        int iResult = 0;
        String strSQL = "UPDATE " + ob
                + " SET dni = ?, nombre = ?, ape1 = ?, ape2 = ?, login = ?, pass = ?, foto = ?, id_tipoUsuario = ? WHERE id = ? ;";

        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setString(1, oUsuarioBean.getDni());
            oPreparedStatement.setString(2, oUsuarioBean.getNombre());
            oPreparedStatement.setString(3, oUsuarioBean.getApe1());
            oPreparedStatement.setString(4, oUsuarioBean.getApe2());
            oPreparedStatement.setString(5, oUsuarioBean.getLogin());
            oPreparedStatement.setString(6, oUsuarioBean.getPass());
            oPreparedStatement.setString(7, oUsuarioBean.getFoto());
            oPreparedStatement.setInt(8, oUsuarioBean.getId_tipoUsuario());
            oPreparedStatement.setInt(9, oUsuarioBean.getId());
            iResult = oPreparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Error en Dao update de " + ob, e);
        } finally {
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return iResult;
    }
}
