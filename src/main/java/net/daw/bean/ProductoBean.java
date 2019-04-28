/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.bean;

import com.google.gson.annotations.Expose;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import net.daw.dao.TipoproductoDao;
import net.daw.helper.EncodingHelper;

/**
 *
 * @author Yolanda
 */
public class ProductoBean {

    @Expose
    private int id;
    @Expose
    private String codigo;
    @Expose
    private String desc;
    @Expose
    private int existencias;
    @Expose
    private float precio;
    @Expose
    private String foto;
    @Expose(serialize = false)
    private int id_tipoProducto;
    @Expose(deserialize = false)
    private TipoproductoBean obj_tipoProducto;
    @Expose
    private boolean novedad;
    @Expose
    private String fecha;

    /**
     *
     * @return
     */
    public TipoproductoBean getObj_tipoProducto() {
        return obj_tipoProducto;
    }

    /**
     *
     * @param obj_tipoProducto
     */
    public void setObj_tipoProducto(TipoproductoBean obj_tipoProducto) {
        this.obj_tipoProducto = obj_tipoProducto;
    }

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
    public String getCodigo() {
        return codigo;
    }

    /**
     *
     * @param codigo
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
     * @return
     */
    public int getExistencias() {
        return existencias;
    }

    /**
     *
     * @param existencias
     */
    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    /**
     *
     * @return
     */
    public float getPrecio() {
        return precio;
    }

    /**
     *
     * @param precio
     */
    public void setPrecio(float precio) {
        this.precio = precio;
    }

    /**
     *
     * @return
     */
    public String getFoto() {
        return foto;
    }

    /**
     *
     * @param foto
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }

    /**
     *
     * @return
     */
    public int getId_tipoProducto() {
        return id_tipoProducto;
    }

    /**
     *
     * @param id_tipoProducto
     */
    public void setId_tipoProducto(int id_tipoProducto) {
        this.id_tipoProducto = id_tipoProducto;
    }

    /**
     *
     * @return
     */
    public boolean isNovedad() {
        return novedad;
    }

    /**
     *
     * @param novedad
     */
    public void setNovedad(boolean novedad) {
        this.novedad = novedad;
    }

    /**
     *
     * @return
     */
    public String getFecha() {
        return fecha;
    }

    /**
     *
     * @param fecha
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     *
     * @param oResultSet
     * @param oConnection
     * @param expand
     * @return
     * @throws Exception
     */
    public ProductoBean fill(ResultSet oResultSet, Connection oConnection, Integer expand) throws Exception {
        this.setId(oResultSet.getInt("id"));
        this.setCodigo(oResultSet.getString("codigo"));
        this.setDesc(oResultSet.getString("desc"));
        this.setExistencias(oResultSet.getInt("existencias"));
        this.setPrecio(oResultSet.getFloat("precio"));
        this.setFoto(oResultSet.getString("foto"));
        this.setId_tipoProducto(oResultSet.getInt("id_tipoProducto"));
        this.setNovedad(oResultSet.getBoolean("novedad"));
        this.setFecha(oResultSet.getString("fecha"));
        if (expand > 0) {
            TipoproductoDao otipoproductoDao = new TipoproductoDao(oConnection, "tipoproducto");
            this.setObj_tipoProducto(otipoproductoDao.get(oResultSet.getInt("id_tipoProducto"), expand - 1));
        } else {
            this.setId_tipoProducto(oResultSet.getInt("id_tipoProducto"));
        }
        return this;
    }

    /**
     *
     * @return
     */
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "codigo,";
        strColumns += "desc,";
        strColumns += "existencias,";
        strColumns += "precio,";
        strColumns += "foto,";
        strColumns += "id_tipoProducto,";
        strColumns += "novedad,";
        strColumns += "fecha";
        return strColumns;
    }

    /**
     *
     * @return
     */
    public String getValues() {

        String strColumns = "";
        strColumns += "NULL,";
        strColumns += codigo + ",";
        strColumns += desc + ",";
        strColumns += existencias + ",";
        strColumns += precio + ",";
        strColumns += foto + ",";
        strColumns += id_tipoProducto + ",";
        strColumns += novedad + ",";
        strColumns += fecha;

        return strColumns;
    }

}
