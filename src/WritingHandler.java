import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class WritingHandler implements Runnable {
    private int portNum;
    private String name;
    private Scanner inputReader;
    private PrintWriter outputWriter;
    private Socket socket;

    public WritingHandler(int portNum, String name, Scanner inputReader) throws IOException {
        this.portNum = portNum;
        this.name = name;
        this.inputReader = inputReader;
        socket = new Socket("127.0.0.1", portNum);
    }

    public void run() {
        try {
            outputWriter = new PrintWriter(socket.getOutputStream(), true);
            while(true) {
                System.out.println();
                if(inputReader.hasNextLine()) {
                    String msg = inputReader.nextLine();
                    if(msg.length() > 0) {
                        outputWriter.println(name + ": " + msg);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
