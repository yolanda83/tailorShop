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
     * @return
     */
    public int getNumFacturas() {
        return numFacturas;
    }

    /**
     *
     * @param numFacturas
     */
    public void setNumFacturas(int numFacturas) {
        this.numFacturas = numFacturas;
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
     * @return
     */
    public TipousuarioBean getObj_tipoUsuario() {
        return obj_tipoUsuario;
    }

    /**
     *
     * @param obj_tipoUsuario
     */
    public void setObj_tipoUsuario(TipousuarioBean obj_tipoUsuario) {
        this.obj_tipoUsuario = obj_tipoUsuario;
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
    public String getDni() {
        return dni;
    }

    /**
     *
     * @param dni
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public String getApe1() {
        return ape1;
    }

    /**
     *
     * @param ape1
     */
    public void setApe1(String ape1) {
        this.ape1 = ape1;
    }

    /**
     *
     * @return
     */
    public String getApe2() {
        return ape2;
    }

    /**
     *
     * @param ape2
     */
    public void setApe2(String ape2) {
        this.ape2 = ape2;
    }

    /**
     *
     * @return
     */
    public String getLogin() {
        return login;
    }

    /**
     *
     * @param login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     *
     * @return
     */
    public String getPass() {
        return pass;
    }

    /**
     *
     * @param pass
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     *
     * @return
     */
    public int getId_tipoUsuario() {
        return id_tipoUsuario;
    }

    /**
     *
     * @param id_tipoUsuario
     */
    public void setId_tipoUsuario(int id_tipoUsuario) {
        this.id_tipoUsuario = id_tipoUsuario;
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
     * @param oResultSet
     * @param oConnection
     * @param expand
     * @return
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
