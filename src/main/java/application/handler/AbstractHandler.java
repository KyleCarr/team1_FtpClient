package application.handler;

import application.connection.EstablishedConnection;

import java.io.IOException;
import java.util.Scanner;

public abstract class AbstractHandler {
    Scanner input = new Scanner(System.in);
    EstablishedConnection connection;

    String remoteHost = "<replace>";
    String username = "<replace>";
    String password = "<replace>";
    public abstract void handleInput() throws IOException;

}
