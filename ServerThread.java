import java.io.*;
import java.net.*;
import java.util.ArrayList;

class ServerThread extends Thread {
	private Socket myDataSocket;
	private Socket otherDataSocket;
	private InputStream is;
	private BufferedReader in;
	private OutputStream os;
	private PrintWriter out;
	private static final String EXIT = "CLOSE";
	private static ArrayList<PrintWriter> printWriters = new ArrayList<PrintWriter>();
	private static ArrayList<MyClass> mySockets = new ArrayList<MyClass>();
	private static Integer socketId;

	public ServerThread(Integer id, Socket socket1) {
		socketId = id;
		myDataSocket = socket1;

		mySockets.add(new MyClass(id, myDataSocket));
	}

	public void run() {
		String inmsg, outmsg;

		System.out.println("[server thread run] socketId: " + socketId);
		try {
			System.out.println("[server thread try] socketId: " + socketId);
			inmsg = mySockets.get(socketId).in.readLine();
			ServerProtocol app = new ServerProtocol();
			outmsg = app.processRequest(inmsg);
			while (!outmsg.equals(EXIT)) {
				System.out.println("[server thread while] socketId: " + socketId);

				for (int i = 0; i < mySockets.size(); i++) {
					if (i != socketId) {
						System.out.println("[server thread while for] i: " + i);
						mySockets.get(i).out.println(outmsg);
					}
				}
				inmsg = mySockets.get(socketId).in.readLine();
				outmsg = app.processRequest(inmsg);
			}

			myDataSocket.close();
			System.out.println("Data socket closed");

		} catch (IOException e) {
			System.out.println("I/O Error " + e);
		}
	}
}
