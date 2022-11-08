package application;

import exception.ClientConnectionException;

public interface ConnectionFactory {
    int CHANNEL_TIMEOUT = 5000;

    EstablishedConnection connect(String remoteHost, String username, String password) throws ClientConnectionException;
}
