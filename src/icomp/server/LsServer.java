package icomp.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LsServer extends Server {
	/*
	 * message protocol used in this server and client works as follows:
	 * - end of a message is marked as an empty line: "\r\n\r\n". So for
	 * 		example if the client sends the string "/home/user/\r\n\r\n".
	 * 		when the server reads "\r\n\r\n", it knows the client finished
	 * 		sending the request, and that the directory requested is in fact
	 * 		"/home/user/.
	 * - items of the contents of the directory are separated by lines.
	 * 		so this string "Desktop\r\nDocuments\r\nMusic\r\n\r\n", translates to
	 * 		the array ["Desktop", "Documents", "Music"]
	 */

	public static void main(String[] args) throws IOException {
		int port;
		if (args.length < 1) {
			port = 9999;
		} else {
			port = (int) new Integer(args[0]);
		}

		LsServer server = new LsServer("localhost", port);
		ServerSocket serverSocket = server.getServerSocket();

		System.out.println("Servidor iniciado. Escutando na porta " + port);

		while (true) {
			Socket socket = serverSocket.accept();
			System.out.println("\nConexão aceita. " + socket);

			server.serveLs(socket);
		}
	}

	public LsServer(String serverIP, int serverPort) throws IOException {
		super("localhost", serverPort);
	}

	/*
	 * Reads a directory path from a client socket and returns a string with the path
	 */
	public String getDirectory(Socket socket) throws IOException {
		DataInputStream in = new DataInputStream(socket.getInputStream());

		String directory = "";
		while (!directory.endsWith("\r\n\r\n")) {
			directory += in.readChar();
		}

		return directory.trim();
	}

	/*
	 *  Local method that returns the contents of a directory path, and returns
	 *  an array of strings with its contents.
	 */
	public static String[] listDir(String directory) {
		File file = new File(directory);

		return file.list();
	}

	/*
	 * Reads a directory path from a client socket, list the contents of the directory
	 * and sends the contents back to the client
	 */
	public void serveLs(Socket socket) throws IOException {
		String directory = getDirectory(socket);
		System.out.println("\tCliente requisitou conteúdo da pasta: " + directory);

		String contents[] = listDir(directory);

		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		for (String fileName : contents) {
			out.writeChars(fileName + "\r\n");
		}
		out.writeChars("\r\n");
	}
}
