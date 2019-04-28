package net.daw.bean;

/**
 *
 * @author Yolanda
 */
public class ReplyBean {

    private int status;
    private String json;

    /** Constructor
     *
     * @param status
     * @param json
     */
    public ReplyBean(int status, String json) {
        super();
        this.status = status;
        this.json = json;
    }

    /**
     *
     * @return Devuelve el valor de Status
     */
    public int getStatus() {
        return status;
    }

    /**
     *
     * @param status Asigna un valor a Status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     *
     * @return Devuelve el valor del JSON
     */
    public String getJson() {
        return json;
    }

    /**
     *
     * @param json Asigna un valor al JSON
     */
    public void setJson(String json) {
        this.json = json;
    }

}
