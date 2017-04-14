package icomp.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	private ServerSocket serverSocket;
	
	public Server(String serverIP, int serverPort) throws IOException {
		this.serverSocket = new ServerSocket(serverPort);
	}
	
	protected void finalize() {
		System.out.println("Fechando o socket do servidor");
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}
}
