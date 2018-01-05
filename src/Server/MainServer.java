package Server;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.net.DatagramSocket;
import java.io.IOException;
import java.net.DatagramPacket;

import Server.ServerNetworkThreadReceive;

public class MainServer {
	
	public static ArrayList<InetAddress> usersList = new ArrayList<>(); // List users
	private static DatagramSocket socket;
	private static DatagramPacket packet;

	public static void main(String[] args) {
		try {
			byte [] buf = new byte[100000];
			
			socket = new DatagramSocket();
			packet = new DatagramPacket(buf, buf.length, InetAddress.getByName("localhost"), 6688);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		new ServerNetworkThreadReceive();
	}

	/**
	 * Adding ip user for users list
	 * @param addr - address user
	 */
	public static void updateUserList(InetAddress addr) {
		System.out.println(usersList.size());
		if(!usersList.contains(addr)) {
			usersList.add(addr);
			System.out.println("user ip: " + addr.getHostAddress() + " adding to users list");
		}
	}

	/**
	 * identified the type of message
	 * @param msg - message from user
	 */
	public static void checkReceiveMessage(String msg) {
		String [] complMsg = msg.split(";");
		switch(complMsg[0]) {
			case "msg": // Public message
				sendMessage(complMsg);
				break;
			case "privMsg": // Private message
				sendPrivateMessage(complMsg[0], complMsg[1], complMsg[3], complMsg[4]);
				break;
			case "updUsersList": // Check users list
				// body...
				break;
		}
	}

	/**
	 * Send public message
	 * @param msg - message from user
	 */
	public static void sendMessage(String [] msg) {
		String complMsg = msg[0] + ";" + msg[1] + ": " + msg[2];
		packet.setData(complMsg.getBytes());
		sendingAllAdressMessage();
	}

	/**
	 * Send private message to ip
	 * msg[0] = privMsg
	 * msg[1] = ip
	 * msg[2] = nick
	 * msg[3] = msg
	 * @param msg - user message
	 */
	public static void sendPrivateMessage(String ... msg) {
		String complMsg = msg[0] + ";" + msg[1] + ";" + msg[2] + ": " + msg[3];
		packet.setData(complMsg.getBytes());
		try {
			packet.setAddress(InetAddress.getByName(msg[1]));
			socket.send(packet);
		} catch (IOException e) { e.printStackTrace(); }

	}

	private static void sendingAllAdressMessage() {
		for(int i=0; i < usersList.size(); i++) {
			packet.setAddress(usersList.get(i));
			try {
				socket.send(packet);
			} catch (IOException e) { e.printStackTrace(); }
		}
	}

}
