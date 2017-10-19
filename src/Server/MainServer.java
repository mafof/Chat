package Server;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.net.DatagramSocket;
import java.io.IOException;
import java.net.DatagramPacket;

import Server.ServerNetworkThreadReceive;

public class MainServer {
	
	public static ArrayList<InetAddress> usersList = new ArrayList<InetAddress>(); // ������ ������������ �������������
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
			System.out.println("������: " + addr.getHostAddress() + " �������� � ������ ������������ ��������");
		}
	}
	
	public static void checkReceiveMessage(String msg) {
		String [] complMsg = msg.split(";");
		switch(complMsg[0]) {
			case "msg":
				sendMessage(complMsg);
				break;
			case "privMsg":
				
				break;
			case "updUsersList":
//				����������� � �������
				break;
		}
	}
	
	/**
	 * ���������� ������ ������������ �������� � �������� �� ������
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
	
	public static void sendPrivateMessage(String [] msg) {
		
	}

}
