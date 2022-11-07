package exception;

public class ClientConnectionException extends RuntimeException{
    public ClientConnectionException(String message){
        super(message);
    }
    public ClientConnectionException(String message, Throwable throwable){
        super(message, throwable);
    }
}
