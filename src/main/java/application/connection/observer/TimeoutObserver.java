package application.connection.observer;

public class TimeoutObserver extends Observer {
    @Override
    public boolean update() {
       System.out.println("Error: Application timed out");
       System.exit(0);
       return false;
    }
}
