package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class NetworkSend {
	public static DatagramPacket packet;
	public static DatagramSocket server;
	public static InetAddress addr;
	
	public static void setSetting() {
		try {
			addr = InetAddress.getByName(Main.ip);	
			server = new DatagramSocket();
			
			byte [] data = new byte[100000];
			packet = new DatagramPacket(data, data.length, addr, 5588);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public static void sendMessage(String type, String ... message) {
		byte [] msg;
		try {
			switch(type) {
				case "message": // simple message
					msg = ("msg;"+ Main.nickname + ";" + message[0]).getBytes();
					packet.setData(msg);
					server.send(packet);
				break;
				case "privMsg": // Private message
					msg = (
						"privMsg;" +
						message[0] + ";" + // ip to user
						InetAddress.getLocalHost().getHostAddress() + ";" +  // ip from user
						Main.nickname + ";" +
						message[1]
					).getBytes();

					packet.setData(msg);
					server.send(packet);
				break;
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
