package net.daw.factory;

import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.connection.specificimplementation.DBCPConnectionSpecificImplementation;
import net.daw.connection.specificimplementation.BoneCPConnectionSpecificImplementation;
import net.daw.connection.specificimplementation.HikariConnectionSpecificImplementation;
import net.daw.connection.specificimplementation.ViburConnectionSpecificImplementation;
import net.daw.constant.ConnectionConstants;

/**
 *
 * @author Yolanda
 */
public class ConnectionFactory {

    /**
     * Método GET CONNECTION
     *
     * @param enumConnection
     * @return Devuelve un ConnectionInterface con uno de los 4 pools de
     * conexiones establecidos
     */
    public static ConnectionInterface getConnection(ConnectionConstants.EnumConstans enumConnection) {
        ConnectionInterface oConnectionInterface = null;
        switch (enumConnection) {
            case Hikari:
                oConnectionInterface = new HikariConnectionSpecificImplementation();
                break;
            case DBCP:
                oConnectionInterface = new DBCPConnectionSpecificImplementation();
                break;
            case BoneCP:
                oConnectionInterface = new BoneCPConnectionSpecificImplementation();
                break;
            case Vibur:
                oConnectionInterface = new ViburConnectionSpecificImplementation();
                break;
            default:
                oConnectionInterface = new HikariConnectionSpecificImplementation();
                break;
        }
        return oConnectionInterface;

    }
}
