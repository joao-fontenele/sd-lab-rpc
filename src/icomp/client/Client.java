package icomp.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	private Socket socket;
	
	public Client(String serverIP, int port) throws IOException {
		this.socket = new Socket(serverIP, port);
		this.inputStream = new DataInputStream(socket.getInputStream());
		this.outputStream = new DataOutputStream(socket.getOutputStream());
	}
	
	public void closeConnection() throws IOException {
		this.inputStream.close();
		this.outputStream.close();
		this.socket.close();
	}

	public DataInputStream getInputStream() {
		return inputStream;
	}

	public DataOutputStream getOutputStream() {
		return outputStream;
	}

	public Socket getSocket() {
		return socket;
	}

}
