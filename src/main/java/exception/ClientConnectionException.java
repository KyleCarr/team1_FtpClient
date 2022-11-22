package exception;

import com.jcraft.jsch.JSchException;

public class ClientConnectionException extends RuntimeException{
    public ClientConnectionException(String message){
        super(message);
    }
    public ClientConnectionException(String message, Throwable throwable){
        super(message, throwable);
    }

    public ClientConnectionException(JSchException e) {
        super(e);
    }
}
