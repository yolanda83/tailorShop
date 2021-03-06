package net.daw.connection.specificimplementation;

import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;

/**
 *
 * @author Yolanda
 */

public class HikariConnectionSpecificImplementation implements ConnectionInterface {

    private Connection oConnection;
    private HikariDataSource oConnectionPool;

    /** Crea una conexión de BBDD
     *
     * @return Devuelve una conexión de BBDD
     * @throws Exception
     */
    public Connection newConnection() throws Exception {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(ConnectionConstants.getConnectionChain());
        config.setUsername(ConnectionConstants.databaseLogin);
        config.setPassword(ConnectionConstants.databasePassword);
        config.setMaximumPoolSize(ConnectionConstants.getDatabaseMaxPoolSize);
        config.setMinimumIdle(ConnectionConstants.getDatabaseMinPoolSize);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setLeakDetectionThreshold(15000);
        config.setConnectionTestQuery("SELECT 1");
        config.setConnectionTimeout(2000);

        try {
            oConnectionPool = new HikariDataSource(config);
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
