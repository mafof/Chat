package Client;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.IOException;
import java.net.DatagramPacket;

public class NetworkThreadReceive extends Thread {
	private DatagramPacket packet;
	private DatagramSocket client;
	
	@Override
	public void run() {
		try {
			System.out.println(getName());
			client = new DatagramSocket(6688);
			
			byte [] buf = new byte[100000];
			packet = new DatagramPacket(buf, buf.length); 
			
			while(true) {
				System.out.println("ќжидаем пакет");
				client.receive(packet);
				String res = new String(packet.getData(), 0, packet.getLength());
				System.out.println("пакет пришел " + res);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public NetworkThreadReceive() {
		super("NetworkThread");
		start();
	}
}
