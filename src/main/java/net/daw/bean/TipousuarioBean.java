package net.daw.bean;

import java.sql.Connection;
import java.sql.ResultSet;
import com.google.gson.annotations.Expose;

/**
 *
 * @author Yolanda
 */
public class TipousuarioBean {

    @Expose
    private int id;
    @Expose
    private String desc;

    /**
     *
     * @return Devuelve el valor de Id Tipo Usuario
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id Asigna un valor a Id Tipo Usuario
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return Devuelve el valor de Descripción de Tipo Usuario
     */
    public String getDesc() {
        return desc;
    }

    /**
     *
     * @param desc Asigna un valor a Descripción Tipo Usuario
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     *
     * @param oResultSet
     * @param oConnection
     * @param expand
     * @return A partir de ResultSet, rellena un POJO completo de Tipo Usuario
     * @throws Exception
     */
    public TipousuarioBean fill(ResultSet oResultSet, Connection oConnection, Integer expand) throws Exception {
        this.setId(oResultSet.getInt("id"));
        this.setDesc(oResultSet.getString("desc"));
        return this;
    }
}
