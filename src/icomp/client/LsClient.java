package icomp.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class LsClient extends Client {

	private static final String USAGE = "Usage:\n\t java LsClient <serverIP> <serverPort> <targetDirectory> <targetIP>";
	//private static final String USAGE_OLD = "Usage:\n\t java LsClient <serverIP> <serverPort> <targetDirectory> <targetIP>";

	public LsClient(String serverIP, int port) throws IOException {
		super(serverIP, port);
	}

	public static void main(String args[]) throws IOException {
		if (args.length < 3) {
			System.out.println(USAGE);
		}

		String serverIP = args[0];
		int port = (int) new Integer(args[1]);
		String directory = args[2];

		System.out.println("Conectando com o servidor ...");
		LsClient client = new LsClient(serverIP, port);

		System.out.println("Fazendo requisição ao servidor ...");
		List<String> contents = client.requestLs(directory);

		System.out.println("Conteúdo do diretório " + directory + " da máquina " + serverIP);
		for (String s : contents) {
			System.out.println("\t" + s);
		}
		
		client.closeConnection();
	}
	
	/*
	 * Sends the string containing a directory path to the server
	 */
	private void sendDirectory(String directory) throws IOException {
		DataOutputStream out = this.getOutputStream();
		out.writeChars(directory + "\r\n\r\n");
	}

	
	/*
	 * Reads the response from the server with the contents of the directory path,
	 * and adds each item of the contents to a list to be returned from the method.
	 */
	private List<String> requestLs(String directory) throws IOException {
		sendDirectory(directory);

		DataInputStream in = this.getInputStream();

		List<String> contents = new LinkedList<>();

		String content = "";

		boolean possibleNewString = false;
		while (!content.endsWith("\r\n\r\n")) {
			char charRead = in.readChar();
			if (possibleNewString) {
				if (charRead != '\r') {
					contents.add(content.trim()); // trim removes trailing whitespaces
					content = "" + charRead;
					possibleNewString = false;
				}
				else {
					break;
				}
			} else {
				content += charRead;
			}
			if (charRead == '\n') {
				possibleNewString = true;
			}
		}
		contents.add(content.trim()); // remove trailing whitespaces
		return contents;
	}

}
