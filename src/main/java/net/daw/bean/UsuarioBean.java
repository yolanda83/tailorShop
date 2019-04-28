package net.daw.bean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gson.annotations.Expose;
import net.daw.dao.FacturaDao;
import net.daw.dao.TipousuarioDao;

/**
 *
 * @author Yolanda
 */
public class UsuarioBean {

    @Expose
    private int id;
    @Expose
    private String dni;
    @Expose
    private String nombre;
    @Expose
    private String ape1;
    @Expose
    private String ape2;
    @Expose
    private String login;
    @Expose(serialize = false)
    private String pass;
    @Expose
    private String foto;
    @Expose(serialize = false)
    private int id_tipoUsuario;
    @Expose(deserialize = false)
    private TipousuarioBean obj_tipoUsuario;
    @Expose(deserialize = false)
    private int numFacturas;

    /**
     *
     * @return Devuelve el valor de Nº de Facturas del Usuario
     */
    public int getNumFacturas() {
        return numFacturas;
    }

    /**
     *
     * @param numFacturas Asigna un valor al Nº de Facturas del Usuario
     */
    public void setNumFacturas(int numFacturas) {
        this.numFacturas = numFacturas;
    }

    /**
     *
     * @return Devuelve el valor de obj_tipoUsuario
     */
    public TipousuarioBean getObj_tipoUsuario() {
        return obj_tipoUsuario;
    }

    /**
     *
     * @param obj_tipoUsuario Asigna un valor a obj_tipoUsuario
     */
    public void setObj_tipoUsuario(TipousuarioBean obj_tipoUsuario) {
        this.obj_tipoUsuario = obj_tipoUsuario;
    }

    /**
     *
     * @return Devuelve el valor de Id Usuario
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id Asigna un valor al Id de Usuario
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return Devuelve el valor del DNI del Usuario
     */
    public String getDni() {
        return dni;
    }

    /**
     *
     * @param dni Asigna un valor al DNI del Usuario
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     *
     * @return Devuelve el valor del Nombre del Usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre Asigna un valor al Nombre del Usuario
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return Devuelve el valor del Primer Apellido del Usuario
     */
    public String getApe1() {
        return ape1;
    }

    /**
     *
     * @param ape1 Asigna un valor al Primer Apellido del Usuario
     */
    public void setApe1(String ape1) {
        this.ape1 = ape1;
    }

    /**
     *
     * @return Devuelve el valor del Segundo Apellido del Usuario
     */
    public String getApe2() {
        return ape2;
    }

    /**
     *
     * @param ape2 Asigna un valor al Segundo Apellido del Usuario
     */
    public void setApe2(String ape2) {
        this.ape2 = ape2;
    }

    /**
     *
     * @return Devuelve el valor del Login de Usuario
     */
    public String getLogin() {
        return login;
    }

    /**
     *
     * @param login Asigna un valor al Login de Usuario
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     *
     * @return Devuelve el valor de la contraseña del Usuario
     */
    public String getPass() {
        return pass;
    }

    /**
     *
     * @param pass Asigna un valor a la contraseña de Usuario
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     *
     * @return Devuelve el valor de id_tipoUsuario
     */
    public int getId_tipoUsuario() {
        return id_tipoUsuario;
    }

    /**
     *
     * @param id_tipoUsuario Asigna un valor a id_tipoUsuario
     */
    public void setId_tipoUsuario(int id_tipoUsuario) {
        this.id_tipoUsuario = id_tipoUsuario;
    }

    /**
     *
     * @return Devuelve el valor de la Foto del Usuario
     */
    public String getFoto() {
        return foto;
    }

    /**
     *
     * @param foto Asigna un valor a la Foto del Usuario
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }

    /**
     *
     * @param oResultSet
     * @param oConnection
     * @param expand
     * @return A partir de ResultSet, rellena un POJO completo de Usuario
     * @throws Exception
     */
    public UsuarioBean fill(ResultSet oResultSet, Connection oConnection, Integer expand) throws Exception {
        this.setId(oResultSet.getInt("id"));
        this.setDni(oResultSet.getString("dni"));
        this.setNombre(oResultSet.getString("nombre"));
        this.setApe1(oResultSet.getString("ape1"));
        this.setApe2(oResultSet.getString("ape2"));
        this.setLogin(oResultSet.getString("login"));
        this.setPass(oResultSet.getString("pass"));
        this.setFoto(oResultSet.getString("foto"));
        FacturaDao oFacturaDao = new FacturaDao(oConnection, "factura");
        this.setNumFacturas(oFacturaDao.getcountspecific(this.getId()));
        if (expand > 0) {
            TipousuarioDao otipousuarioDao = new TipousuarioDao(oConnection, "tipousuario");
            this.setObj_tipoUsuario(otipousuarioDao.get(oResultSet.getInt("id_tipoUsuario"), expand - 1));
            this.setId_tipoUsuario(oResultSet.getInt("id_tipoUsuario"));
        } else {
            this.setId_tipoUsuario(oResultSet.getInt("id_tipoUsuario"));
        }
        return this;
    }

}
