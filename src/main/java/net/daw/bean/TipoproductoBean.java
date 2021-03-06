package net.daw.bean;

import com.google.gson.annotations.Expose;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author Yolanda
 */
public class TipoproductoBean {

    @Expose
    private int id;
    @Expose
    private String desc;

    /**
     *
     * @return Devuelve el valor de Id Tipo Producto
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id Asigna un valor a Id Tipo Producto
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return Devuelve el valor de Descripción del Tipo Producto
     */
    public String getDesc() {
        return desc;
    }

    /**
     *
     * @param desc Asigna un valor a Descripción del Tipo Producto
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     *
     * @param oResultSet
     * @param oConnection
     * @param expand
     * @return A partir de ResultSet, rellena un POJO completo de Tipo Producto
     * @throws Exception
     */
    public TipoproductoBean fill(ResultSet oResultSet, Connection oConnection, Integer expand) throws Exception {
        this.setId(oResultSet.getInt("id"));
        this.setDesc(oResultSet.getString("desc"));
        return this;
    }
}
