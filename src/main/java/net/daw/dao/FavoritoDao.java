package net.daw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import net.daw.bean.ProductoBean;
import net.daw.helper.SqlBuilder;

/**
 *
 * @author Yolanda
 */
public class FavoritoDao {

    Connection oConnection;
    String ob = null;

    public FavoritoDao(Connection oConnection, String ob) {
        super();
        this.oConnection = oConnection;
        this.ob = ob;
    }

    public int remove(int idProducto, int idUsuario) throws Exception {
        int iRes = 0;
        String strSQL = "DELETE FROM favorito WHERE id_producto = ? AND id_usuario = ?";
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, idProducto);
            oPreparedStatement.setInt(2, idUsuario);
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

    public int getcountfav(int id) throws Exception {
        String strSQL = "SELECT COUNT(id) FROM favorito";
        strSQL += " WHERE id_usuario = ?";
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

    public int create(int idProducto, int idUsuario) throws Exception {
        int iRes = 0;
        String strSQL = "INSERT INTO favorito (`id`, `id_producto`, `id_usuario`) VALUES (NULL, ?,?); ";
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, idProducto);
            oPreparedStatement.setInt(2, idUsuario);
            iRes = oPreparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error en Dao create de favorito", e);
        } finally {
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return iRes;
    }

    public ArrayList<ProductoBean> getfavoritos(int iRpp, int iPage, HashMap<String, String> hmOrder, Integer expand, int id) throws Exception {

        String strSQL = "SELECT pr.* FROM producto pr, favorito f";

        if (id != 0) {
            strSQL += " WHERE pr.id = f.id_producto AND id_usuario = ?";
        }

        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        ArrayList<ProductoBean> alProductoBean;
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
                alProductoBean = new ArrayList<ProductoBean>();
                while (oResultSet.next()) {
                    ProductoBean oProductoBean = new ProductoBean();

                    oProductoBean.fill(oResultSet, oConnection, expand);

                    alProductoBean.add(oProductoBean);
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
        return alProductoBean;
    }

    public int get(int idProducto, int idUsuario) throws Exception {
        Integer iRes = 0;
        String strSQL = "SELECT * FROM favorito WHERE id_producto = ? AND id_usuario = ?";
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, idProducto);
            oPreparedStatement.setInt(2, idUsuario);
            oResultSet = oPreparedStatement.executeQuery();
            if (oResultSet.next()) {
                iRes = 1;
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
        return iRes;
    }

}
