package icomp.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AverageServer extends Server {

	public static double calculateSampleVariance(double numbers[]) {
		double avg;
		double var = 0;
		int n = numbers.length;
		avg = calculateAverage(numbers);

		for (double num : numbers) {
			var += Math.pow(avg - num, 2.0);
		}

		return var / (n - 1);
	}

	public static double calculateSampleDeviation(double numbers[]) {
		double var = calculateSampleVariance(numbers);

		return Math.sqrt(var);
	}

	public static void main(String[] args) throws IOException {
		int port;
		if (args.length < 1) {
			port = 9999;
		} else {
			port = (int) new Integer(args[0]);
		}
		AverageServer server = new AverageServer(port);
		ServerSocket serverSocket = server.getServerSocket();

		System.out.println("Servidor iniciado. Escutando na porta " + port);
		while (true) {
			Socket socket = serverSocket.accept();
			System.out.println("\nConexão aceita. " + socket);

			serveAverage(socket);
			socket.close();
		}
	}

	public AverageServer(int serverPort) throws IOException {
		super("localhost", serverPort);
	}

	/*
	 * Reads n numbers from a client socket and returns them on a array
	 */
	public static double[] getNumbers(Socket socket, int n) throws IOException {
		double numbers[] = new double[n];

		DataInputStream in = new DataInputStream(socket.getInputStream());
		for (int i = 0; i < n; i++) {
			double num = in.readDouble();
			numbers[i] = num;
		}

		return numbers;
	}

	/*
	 * Sends a previously calculated average to a client socket
	 */
	public static void sendAverage(Socket socket, double avg, double desvio, double var) throws IOException {
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		out.writeDouble(avg);
		out.writeDouble(desvio);
		out.writeDouble(var);
	}

	/*
	 * Reads 10 numbers from a client socket, then calculate the average of the
	 * 10 numbers, then send the average to the client
	 */
	public static void serveAverage(Socket socket) throws IOException {
		int n = 10;
		double numbers[] = getNumbers(socket, n);

		double avg = calculateAverage(numbers);
		double desvio = calculateSampleDeviation(numbers);
		double var = calculateSampleVariance(numbers);

		// this block is for debugging purposes
		System.out.print("\tA média do números: [ ");
		for (double num : numbers) {
			System.out.print(num + ", ");
		}
		System.out.println("]\n\té = " + avg);

		sendAverage(socket, avg, desvio, var);
	}

	public static double calculateAverage(double numbers[]) {
		double avg = 0;
		for (double n : numbers) {
			avg += n;
		}

		avg /= numbers.length;

		return avg;
	}

}
