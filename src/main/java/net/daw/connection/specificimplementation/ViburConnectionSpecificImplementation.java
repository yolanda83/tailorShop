package net.daw.connection.specificimplementation;

import java.sql.Connection;
import java.sql.SQLException;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import org.vibur.dbcp.ViburDBCPDataSource;

/** 
 *
 * @author Yolanda
 */

public class ViburConnectionSpecificImplementation implements ConnectionInterface {

    private Connection oConnection;
    private ViburDBCPDataSource oConnectionPool;

    /** Crea una conexión de BBDD
     *
     * @return Devuelve una conexión de BBDD
     * @throws Exception
     */
    public Connection newConnection() throws Exception {

        oConnectionPool = new ViburDBCPDataSource();
        oConnectionPool.setJdbcUrl(ConnectionConstants.getConnectionChain());
        oConnectionPool.setUsername(ConnectionConstants.databaseLogin);
        oConnectionPool.setPassword(ConnectionConstants.databasePassword);
        oConnectionPool.setPoolMaxSize(ConnectionConstants.getDatabaseMaxPoolSize);
        oConnectionPool.setPoolInitialSize(ConnectionConstants.getDatabaseMinPoolSize);

        oConnectionPool.setConnectionIdleLimitInSeconds(30);
        oConnectionPool.setTestConnectionQuery("isValid");

        oConnectionPool.setLogQueryExecutionLongerThanMs(500);
        oConnectionPool.setLogStackTraceForLongQueryExecution(true);

        oConnectionPool.setStatementCacheMaxSize(200);

        oConnectionPool.start();

        try {
            oConnection = (Connection) oConnectionPool.getConnection();
            return oConnection;
        } catch (SQLException ex) {
            String msgError = this.getClass().getName() + ":" + (ex.getStackTrace()[1]).getMethodName();
            throw new Exception(msgError, ex);
        }

    }

    /** Cierra la conexión de BBDD
     *
     * @throws Exception
     */
    public void disposeConnection() throws Exception {
        if (oConnection != null) {
            oConnection.close();
        }
        if (oConnectionPool != null) {
            oConnectionPool.close();
        }
    }
}
