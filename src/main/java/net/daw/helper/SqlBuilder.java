package net.daw.helper;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Yolanda
 */
public class SqlBuilder {

    /**
     *
     * @param hmOrder
     * @return
     */
    public static String buildSqlOrder(HashMap<String, String> hmOrder) {
        String strSQLOrder;
        if (hmOrder != null) {
            strSQLOrder = " ORDER BY ";
            for (Map.Entry<String, String> oPar : hmOrder.entrySet()) {
                strSQLOrder += oPar.getKey();
                strSQLOrder += " ";
                strSQLOrder += oPar.getValue();
                strSQLOrder += ",";
            }
            strSQLOrder = strSQLOrder.substring(0, strSQLOrder.length() - 1);
        } else {
            strSQLOrder = "";
        }
        return strSQLOrder;
    }

}
