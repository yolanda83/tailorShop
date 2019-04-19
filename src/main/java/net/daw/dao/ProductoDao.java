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
public class ProductoDao {

    Connection oConnection;
    String ob = null;

    public ProductoDao(Connection oConnection, String ob) {
        super();
        this.oConnection = oConnection;
        this.ob = ob;
    }

    public ProductoBean get(int id, Integer expand) throws Exception {
        String strSQL = "SELECT * FROM " + ob + " WHERE id=?";
        ProductoBean oProductoBean;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, id);
            oResultSet = oPreparedStatement.executeQuery();
            if (oResultSet.next()) {
                oProductoBean = new ProductoBean();
                oProductoBean.fill(oResultSet, oConnection, expand);
            } else {
                oProductoBean = null;
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
        return oProductoBean;
    }

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

    public int getcounttipo(int tipo) throws Exception {
        String strSQL = "SELECT COUNT(id) FROM " + ob;
        strSQL += " WHERE id_tipoproducto = ?";
        int res = 0;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            if (tipo != 0) {
                oPreparedStatement.setInt(1, tipo);
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

    public int getcountbusqueda(String busqueda) throws Exception {
        String strSQL = "SELECT COUNT(id) FROM " + ob;
        strSQL += " WHERE `desc` LIKE ?";
        int res = 0;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            String busc = "%" + busqueda + "%";
            oPreparedStatement.setString(1, busc);
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

    public ProductoBean create(ProductoBean oProductoBean) throws Exception {
        String strSQL = "INSERT INTO " + ob + " (`id`, `codigo`, `desc`, `existencias`, `precio`, `foto`, `id_tipoProducto`, `novedad`, `fecha`) VALUES (NULL,?,?,?,?,?,?,?,?); ";

        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;

        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setString(1, oProductoBean.getCodigo());
            oPreparedStatement.setString(2, oProductoBean.getDesc());
            oPreparedStatement.setInt(3, oProductoBean.getExistencias());
            oPreparedStatement.setFloat(4, oProductoBean.getPrecio());
            oPreparedStatement.setString(5, oProductoBean.getFoto());
            oPreparedStatement.setInt(6, oProductoBean.getId_tipoProducto());
            oPreparedStatement.setBoolean(7, oProductoBean.isNovedad());

            String fecha = oProductoBean.getFecha();

            String fechaFinal = getFecha(fecha);

            oPreparedStatement.setString(8, fechaFinal);
            oPreparedStatement.executeUpdate();
            oResultSet = oPreparedStatement.getGeneratedKeys();
            if (oResultSet.next()) {
                oProductoBean.setId(oResultSet.getInt(1));
            } else {
                oProductoBean.setId(0);
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
        return oProductoBean;
    }

    public int update(ProductoBean oProductoBean) throws Exception {
        int iResult = 0;
        String strSQL = "UPDATE " + ob + " SET " + ob + ".codigo = ?,  " + ob + ".desc = ?,  " + ob + ".existencias = ?, " + ob
                + ".precio = ?, " + ob + ".foto = ?, " + ob + ".id_tipoProducto = ?, " + ob + ".novedad = ?, " + ob + ".fecha = ? WHERE " + ob + ".id = ?;";

        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setString(1, oProductoBean.getCodigo());
            oPreparedStatement.setString(2, oProductoBean.getDesc());
            oPreparedStatement.setInt(3, oProductoBean.getExistencias());
            oPreparedStatement.setFloat(4, oProductoBean.getPrecio());
            oPreparedStatement.setString(5, oProductoBean.getFoto());
            oPreparedStatement.setInt(6, oProductoBean.getId_tipoProducto());
            oPreparedStatement.setBoolean(7, oProductoBean.isNovedad());

            String fecha = oProductoBean.getFecha();

            String fecha1 = fecha.substring(1, 2);

            if (!fecha1.matches("[0-9]*")) {
                String fechaFinal = getFecha(fecha);
                oPreparedStatement.setString(8, fechaFinal);
            } else {
                oPreparedStatement.setString(8, fecha);
            }

            oPreparedStatement.setInt(9, oProductoBean.getId());
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

    public ArrayList<ProductoBean> getpage(int iRpp, int iPage, HashMap<String, String> hmOrder, Integer expand, int tipo) throws Exception {
        String strSQL = "SELECT * FROM " + ob;

        if (tipo != 0) {
            strSQL += " WHERE id_tipoproducto = ?";
        }

        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        ArrayList<ProductoBean> alProductoBean;
        if (iRpp > 0 && iRpp < 100000 && iPage > 0 && iPage < 100000000) {
            strSQL += " LIMIT " + (iPage - 1) * iRpp + ", " + iRpp;
            ResultSet oResultSet = null;
            PreparedStatement oPreparedStatement = null;
            try {
                oPreparedStatement = oConnection.prepareStatement(strSQL);
                if (tipo != 0) {
                    oPreparedStatement.setInt(1, tipo);
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

    public ArrayList<ProductoBean> getfavoritos(int iRpp, int iPage, HashMap<String, String> hmOrder, Integer expand, int id) throws Exception {

        String strSQL = "SELECT id_producto FROM favorito";

        if (id != 0) {
            strSQL += " WHERE id_usuario = ?";
        }

        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        ArrayList<ProductoBean> alProductoBean;
        try {
            StringBuilder sbId = new StringBuilder();
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            if (id != 0) {
                oPreparedStatement.setInt(1, id);
            }
            oResultSet = oPreparedStatement.executeQuery();

            int rows = 0;

            if (oResultSet.last()) {
                rows = oResultSet.getRow();
                // Move to beginning
                oResultSet.beforeFirst();
            }

            sbId.append("(");
            int contador = 0;
            while (oResultSet.next()) {

                contador++;

                if (contador != rows) {
                    sbId.append(oResultSet.getInt("id_producto"));
                    sbId.append(", ");
                } else {
                    sbId.append(oResultSet.getInt("id_producto"));
                }

            }

            sbId.append(")");

            String strSQL2 = "SELECT * FROM " + ob;
            strSQL2 += " WHERE id IN " + sbId;

            strSQL2 += SqlBuilder.buildSqlOrder(hmOrder);
            if (iRpp > 0 && iRpp < 100000 && iPage > 0 && iPage < 100000000) {
                strSQL2 += " LIMIT " + (iPage - 1) * iRpp + ", " + iRpp;

                ResultSet oResultSet2 = null;
                PreparedStatement oPreparedStatement2 = null;

                oPreparedStatement2 = oConnection.prepareStatement(strSQL2);
                oResultSet2 = oPreparedStatement2.executeQuery();

                alProductoBean = new ArrayList<ProductoBean>();

                while (oResultSet2.next()) {

                    ProductoBean oProductoBean = new ProductoBean();

                    oProductoBean.fill(oResultSet2, oConnection, expand);

                    alProductoBean.add(oProductoBean);

                }

            } else {
                throw new Exception("Error en Dao getpage de " + ob);
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

        return alProductoBean;
    }

    public ArrayList<ProductoBean> getbusqueda(int iRpp, int iPage, HashMap<String, String> hmOrder, Integer expand, String busqueda) throws Exception {
        String strSQL = "SELECT * FROM " + ob;
        strSQL += " WHERE `desc` LIKE ?";

        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        ArrayList<ProductoBean> alProductoBean;

        if (iRpp > 0 && iRpp < 100000 && iPage > 0 && iPage < 100000000) {
            strSQL += " LIMIT " + (iPage - 1) * iRpp + ", " + iRpp;
            ResultSet oResultSet = null;
            PreparedStatement oPreparedStatement = null;
            try {
                oPreparedStatement = oConnection.prepareStatement(strSQL);

                String busc = "%" + busqueda + "%";
                oPreparedStatement.setString(1, busc);

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

    public ArrayList<ProductoBean> getnovedad(Integer expand) throws Exception {
        String strSQL = "SELECT * FROM " + ob;
        strSQL += " WHERE novedad = 1 ORDER BY fecha DESC";

        ArrayList<ProductoBean> alProductoBean;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
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

        return alProductoBean;
    }

    public String getFecha(String fecha) {

        String fechaFinal = "";
        String mesN = "";

        String mes = fecha.substring(4, 7);
        String dia = fecha.substring(8, 10);
        String anyo = fecha.substring(11, 15);
        String hora = fecha.substring(16, 24);

        switch (mes) {
            case "Jan":
                mesN = "01";
                break;
            case "Feb":
                mesN = "02";
                break;
            case "Mar":
                mesN = "03";
                break;
            case "Apr":
                mesN = "04";
                break;
            case "May":
                mesN = "05";
                break;
            case "Jun":
                mesN = "06";
                break;
            case "Jul":
                mesN = "07";
                break;
            case "Aug":
                mesN = "08";
                break;
            case "Sep":
                mesN = "09";
                break;
            case "Oct":
                mesN = "10";
                break;
            case "Nov":
                mesN = "11";
                break;
            case "Dec":
                mesN = "12";
                break;
            default:

        }

        fechaFinal = anyo + mesN + dia + ' ' + hora;

        return fechaFinal;

    }
}
