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
	
	public static void sendMessage(String type) {
		try {
			switch(type) {
			case "message":
				byte [] msg = ("[" + Main.getData() + "]" + Main.nickname + ": " + Main.gui.inputTextChat.getText()).getBytes(); 
				packet.setData(msg);
				server.send(packet);
				System.out.println("[" + Main.getData() + "]" + Main.nickname + ": " + Main.gui.inputTextChat.getText());
				break;
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
