/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.bean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import net.daw.dao.LineaDao;
import net.daw.dao.UsuarioDao;
import net.daw.helper.EncodingHelper;

/**
 *
 * @author Yolanda
 */
public class FacturaBean {

    private int id;
    private Date fecha;
    private double iva;
    private UsuarioBean obj_usuario;
    private int numLineas;
    private int id_usuario;

    /**
     *
     * @return Devuelve el valor del Id Usuario de la factura
     */
    public int getId_usuario() {
        return id_usuario;
    }

    /**
     *
     * @param id_usuario Asigna un valor al Id Usuario de la factura
     */
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    /**
     *
     * @return Devuelve el valor del Nº de Líneas de la Factura
     */
    public int getNumLineas() {
        return numLineas;
    }

    /**
     *
     * @param numLineas Asigna un valor a Nº de Líneas
     */
    public void setNumLineas(int numLineas) {
        this.numLineas = numLineas;
    }

    /**
     *
     * @return Devuelve el valor del Id de la Factura
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id Asigna un valor a Id de Factura
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return Devuelve el valor de Fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     *
     * @param fecha Asigna un valor a Fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     *
     * @return Devuelve el valor de Iva
     */
    public double getIva() {
        return iva;
    }

    /**
     *
     * @param iva Asigna un valor a Iva
     */
    public void setIva(double iva) {
        this.iva = iva;
    }

    /**
     *
     * @return Devuelve el valor de obj_usuario
     */
    public UsuarioBean getObj_usuario() {
        return obj_usuario;
    }

    /**
     *
     * @param obj_usuario Asigna un valor a obj_usuario
     */
    public void setObj_usuario(UsuarioBean obj_usuario) {
        this.obj_usuario = obj_usuario;
    }

    /**
     *
     * @param oResultSet
     * @param oConnection
     * @param expand
     * @return A partir de ResultSet, rellena un POJO completo de Factura
     * @throws SQLException
     * @throws Exception
     */
    public FacturaBean fill(ResultSet oResultSet, Connection oConnection, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setFecha(oResultSet.getDate("fecha"));
        this.setIva(oResultSet.getDouble("iva"));
        LineaDao oLineaDao = new LineaDao(oConnection, "linea");
        this.setNumLineas(oLineaDao.getcountspecific(this.getId()));
        if (expand > 0) {
            UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, "usuario");
            this.setObj_usuario(oUsuarioDao.get(oResultSet.getInt("id_usuario"), expand));
            System.out.println(obj_usuario.getId());
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
        strColumns += "fecha,";
        strColumns += "iva,";
        strColumns += "id_usuario";
        return strColumns;
    }

    /**
     *
     * @return Obtiene los valores recogidos en formulario y los guarda en una estructura para la sentencia SQL CREATE
     */
    public String getValues() {
        //Getting the default zone id
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //Converting the date to Instant
        Instant instant = fecha.toInstant();

        //Converting the Date to LocalDate
        LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
        String strColumns = "";
        strColumns += "null,";
        strColumns += EncodingHelper.quotate(localDate.toString()) + ",";
        strColumns += iva + ",";
        if (obj_usuario == null) {
            strColumns += id_usuario;
        } else {
            strColumns += getObj_usuario().getId();
        }

        return strColumns;
    }

    /**
     *
     * @return Obtiene los valores recogidos en formulario y los guarda en una estructura para la sentencia SQL UPDATE
     */
    public String getPairs() {

        //Getting the default zone id
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = null;

        if (fecha != null) {
            //Converting the date to Instant
            instant = fecha.toInstant();
        } else {
            Date fechaActual = new Date();
            instant = fechaActual.toInstant();
        }
        //Converting the Date to LocalDate
        LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();

        String strPairs = "";
        strPairs += "id=" + id + ",";
        strPairs += "fecha=" + EncodingHelper.quotate(localDate.toString()) + ",";
        strPairs += "iva=" + iva + ",";
        strPairs += "id_usuario=" + obj_usuario.getId();
        strPairs += " WHERE id=" + id;
        return strPairs;
    }

}
