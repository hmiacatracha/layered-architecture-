package es.udc.ws.app.exceptions;

public class ExpiredEventException extends Exception {
    public ExpiredEventException(String msg) {
        super(msg);
    }
}