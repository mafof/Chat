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
	
	
	/**
	 * Клиентская отправка сообщения
	 * @param type - тип отправляемых данных
	 */
	public static void sendMessage(String type) {
		byte [] msg;
		try {
			switch(type) {
			case "message":
				msg = ("msg;"+ Main.nickname + ";" + Main.gui.inputTextChat.getText()).getBytes(); 
				packet.setData(msg);
				server.send(packet);
				break;
			case "privMsg":
				msg = ("privMsg;" + InetAddress.getLocalHost().getHostAddress() + ";" + Main.nickname + ";" + Main.gui.inputTextChat.getText()).getBytes(); 
				packet.setData(msg);
				server.send(packet);
				break;
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
