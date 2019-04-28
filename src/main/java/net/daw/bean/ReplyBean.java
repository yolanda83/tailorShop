package net.daw.bean;

/**
 *
 * @author Yolanda
 */
public class ReplyBean {

    private int status;
    private String json;

    /**
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
     * @return
     */
    public int getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public String getJson() {
        return json;
    }

    /**
     *
     * @param json
     */
    public void setJson(String json) {
        this.json = json;
    }

}
