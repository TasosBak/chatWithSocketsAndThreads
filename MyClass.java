import java.io.*;
import java.net.*;

public class MyClass {

    private Socket myDataSocket;
    private InputStream is;
    public BufferedReader in;
    private OutputStream os;
    public PrintWriter out;
    public Integer socketId;

    public MyClass(Integer id, Socket socket) {
        myDataSocket = socket;
        socketId = id;
        try {
            System.out.println("[my class constructor] socketId: " + socketId);
            is = myDataSocket.getInputStream();
            in = new BufferedReader(new InputStreamReader(is));
            os = myDataSocket.getOutputStream();
            out = new PrintWriter(os, true);
        } catch (IOException e) {
            System.out.println("I/O Error " + e);
        }
    }

    public BufferedReader getBR() {
        return in;
    }

    public PrintWriter getPW() {
        return out;
    }
}
