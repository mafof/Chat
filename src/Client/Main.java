package Client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Utility.FilesHadler;

/**
 * Ports =>
 * 6688 - Client part
 * 5588 - Server part
 * @author mafof
 */

public class Main {
	// Declaring variables for next initialization =>
	public static Gui gui;
	public static ClientNetworkThreadReceive net;
	public static NetworkSend netSend;
	public static FilesHadler fh = new FilesHadler("settings.txt");

	// Date =>
	public static Date date = new Date();
	public static SimpleDateFormat format;

	// Settings =>
	public static String chatData = ""; // Data
	public static boolean isSetting = false;
	public static String ip = null;
	public static String nickname = null;

	public static void main(String[] args) {
		// settings date time =>
		date = new Date();
		format = new SimpleDateFormat("hh:mm:ss");

		// init classes =>
		gui = new Gui(); // create gui
		net = new ClientNetworkThreadReceive(); // create Network chart
		checkSettings();
	}

	/**
	 * Get date in need format
	 * @return current date with need format
	 */
	public static String getDate() {
		date = new Date();
		return format.format(date);
	}

	/**
	 * Check coming from the server message
	 * @param _msg - user message from server
	 */
	public static void checkReceiveMessage(String _msg) {
		String [] msg = _msg.split(";");
		switch (msg[0]) {
			case "msg":
                System.out.println("[DEBUG]"+"come message " + msg[1]);
				updateChatWindow(msg[1]);
				break;
			case "privMsg":
                System.out.println("[DEBUG]"+"come private message from "+msg[1]+" text: "+msg[2]);
				String _tempMsg = "[Private message from " + msg[1] + "] " + msg[2];
				updateChatWindow(_tempMsg);
				break;
			default:
				System.out.println("unknown type data");
				break;
		}
	}
	
	/**
	 * Check data through ini files
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
	 * Check data variables properties
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
	 * Adding message with server
	 * @param str - message
	 */
	private static void updateChatWindow(String str) {
		chatData += "[" + getDate() + "] " + str + "\n";
		gui.chatWindow.setText(chatData);
	}
	
	/**
	 * Check command on whitespace
	 */
	public static void checkMessageCommand() {
		String msg = gui.inputTextChat.getText();
		if(msg == null || msg.isEmpty()) return; // <= may be catching NPE [!]

		String[] command;
		Matcher m = Pattern.compile("![a-zA-Z]").matcher(msg);

		if(!m.find()) {
			netSend.sendMessage("message", msg);
			return;
		}

		command = msg.substring(1, msg.length()).split(" ");
		switch (command[0]) {
			case "pm": // Private message
				if(Pattern.compile("\\d{3}.\\d{3}.\\d{1,3}.\\d{1,3}").matcher(command[1]).matches()) {
                    netSend.sendMessage("privMsg", command[1], command[2]);
                    updateChatWindow("[private message to " + command[1] + "] " + command[2]);
                } else { updateChatWindow("Введите команду правильно!"); }
				break;
			default:
				System.out.println("Command not found");
				break;
		}
	}

}
