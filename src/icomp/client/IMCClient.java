package icomp.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class IMCClient extends Client {

	private static final String USAGE = "Usage:\n\t $ java -jar IMCClient <serverIP> <serverPort>";
	private static final String SEP = "\r\n";

	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			System.out.println(USAGE);
			return;
		}
		String serverIP = args[0];
		int port = (int) new Integer(args[1]);
		
		// this message has weight, height and sex, serialized, using the server's
		// protocol
		String requestMessage = IMCClient.readInputsFromKeyBoard();

		System.out.println("Conectando com o servidor ...");
		IMCClient client = new IMCClient(serverIP, port);

		System.out.println("Requisitando o IMC ...");
		String response = client.requestIMC(requestMessage);
		System.out.println(response);
		
		System.out.println("\nFechando conexão com o servidor ...");
		client.closeConnection();
	}

	public IMCClient(String serverIP, int port) throws IOException {
		super(serverIP, port);
	}
	
	/*
	 * Local method, read weight, height and sex from keyboard and serializes the
	 * inputs into a string to be sent to the server. Using the protocol mentioned
	 * in the server
	 */
	public static String readInputsFromKeyBoard() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Digite seu peso em Kg (e.g. 70): ");
		String weight = scanner.nextLine();

		System.out.println("Digite sua altura em m (e.g. 1.7): ");
		String height = scanner.nextLine();
		
		System.out.println("Digite seu sexo (e.g. M | F): ");
		String sex = scanner.nextLine();
		
		scanner.close();
		return IMCClient.serializeRequest(weight, height, sex);
	}
	
	public static String serializeRequest(String weight, String height, String sex) {
		return weight + SEP + height + SEP + sex + SEP + SEP;
	}

	/*
	 * Sends the request with the imc inputs to the server and get the response
	 */
	public String requestIMC(String message) throws IOException {
		DataOutputStream out = this.getOutputStream();
		out.writeChars(message);
		
		String messagesEnd = SEP + SEP; // signal the end of the server's response
		
		String response = "";
		DataInputStream in =  this.getInputStream();
		
		while (!response.endsWith(messagesEnd)) {
			response += in.readChar();
		}
		
		return response;
	}
}
