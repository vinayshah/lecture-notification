package packageJson;

/**
 * Created by Vinay on 7/8/2015.
 */
public class Common {
//    String url_path = "http://lecnotify.techpatel.com/";
    private final String url_path;
    private final String SENDER_ID;
    private final String PUSHBOTS_APPLICATION_ID;

    public Common() {
        this.url_path = "http://lecnotify.techpatel.com/";
        this.SENDER_ID = "244998884036";
        this.PUSHBOTS_APPLICATION_ID = "532ca6b31d0ab1f47c8b45bf";
    }

    public String getUrl_path() {
        return url_path;
    }

    public String getSENDER_ID() {
        return SENDER_ID;
    }

    public String getPUSHBOTS_APPLICATION_ID() {
        return PUSHBOTS_APPLICATION_ID;
    }
}
