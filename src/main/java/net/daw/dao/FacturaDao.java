/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import net.daw.bean.FacturaBean;
import net.daw.bean.UsuarioBean;
import net.daw.helper.SqlBuilder;

/**
 *
 * @author Yolanda
 */
public class FacturaDao {

    Connection oConnection;
    String ob = null;

    /**
     *
     * @param oConnection
     * @param ob
     */
    public FacturaDao(Connection oConnection, String ob) {
        super();
        this.oConnection = oConnection;
        this.ob = ob;
    }

    /**
     *
     * @param id
     * @param expansion
     * @return
     * @throws Exception
     */
    public FacturaBean get(int id, Integer expansion) throws Exception {
        String strSQL = "SELECT * FROM " + ob + " WHERE id=?";
        FacturaBean oFacturaBean;
//        UsuarioBean oUsuarioBean;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, id);
            oResultSet = oPreparedStatement.executeQuery();
            if (oResultSet.next()) {
                oFacturaBean = new FacturaBean();
                oFacturaBean.fill(oResultSet, oConnection, expansion);
            } else {
                oFacturaBean = null;
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
        return oFacturaBean;
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
     * @param id
     * @return
     * @throws Exception
     */
    public int getcount(int id) throws Exception {
        String strSQL = "SELECT COUNT(id) FROM " + ob;

        if (id != 0) {
            strSQL += " WHERE id_usuario = ?";
        }

        int res = 0;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);

            if (id != 0) {
                oPreparedStatement.setInt(1, id);
            }

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
     * @param id
     * @return
     * @throws Exception
     */
    public int getcountspecific(int id) throws Exception {
        String strSQL = "SELECT COUNT(id) FROM " + ob + " WHERE id_usuario=" + id;
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
     * @param oFacturaBean
     * @return
     * @throws Exception
     */
    public FacturaBean create(FacturaBean oFacturaBean) throws Exception {

        String strSQL = "INSERT INTO " + ob;
        strSQL += "(" + oFacturaBean.getColumns() + ")";
        strSQL += " VALUES ";
        strSQL += "(" + oFacturaBean.getValues() + ")";
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.executeUpdate();
            oResultSet = oPreparedStatement.getGeneratedKeys();
            if (oResultSet.next()) {
                oFacturaBean.setId(oResultSet.getInt(1));
            } else {
                oFacturaBean.setId(0);
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
        return oFacturaBean;
    }

    /**
     *
     * @param oFacturaBean
     * @return
     * @throws Exception
     */
    public int update(FacturaBean oFacturaBean) throws Exception {
        int iResult = 0;
        String strSQL = "UPDATE " + ob + " SET ";
        strSQL += oFacturaBean.getPairs();

        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
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
     * @param id
     * @param hmOrder
     * @param expand
     * @return
     * @throws Exception
     */
    public ArrayList<FacturaBean> getpage(int iRpp, int iPage, int id, HashMap<String, String> hmOrder, Integer expand) throws Exception {
        String strSQL = "SELECT * FROM " + ob;
        if (id != 0) {
            strSQL += " WHERE id_usuario = ?";
        }
        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        ArrayList<FacturaBean> alFacturaBean;

        if (iRpp > 0 && iRpp < 100000 && iPage > 0 && iPage < 100000000) {
            strSQL += " LIMIT " + (iPage - 1) * iRpp + ", " + iRpp;
            ResultSet oResultSet = null;
            PreparedStatement oPreparedStatement = null;
            try {
                oPreparedStatement = oConnection.prepareStatement(strSQL);

                if (id != 0) {
                    oPreparedStatement.setInt(1, id);
                }

                oResultSet = oPreparedStatement.executeQuery();
                alFacturaBean = new ArrayList<FacturaBean>();
                while (oResultSet.next()) {
                    FacturaBean oFacturaBean = new FacturaBean();
                    oFacturaBean.fill(oResultSet, oConnection, expand);
                    alFacturaBean.add(oFacturaBean);
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
        return alFacturaBean;

    }

}
