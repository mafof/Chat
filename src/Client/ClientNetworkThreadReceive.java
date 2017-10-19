package Client;

import java.net.DatagramSocket;
import java.io.IOException;
import java.net.DatagramPacket;

public class ClientNetworkThreadReceive extends Thread {
	public DatagramPacket packet;
	private DatagramSocket client;
	
	@Override
	public void run() {
		try {
			System.out.println(getName());
			client = new DatagramSocket(6688);
			
			byte [] buf = new byte[100000];
			packet = new DatagramPacket(buf, buf.length); 
			
			while(true) {
				System.out.println("Wait packet");
				client.receive(packet);
				String res = new String(packet.getData(), 0, packet.getLength());
				System.out.println("Packet receive " + res);
				
				Main.checkReceiveMessage(res);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ClientNetworkThreadReceive() {
		super("NetworkThread");
		start();
	}
}
