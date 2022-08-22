package cz.zcu.fav.kiv.ups.sp.client.net.dto;

/**
 * @author Hana Hrkalova on 15.11.2021
 * @version 0.0.0
 * @project ups
 */
public class RequestMessage {

    private RequestPrefix request;
    private String message;

    public RequestPrefix getRequest() {
        return request;
    }

    public void setRequest(RequestPrefix request) {
        this.request = request;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RequestMessage{" +
                "request=" + request +
                ", message='" + message + '\'' +
                '}';
    }
}
