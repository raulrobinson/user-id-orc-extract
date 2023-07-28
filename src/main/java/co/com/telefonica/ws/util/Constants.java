package co.com.telefonica.ws.util;

public class Constants {
    public static final String RESPONSE_CONTROLLER = "{}(C): to POSTGRES LOAD_DATE={}";
    public static final String SUCCESS_SERVICE = "Success(S) 200: to POSTGRES TOTAL={} PAGE={} LOAD_DATE={}";
    public static final String UNAVAILABLE_SERVICE = "Error(S) 500: to POSTGRES TOTAL={} PAGE={} LOAD_DATE={}";
    public static final String BAD_REQUEST_SERVICE = "Error(S) 400: to POSTGRES TOTAL={} PAGE={} LOAD_DATE={}";
    public static final String RESPONSE_TOTAL = "TOTAL SENT={}";
    private Constants() {}
}
