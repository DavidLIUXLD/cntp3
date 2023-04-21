import java.net.*;
import java.io.*;
import java.util.Scanner;



public class Client {
    private int portNum;
    private final static String server = "127.0.0.1";
    private Scanner inputReader;
    private String clientName;
    private ServerSocket listener;
    private Thread read;
    private Thread write;
    private boolean isConnected;
    private boolean isListening;

    public Client(String clientName) throws IOException {
        this.clientName = clientName;
        isConnected = false;
        inputReader = new Scanner(System.in);
    }

    public void run() throws IOException {
        listener = new ServerSocket(0);
        portNum = listener.getLocalPort();
        System.out.println(clientName + " is running");
        System.out.println("Port number is " + portNum);
        System.out.print("please enter the requested port number: ");
        while(true) {
            int requestedPort;
            WritingHandler wHandler = null;
            ReadingHandler rHandler = null;
            if(inputReader.hasNextInt()) {
                requestedPort = inputReader.nextInt();
                System.out.println("connecting to port: " + requestedPort);
                wHandler = new WritingHandler(requestedPort, clientName, inputReader);
                System.out.println("connection success");
                isConnected = true;
            }
            try {
                Thread.sleep(1000);
                Socket connection = listener.accept();
                System.out.println("Listening from port: " + connection.getLocalPort());
                rHandler = new ReadingHandler(connection, portNum, clientName);
                System.out.println("Listener established");
                isListening = true;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(isConnected && isListening) {
                write = new Thread(wHandler);
                //read = new Thread(rHandler);
                write.start();
                rHandler.run();
            }
        }
    }
}

