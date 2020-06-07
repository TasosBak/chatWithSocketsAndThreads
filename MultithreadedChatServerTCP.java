import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class MultithreadedChatServerTCP {
	private static final int PORT = 1234;
	private static ArrayList<ServerThread> threads = new ArrayList<ServerThread>();
	private static Integer threadCount = 0;

	public static void main(String args[]) throws IOException {

		ServerSocket connectionSocket = new ServerSocket(PORT);

		while (true) {

			System.out.println("Server is waiting first client in port: " + PORT);
			Socket dataSocket1 = connectionSocket.accept();
			System.out.println("Received request from " + dataSocket1.getInetAddress());
			System.out.println("Server is waiting second client in port: " + PORT);
			Socket dataSocket2 = connectionSocket.accept();
			System.out.println("Received request from " + dataSocket2.getInetAddress());

			threads.add(threadCount, new ServerThread(dataSocket1, dataSocket2));

			threadCount++;
			threads.add(threadCount, new ServerThread(dataSocket2, dataSocket1));

			if (threadCount == 1) {
				System.out.println("enough clients");
				for (ServerThread thread : threads)
					thread.start();
			}

		}
	}
}
