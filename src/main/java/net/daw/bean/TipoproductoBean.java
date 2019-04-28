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
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getDesc() {
        return desc;
    }

    /**
     *
     * @param desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     *
     * @param oResultSet
     * @param oConnection
     * @param expand
     * @return
     * @throws Exception
     */
    public TipoproductoBean fill(ResultSet oResultSet, Connection oConnection, Integer expand) throws Exception {
        this.setId(oResultSet.getInt("id"));
        this.setDesc(oResultSet.getString("desc"));
        return this;
    }
}
