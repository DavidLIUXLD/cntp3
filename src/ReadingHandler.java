import java.io.*;
import java.net.Socket;
public class ReadingHandler implements Runnable{
    private int portNum;
    private String name;
    private BufferedReader inputReader;
    private Socket socket;

    public ReadingHandler(Socket socket, int portNum, String name) {
        this.socket = socket;
        this.portNum = portNum;
        this.name = name;
    }

    public void run() {
        try {
            Thread.sleep(1000);
            inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(true) {
                String msg;
                while((msg = inputReader.readLine()) != null) {
                    System.out.println(msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
