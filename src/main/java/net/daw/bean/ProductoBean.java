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
     * @return Devuelve el valor de obj_tipoProducto
     */
    public TipoproductoBean getObj_tipoProducto() {
        return obj_tipoProducto;
    }

    /**
     *
     * @param obj_tipoProducto Asigna un valor a obj_tipoProducto
     */
    public void setObj_tipoProducto(TipoproductoBean obj_tipoProducto) {
        this.obj_tipoProducto = obj_tipoProducto;
    }

    /**
     *
     * @return Devuelve el valor del Id Producto
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id Asigna un valor a Id Producto
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return Devuelve el valor del código del Producto
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     *
     * @param codigo Asigna un valor a Código del Producto
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     *
     * @return Devuelve el valor de la descripción del Producto
     */
    public String getDesc() {
        return desc;
    }

    /**
     *
     * @param desc Asigna un valor a Descripción del Producto
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     *
     * @return Devuelve el valor de las existencias del Producto
     */
    public int getExistencias() {
        return existencias;
    }

    /**
     *
     * @param existencias Asigna un valor a Existencias del Producto
     */
    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    /**
     *
     * @return Devuelve el valor del Precio del Producto
     */
    public float getPrecio() {
        return precio;
    }

    /**
     *
     * @param precio Asigna un valor al Precio del Producto
     */
    public void setPrecio(float precio) {
        this.precio = precio;
    }

    /**
     *
     * @return Devuelve el valor de la Foto del producto
     */
    public String getFoto() {
        return foto;
    }

    /**
     *
     * @param foto Asigna un valor a Foto del Producto
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }

    /**
     *
     * @return Devuelve el valor del Id Tipo Producto
     */
    public int getId_tipoProducto() {
        return id_tipoProducto;
    }

    /**
     *
     * @param id_tipoProducto Asigna un valor a Id Tipo Producto
     */
    public void setId_tipoProducto(int id_tipoProducto) {
        this.id_tipoProducto = id_tipoProducto;
    }

    /**
     *
     * @return Devuelve el valor TRUE o FALSE de Novedad
     */
    public boolean isNovedad() {
        return novedad;
    }

    /**
     *
     * @param novedad Asigna un valor TRUE o FALSE a Novedad
     */
    public void setNovedad(boolean novedad) {
        this.novedad = novedad;
    }

    /**
     *
     * @return Devuelve el valor de la Fecha de subida del Producto
     */
    public String getFecha() {
        return fecha;
    }

    /**
     *
     * @param fecha Asigna un valor a Fecha de subida del Producto
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     *
     * @param oResultSet
     * @param oConnection
     * @param expand
     * @return A partir de ResultSet, rellena un POJO completo de Producto
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
     * @return Obtiene las columnas de la tabla de BBDD
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
     * @return Obtiene los valores recogidos en formulario y los guarda en una estructura para las sentencias SQL
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
