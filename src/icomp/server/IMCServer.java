package icomp.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import icomp.server.imc.IMCInputs;
import icomp.server.imc.IMCResults;

public class IMCServer extends Server {
	/*
	 * This server uses a protocol for receiving requests: it receives 3 arguments
	 * separated by "\r\n", and the end of the arguments must be signaled with
	 * "\r\n\r\n"
	 *
	 * The arguments must follow the order: weight, height and sex. As of now
	 * the sex is not used
	 */

	private static final String SEP = "\r\n";
	private static final String USAGE = "java icomp.server.AverageServer [port]";

	public static void main(String[] args) throws IOException {
		int port;
		if (args.length < 1) {
			port = 9999;
		} else {
			port = (int) new Integer(args[0]);
		}

		IMCServer server = new IMCServer(port);
		ServerSocket serverSocket = server.getServerSocket();

		System.out.println("Servidor iniciado. Escutando na porta " + port);
		while (true) {
			Socket socket = serverSocket.accept();
			System.out.println("\nConexão aceita. " + socket);

			try {
				serveIMC(socket);
			} finally {
				socket.close();
			}
		}
	}

	public IMCServer(int serverPort) throws IOException {
		super("localhost", serverPort);
	}

	/*
	 * Reads weight, height and sex from the socket
	 */
	public static IMCInputs getIMCInputs(Socket socket) throws IOException, IndexOutOfBoundsException {
		DataInputStream in = new DataInputStream(socket.getInputStream());

		String messagesEnd = SEP + SEP; // signal the end of the clients's request

		String request = "";
		while (!request.endsWith(messagesEnd)) {
			request += in.readChar();
		}

		String[] split = request.split(SEP);

		double weight = (double) new Double(split[0]);
		double height = (double) new Double(split[1]);
		String sex = split[2];

		return new IMCInputs(weight, height, sex);
	}

	/*
	 * Sends the formatted response back to the client
	 */
	public static void sendResults(Socket socket, String response) throws IOException {
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		out.writeChars(response + SEP + SEP);
	}

	/*
	 * reads the inputs needed to calculate the imc, then compute the report
	 * and send back to the client
	 */
	public static void serveIMC(Socket socket) throws IOException {
		IMCInputs inputs = IMCServer.getIMCInputs(socket);

		String results = IMCResults.getIMCDiagnostic(
				inputs.getWeight(),
				inputs.getHeight(),
				inputs.getSex()
		);

		sendResults(socket, results);
	}
}
