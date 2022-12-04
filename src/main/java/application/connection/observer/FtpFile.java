package application.connection.observer;


import java.util.ArrayList;
import java.util.List;

public class FtpFile implements Subject {
    private List<Observer> observerList = new ArrayList<>();
    private String status;
    private String filename;

    public FtpFile(String filename){
        this.filename = filename;
    }

    @Override
    public void registerObserver(Observer o) {
        observerList.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observerList.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observerList){
            observer.updateStatus(status, filename);
        }
    }

    public void updateStatus(String status){
        this.status = status;
        notifyObservers();
    }
}
