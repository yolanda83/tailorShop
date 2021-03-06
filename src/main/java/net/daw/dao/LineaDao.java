package net.daw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import net.daw.bean.LineaBean;
import net.daw.helper.SqlBuilder;

/**
 *
 * @author Yolanda
 */
public class LineaDao {

    Connection oConnection;
    String ob = null;

    /**
     * Constructor
     *
     * @param oConnection
     * @param ob
     */
    public LineaDao(Connection oConnection, String ob) {
        super();
        this.oConnection = oConnection;
        this.ob = ob;
    }

    /**
     * Método GET
     *
     * @param id
     * @param expandProducto
     * @param expandFactura
     * @return Devuelve una única línea para una factura concreta
     * @throws Exception
     */
    public LineaBean get(int id, Integer expandProducto, Integer expandFactura) throws Exception {
        String strSQL = "SELECT * FROM " + ob + " WHERE id=?";
        LineaBean oLineaBean;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, id);
            oResultSet = oPreparedStatement.executeQuery();
            if (oResultSet.next()) {
                oLineaBean = new LineaBean();
                oLineaBean.fill(oResultSet, oConnection, expandProducto, expandFactura);
            } else {
                oLineaBean = null;
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
        return oLineaBean;
    }

    /**
     * Método REMOVE
     *
     * @param id
     * @return Borra una línea concreta de una Factura. Devuelve un resultado
     * binario: (1) éxito (0) fallo
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
     * Método GET COUNT
     *
     * @param id
     * @return Devuelve el nº de líneas para una factura concreta
     * @throws Exception
     */
    public int getcount(int id) throws Exception {
        String strSQL = "SELECT COUNT(id) FROM " + ob + " WHERE id_factura = ?";
        int res = 0;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, id);

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
     * Método GET COUNT ESPECIFIC
     *
     * @param id
     * @return Devuelve el nº de líneas específicas de una factura
     * @throws Exception
     */
    public int getcountspecific(int id) throws Exception {
        String strSQL = "SELECT COUNT(id) FROM " + ob + " WHERE id_factura = " + id;
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
     * Método CREATE
     *
     * @param oLineaBean
     * @return Crea una línea para una factura. Devuelve un Bean de Linea
     * relleno.
     * @throws Exception
     */
    public LineaBean create(LineaBean oLineaBean) throws Exception {
        String strSQL = "INSERT INTO " + ob + " (" + ob + ".id, " + ob + ".cantidad, " + ob + ".id_producto, " + ob + ".id_factura) VALUES (NULL, ?, ?, ?); ";
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, oLineaBean.getCantidad());
            oPreparedStatement.setInt(2, oLineaBean.getId_producto());
            oPreparedStatement.setInt(3, oLineaBean.getId_factura());
            oPreparedStatement.executeUpdate();
            oResultSet = oPreparedStatement.getGeneratedKeys();
            if (oResultSet.next()) {
                oLineaBean.setId(oResultSet.getInt(1));
            } else {
                oLineaBean.setId(0);
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
        return oLineaBean;
    }

    /**
     * Método UPDATE
     *
     * @param oLineaBean
     * @return Actualiza una línea de una factura. Devuelve un resultado
     * binario: (1) éxito (0) fallo
     * @throws Exception
     */
    public int update(LineaBean oLineaBean) throws Exception {
        int iResult = 0;
        String strSQL = "UPDATE " + ob + " SET " + ob + ".cantidad = ?, " + ob + ".id_producto = ?, " + ob + ".id_factura=? WHERE " + ob + ".id = ?;";

        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, oLineaBean.getCantidad());
            oPreparedStatement.setInt(2, oLineaBean.getId_producto());
            oPreparedStatement.setInt(3, oLineaBean.getId_factura());
            oPreparedStatement.setInt(4, oLineaBean.getId());
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
     * Método GET PAGE
     *
     * @param iRpp
     * @param iPage
     * @param id
     * @param hmOrder
     * @param expandProducto
     * @param expandFactura
     * @return Devuelve un arrayList con el listado de líneas de una factura.
     * @throws Exception
     */
    public ArrayList<LineaBean> getpage(int iRpp, int iPage, int id, HashMap<String, String> hmOrder, Integer expandProducto, Integer expandFactura) throws Exception {
        String strSQL = "SELECT * FROM " + ob;
        strSQL += " WHERE id_factura = ?";
        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        ArrayList<LineaBean> alLineaBean;
        if (iRpp > 0 && iRpp < 100000 && iPage > 0 && iPage < 100000000) {
            strSQL += " LIMIT " + (iPage - 1) * iRpp + ", " + iRpp;
            ResultSet oResultSet = null;
            PreparedStatement oPreparedStatement = null;
            try {
                oPreparedStatement = oConnection.prepareStatement(strSQL);
                oPreparedStatement.setInt(1, id);
                oResultSet = oPreparedStatement.executeQuery();
                alLineaBean = new ArrayList<LineaBean>();
                while (oResultSet.next()) {
                    LineaBean oLineaBean = new LineaBean();
                    oLineaBean.fill(oResultSet, oConnection, expandProducto, expandFactura);
                    alLineaBean.add(oLineaBean);
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
        return alLineaBean;

    }

}
