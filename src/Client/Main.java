package Client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Client.Gui;
import Client.ClientNetworkThreadReceive;
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
	public static ClientNetworkThreadReceive net;
	public static boolean isSetting = false;
	public static String ip = null;
	public static String nickname = null;
	public static FilesHadler fh = new FilesHadler("settings.txt");
	public static NetworkSend netSend;
	public static String chatData = "";
	public static Date date = new Date();
	public static SimpleDateFormat format;

	public static void main(String[] args) {
		date = new Date();
		format = new SimpleDateFormat("hh:mm:ss:SS");
		
		gui = new Gui(null);
		net = new ClientNetworkThreadReceive();
		checkSettings();
		
		/*
		if(checkMessageCommand("!pm 192.168.1.4 ratatyq", "!pm ") != null) {
			System.out.println(true);
		}
		*/
	}
	
	public static String getData() {
		date = new Date();
		return format.format(date);
	}
	
	
	/**
	 * �������� ���������� ���������
	 * ������ ������ ��������� ��������� =>
	 * ���;��������;���.���������
	 */
	public static void checkReceiveMessage(String _msg) {
		String [] msg = _msg.split(";");
		if(msg[0].equals("msg")) {
			updateChatWindow(msg[1]);
		} else if(msg[0].equals("privMsg")) {
			String _tempMsg = "[��������� ��������� �� " + msg[1] + "] " + msg[2];
			updateChatWindow(_tempMsg);
		}
	}
	
	/**
	 * ������ ini ����� � �����������
	 */
	public static void checkSettings() {
		if(fh.isFiles()) {
			fh.read();
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
	
	/**
	 * ���������� ������ ����
	 */
	private static void updateChatWindow(String str) {
		chatData += "[" + getData() + "] " + str + "\n";
		gui.chatWindow.setText(chatData);
	}
	
	/**
	 * RegEx �������� ���������� ��������� �� ������� �������
	 */
	public static String checkMessageCommand(String msg, String reg) {
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(msg);
		if(m.lookingAt()) {
			msg = msg.substring(m.end(), msg.length());
			System.out.println(msg);
			return msg;
		}
		return null;
	}

}
