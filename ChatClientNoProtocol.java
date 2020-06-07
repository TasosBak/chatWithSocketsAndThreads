import java.net.*;
import java.io.*;

// Spawn a different thread for receiving messages
// Due to multiple threads used, simultaneous communication is now possibl

public class ChatClientNoProtocol {
	private static final int PORT = 1234;
        //private static final InetAddress HOST = InetAddress.getLocalHost();
        private static final String HOST = "localhost";

	public static void main(String args[]) throws IOException
	{
		Socket dataSocket = new Socket(HOST,PORT);
                System.out.println("Connection to " + HOST + " established");

		SendThread send = new SendThread(dataSocket);
		Thread thread = new Thread(send);
		thread.start();
		ReceiveThread receive = new ReceiveThread(dataSocket);
		Thread thread2 = new Thread(receive);
		thread2.start();
	}
}	

class SendThread implements Runnable{

	private Socket dataSocket;
        private BufferedReader user;
        private OutputStream os;
        private PrintWriter out;
	
	public SendThread(Socket soc) throws IOException {
		dataSocket = soc;
                user = new BufferedReader(new InputStreamReader(System.in));
		os = dataSocket.getOutputStream();
		out = new PrintWriter(os,true);
	}
	
	public void run() {
		try{
                        System.out.print("Send message, CLOSE for exit:");
			String outmsg = user.readLine();
			while(!outmsg.equals("CLOSE")) {
				out.println(outmsg);
				System.out.print("Send message:");
				outmsg = user.readLine();
			}	
			out.println(outmsg);
			dataSocket.close();
			
		}catch (IOException e){}
	}
	
}

class ReceiveThread implements Runnable{

	private Socket dataSocket;
        private InputStream is;
        private BufferedReader in;
	
	public ReceiveThread(Socket soc) throws IOException {
		dataSocket = soc;
                is = dataSocket.getInputStream();
		in = new BufferedReader(new InputStreamReader(is));
	}
	
	public void run() {
		try{
			String inmsg = in.readLine();
			while(inmsg != null) {
				System.out.println("Received message: " + inmsg);
				System.out.print("Send a reply:");
				inmsg = in.readLine();
			}
		}catch (IOException e){}	
	}
	
}

