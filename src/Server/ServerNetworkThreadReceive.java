package Server;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.DatagramPacket;

/**
 * Только для принятия данных
 */
public class ServerNetworkThreadReceive extends Thread{
	
	private DatagramSocket socket;
	private DatagramPacket packet;
	
	@Override
	public void run() {
		try {
			System.out.println("Поток создан");
			socket = new DatagramSocket(5588);
			
			byte [] buf = new byte[100000];
			packet = new DatagramPacket(buf, buf.length);
			
			while(true) {
				System.out.println("Жду пакета");
				socket.receive(packet);
				
				System.out.println("Пакет пришел");
				String res = new String(packet.getData(), 0, packet.getLength());
				System.out.println(res);
				
				MainServer.updateUserList(packet.getAddress());
				MainServer.checkReceiveMessage(res);
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public ServerNetworkThreadReceive() {
		super("NetworkThread");
		start();
	}
}
