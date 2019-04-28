package net.daw.connection.publicinterface;

import java.sql.Connection;

/**
 *
 * @author Yolanda
 */
public interface ConnectionInterface {

    /**
     *
     * @return
     * @throws Exception
     */
    public Connection newConnection() throws Exception;

    /**
     *
     * @throws Exception
     */
    public void disposeConnection() throws Exception;

}
