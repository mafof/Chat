package Client;

import java.text.SimpleDateFormat;
import java.util.Date;

import Client.Gui;
import Client.NetworkThreadReceive;
import Client.NetworkSend;
import Utility.FilesHadler;

/**
 * порты =>
 * 6688 Для принятие клиентам сообщений 
 * 5588 Для отправки на сервер сообщений
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
	 * Проверка пришедшего сообщения
	 * Формат такого сообщения следующий =>
	 * Тип;значение;доп.параметры
	 */
	public static void checkReceiveMessage() {
		
	}
	
	/**
	 * Чтение ini файла с настройками
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
	 * Заполнены ли переменные в настройках
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
