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
	
	public static ArrayList<InetAddress> usersList = new ArrayList<InetAddress>(); // Список подключенных пользователей
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
	
	public static void updateUserList(InetAddress addr) {
		System.out.println(usersList.size());
		if(!usersList.contains(addr)) {
			usersList.add(addr);
			System.out.println("Адресс: " + addr.getHostAddress() + " добавлен в список подключенных адрессов");
		}
	}
	
	public static void checkReceiveMessage(String msg) {
		String [] complMsg = msg.split(";");
		switch(complMsg[0]) {
			case "msg":
				sendMessage(complMsg);
				break;
			case "privMsg":
				sendPrivateMessage(complMsg);
				break;
			case "updUsersList":
//				Реализовать в будущем
				break;
		}
	}
	
	/**
	 * Перебирает список подключенных адрессов и отсылает им данные
	 */
	private static void sendingAllAdressMessage() {
		for(int i=0; i < usersList.size(); i++) {
			packet.setAddress(usersList.get(i));
			try {
				socket.send(packet);
			} catch (IOException e) { e.printStackTrace(); }
		}
	}
	
	
	public static void sendMessage(String [] msg) {
		String complMsg = msg[0] + ";" + msg[1] + ": " + msg[2];
		packet.setData(complMsg.getBytes());
		sendingAllAdressMessage();
	}
	
	public static void sendUsersList(String [] msg) {
		
	}
	
	/**
	 * msg[0] = privMsg
	 * msg[1] = ip
	 * msg[2] = nick
	 * msg[3] = msg
	 * @param msg
	 */
	public static void sendPrivateMessage(String [] msg) {
		String complMsg = msg[0] + ";" + msg[1] + ";" + msg[2] + ": " + msg[3];
		packet.setData(complMsg.getBytes());
		try { 
			packet.setAddress(InetAddress.getByName(msg[1]));
			socket.send(packet);
		} catch (IOException e) { e.printStackTrace(); }
		
	}

}
