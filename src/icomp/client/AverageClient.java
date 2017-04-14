package icomp.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class AverageClient extends Client {

	private static final String USAGE = "Usage:\n\t $ java -jar AverageClient <serverIP> <serverPort>";
	private static final int N = 10; // amount of numbers for the average

	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			System.out.println(USAGE);
			return;
		}
		String serverIP = args[0];
		int port = (int) new Integer(args[1]);

		System.out.println("Digite 10 números:");
		double numbers[] = getNumbersFromKeyboard(N);

		System.out.println("Conectando com o servidor ...");
		AverageClient client = new AverageClient(serverIP, port);

		System.out.println("Requisitando a média ...");
		client.requestAverage(numbers);
		
		client.closeConnection();

	}

	public AverageClient(String serverIP, int port) throws IOException {
		super(serverIP, port);
	}

	/*
	 * Local method, read n numbers from the keyboard, and returns a vector
	 * containing the numbers read.
	 */
	public static double[] getNumbersFromKeyboard(int n) {
		Scanner scanner = new Scanner(System.in);

		double numbers[] = new double[n];
		for (int i = 0; i < n; i++) {
			numbers[i] = scanner.nextDouble();
		}

		scanner.close();
		return numbers;
	}

	/*
	 * Sends the number to the server through a socket, and wait the server to
	 * calculate and send back the average of the numbers.
	 */
	public double requestAverage(double numbers[]) throws IOException {
		DataOutputStream out = this.getOutputStream();
		for (double num : numbers) {
			out.writeDouble(num);
		}
		
		DataInputStream in =  this.getInputStream();
		double avg = in.readDouble();
		double desvio = in.readDouble();
		double var = in.readDouble();
		
		System.out.println("A média é: " + avg);
		System.out.println("O desvio é: " + desvio);
		System.out.println("A variância é: " + var);

		System.out.println("Fechando conexão com o servidor ...");

		return avg;
	}
}
