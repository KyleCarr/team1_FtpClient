package application.connection.observer;

import application.connection.EstablishedConnection;

public abstract class Observer {
    protected EstablishedConnection connection;
    public abstract boolean update();

}
