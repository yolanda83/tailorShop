package net.daw.constant;

/**
 *
 * @author Yolanda
 */
public class ConnectionConstants {

    /**
     *
     */
    public static enum EnumConstans {
        Hikari,
        DBCP,
        BoneCP,
        Vibur
    };

    public static final EnumConstans connectionPool = EnumConstans.Hikari;
    public static final String databaseName = "patchserver";
    public static final String databaseLogin = "root";
    public static final String databasePassword = "bitnami";
    public static final String databasePort = "3307";
    public static final String databaseHost = "localhost";
    public static final int getDatabaseMaxPoolSize = 10;
    public static final int getDatabaseMinPoolSize = 5;

    /**
     *
     * @return
     */
    public static String getConnectionChain() {
        return "jdbc:mysql://" + ConnectionConstants.databaseHost + ":" + ConnectionConstants.databasePort + "/"
                + ConnectionConstants.databaseName;
    }

}
