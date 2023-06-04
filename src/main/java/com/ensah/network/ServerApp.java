package com.ensah.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerApp {
	private ServerSocket serverSocket;
	private static Socket clientSocket;
	public static ObjectOutputStream out;
	public static ObjectInputStream in;
	private Scanner scanner;
	private int port=5002;

	public ServerApp() throws Exception {
		serverSocket = new ServerSocket(port);

		System.out.println("Server started");

		clientSocket = serverSocket.accept();
		System.out.println("Client connected!");

	}

	public void sendMessageToClient(String message) throws IOException {
		out = new ObjectOutputStream(clientSocket.getOutputStream());
		out.writeObject(message);
		out.flush();

	}

	public String getMessageFromClient() throws IOException, ClassNotFoundException {
		in = new ObjectInputStream(clientSocket.getInputStream());


		String receivedData = (String) in.readObject();
		System.out.println("Received message: " + receivedData);
		return receivedData;


	}


}
