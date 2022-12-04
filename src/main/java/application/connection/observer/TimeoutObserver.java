package application.connection.observer;

public class TimeoutObserver extends Observer {
    public void updateStatus(String status, String filename) {
        System.out.println("File status: " + status);
    }

    @Override
    public boolean update() {
       System.out.println("Error: Application timed out");
       System.exit(0);
       return false;
    }
}
