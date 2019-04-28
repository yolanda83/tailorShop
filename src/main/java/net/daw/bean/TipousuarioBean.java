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
    public TipousuarioBean fill(ResultSet oResultSet, Connection oConnection, Integer expand) throws Exception {
        this.setId(oResultSet.getInt("id"));
        this.setDesc(oResultSet.getString("desc"));
        return this;
    }
}
