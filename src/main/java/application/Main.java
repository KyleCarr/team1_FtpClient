package application;

import application.handler.AbstractHandler;
import application.handler.UnixHandler;
import application.handler.WindowsHandler;

import java.io.IOException;
import java.util.Scanner;


public class Main {

    public static void main(String Args[]) throws IOException {

                    AbstractHandler handler;
                    if (System.getProperty("os.name").contains("Windows")) {
                        handler = new WindowsHandler();
                    }
                    else {
                        handler = new UnixHandler();
                    }
                    handler.handleInput();


    }
}
