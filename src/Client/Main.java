package Client;

import java.text.SimpleDateFormat;
import java.util.Date;

import Client.Gui;
import Client.NetworkThreadReceive;
import Client.NetworkSend;
import Utility.FilesHadler;

/**
 * ����� =>
 * 6688 ��� �������� �������� ��������� 
 * 5588 ��� �������� �� ������ ���������
 * @author admin
 *
 */

public class Main {
	public static Gui gui;
	public static NetworkThreadReceive net;
	public static boolean isSetting = false;
	public static String ip = null;
	public static String nickname = null;
	public static FilesHadler fh = new FilesHadler("settings.txt");
	public static NetworkSend netSend;
	public static Date data = new Date();
	public static SimpleDateFormat format;

	public static void main(String[] args) {
		data = new Date();
		format = new SimpleDateFormat("hh:mm:ss:SS");
		
		gui = new Gui(null);
		net = new NetworkThreadReceive();
		checkSettings();
	}
	
	public static String getData() {
		data = new Date();
		System.out.println(format.format(data));
		return format.format(data);
	}
	
	
	/**
	 * �������� ���������� ���������
	 * ������ ������ ��������� ��������� =>
	 * ���;��������;���.���������
	 */
	public static void checkReceiveMessage() {
		
	}
	
	/**
	 * ������ ini ����� � �����������
	 */
	public static void checkSettings() {
		if(fh.isFiles()) {
			fh.read();
			System.out.println(ip + " " + nickname);
			gui.setting_ip.setText(ip);
			gui.setting_nickname.setText(nickname);
			checkSettingsVariable();
		} else {
			fh.write();
		}
	}
	
	/**
	 * ��������� �� ���������� � ����������
	 */
	public static void checkSettingsVariable() {
		if(ip != null && nickname != null && !ip.isEmpty() && !nickname.isEmpty()) {
			isSetting = true;
			fh.write();
			netSend.setSetting();
		} else {
			isSetting = false;
		}
	}

}
