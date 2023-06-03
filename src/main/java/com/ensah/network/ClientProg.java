package com.ensah.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


/**
 *
 * @author T. BOUDAA
 *
 */
public class ClientProg {


	// initialize socket and input output streams
	private Socket socket = null;
	private int port=5002;

	private ObjectOutputStream out;

	private ObjectInputStream in;



	private static ClientProg instance;

	// constructor to put ip address and port
	private ClientProg(String address) {

		try {

			socket = new Socket(address, port);

			// sends output to the socket
			System.out.println("Connected to server");

		} catch (Exception ex) {
			throw new NetworkException(ex);
		}

	}

	public static ClientProg getConnnection(String pIpAdress) {

		if (instance == null) {
			instance = new ClientProg(pIpAdress);

		}

		return instance;

	}

	public void close() {
		try {
			out.writeObject("end_connection");
			out.flush();
			out.close();
			in.close();
			socket.close();
		} catch (Exception ex) {
			throw new NetworkException(ex);
		}
	}

	public void send(String pMessage) {


		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(pMessage);
			out.flush();

		} catch (Exception ex) {
			throw new NetworkException(ex);
		}
	}

	public Object recieve() {

		Object obj;
		try {
			in = new ObjectInputStream(socket.getInputStream());
			obj = in.readObject();

		} catch (Exception ex) {
			throw new NetworkException(ex);
		}
		return obj;
	}

	/*public static void main(String args[]) {


		Scanner sc = new Scanner(System.in);
		System.out.println("please entrer the network IP Adresse of the server number (for localhost the ip adresse is: 127.0.0.1)");
		String ipAdresse =  sc.next().trim();
		//System.out.println("please entrer the network port number user by the server (Example: 5000)");
		//int port  = sc.nextInt();

		//connect to server
		ClientProg client = ClientProg.getConnnection(ipAdresse);
		Scanner s = new Scanner(System.in);
		while (true) {
			System.out.println("Pleaser write your message:");
			//read message
			String line = s.nextLine();
			//send message
			Object rep = client.send(line);
			//print server response
			System.out.println(rep);

		}

	}*/
}
