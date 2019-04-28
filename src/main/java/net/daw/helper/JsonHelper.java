package net.daw.helper;

/**
 *
 * @author Yolanda
 */
public class JsonHelper {

    /**
     *
     * @param status
     * @param msg
     * @return
     */
    public String strJson(int status, String msg) {
        String strJson = "";
        if (status == 200) {
            strJson = "{\"status\":" + status + ",\"message\":" + msg + "}";
        } else {
            strJson = "{\"status\":" + status + ",\"message\":\"" + msg + "\"}";
        }
        return strJson;
    }

}
