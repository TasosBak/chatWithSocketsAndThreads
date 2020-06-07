import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class MultithreadedChatServerTCP {
	private static final int PORT = 1234;
	public static ArrayList<ServerThread> threads = new ArrayList<ServerThread>();
	public static ArrayList<Socket> dataSockets = new ArrayList<Socket>();
	private static Integer socketCount = 0;
	private static Integer threadCount = 0;

	public static void main(String args[]) throws IOException {

		ServerSocket connectionSocket = new ServerSocket(PORT);

		while (true) {

			System.out.println("Server is waiting first client in port: " + PORT);
			// Socket dataSocket1 = connectionSocket.accept();
			// System.out.println("Received request from " + dataSocket1.getInetAddress());
			dataSockets.add(socketCount, connectionSocket.accept());
			System.out.println("Received request from " + dataSockets.get(0).getInetAddress());
			socketCount++;

			System.out.println("Server is waiting second client in port: " + PORT);

			// Socket dataSocket2 = connectionSocket.accept();

			dataSockets.add(socketCount, connectionSocket.accept());
			System.out.println("Received request from " + dataSockets.get(1).getInetAddress());
			socketCount++;

			dataSockets.add(socketCount, connectionSocket.accept());
			System.out.println("Received request from " + dataSockets.get(2).getInetAddress());

			threads.add(threadCount, new ServerThread(threadCount, dataSockets.get(0)));

			threadCount++;
			threads.add(threadCount, new ServerThread(threadCount, dataSockets.get(1)));

			threadCount++;
			threads.add(threadCount, new ServerThread(threadCount, dataSockets.get(2)));

			if (threadCount == 2) {
				System.out.println("enough clients");
				for (ServerThread thread : threads)
					thread.start();
			}

		}
	}
}
