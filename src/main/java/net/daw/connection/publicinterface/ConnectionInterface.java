package net.daw.connection.publicinterface;

import java.sql.Connection;

/**
 *
 * @author Yolanda
 */
public interface ConnectionInterface {

    /** Crea una nueva conexión a BBDD
     *
     * @return Devuelve una nueva conexión a BBDD
     * @throws Exception
     */
    public Connection newConnection() throws Exception;

    /** Cierra la conexión de BBDD
     *
     * @throws Exception
     */
    public void disposeConnection() throws Exception;

}
