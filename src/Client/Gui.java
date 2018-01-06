package Client;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Gui {
	// Frames =>
	public JFrame frame;
	public JFrame frame_setting;

	// Text Area =>
	public JTextArea chatWindow;

	// Text field =>
	public JTextField inputTextChat;
	public JTextField setting_ip;
	public JTextField setting_nickname;

	// Buttons =>
	public JButton bt_send;
	public JButton bt_clear;
	public JButton bt_refresh;
	public JButton bt_setting;
	public JButton bt_setting_save;
	
	private int width = 900;
	private int height = 390;
		
	public Gui() {
		createFrame();
		createTextArea();
		createJTextField();
		createButtons();
		createLabels();
		resetFrame();
	}
	
	private void createFrame() {
		frame = new JFrame("Chat");
		frame.setLayout(null);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		frame_setting = new JFrame("Settings chat");
		frame_setting.setLayout(null);
		frame_setting.setSize(345, 160);
		frame_setting.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private void createTextArea() {
		chatWindow = new JTextArea();
		chatWindow.setEditable(false);
		chatWindow.setBounds(5, 5, 500, 300);
		chatWindow.setVisible(true);
	
		JScrollPane scroll = new JScrollPane(chatWindow, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(5, 5, 500, 300);
		scroll.setVisible(true);
		scroll.setEnabled(true);
		frame.add(scroll);
	}
	
	private void createJTextField() {
		inputTextChat = new JTextField();
		inputTextChat.setBounds(5, 310, 300, 25);
		inputTextChat.setVisible(true);
		
		setting_ip = new JTextField();
		setting_ip.setBounds(125, 5, 200, 25);
		
		setting_nickname = new JTextField();
		setting_nickname.setBounds(125, 35, 200, 25);
		
		frame.add(inputTextChat);
		frame_setting.add(setting_ip);
		frame_setting.add(setting_nickname);
	}
	
	private void createButtons() {
		bt_send = new JButton("Send message");
		bt_send.setBounds(305, 310, 200, 24);

		bt_send.addActionListener(n -> {
			if(!Main.isSetting) { // Проверка на все настройки
				System.out.println("You don't setting application!!!");
				return;
			}
			Main.checkMessageCommand();
			inputTextChat.setText(""); // clear input text field
		});

		bt_clear = new JButton("Clear chat");
		bt_clear.setBounds(510, 310, 115, 24);
		bt_clear.addActionListener(n ->{
			if(!Main.isSetting) {
				System.out.println("You don't setting application!!!");
			} else {
				Main.gui.chatWindow.setText("");
			}
		});
		
		bt_refresh = new JButton("Refresh");
		bt_refresh.setBounds(630, 310, 115, 24);
		bt_refresh.addActionListener(n -> {
			if(!Main.isSetting) {
				System.out.println("You don't setting application!!!");
			}
		});
		
		bt_setting = new JButton("Settings");
		bt_setting.setBounds(750, 310, 115, 24);
		bt_setting.addActionListener(n -> {
			System.out.println("Click->setting");
			frame_setting.setVisible(true);
		});
		
		bt_setting_save = new JButton("Save settings");
		bt_setting_save.setBounds(64, 90, 200, 24);
		bt_setting_save.addActionListener(n -> {
			Main.ip = setting_ip.getText();
			Main.nickname = setting_nickname.getText();
			System.out.println(Main.ip);
			System.out.println(Main.nickname);
			Main.checkSettingsVariable();
			frame_setting.dispose();
		});
		
		frame.add(bt_send);
		frame.add(bt_clear);
		frame.add(bt_refresh);
		frame.add(bt_setting);
		
		frame_setting.add(bt_setting_save);
	}
	
	private void createLabels() {
		JLabel setting_label_ip = new JLabel("Ip server connect");
		setting_label_ip.setBounds(19, 5, 125, 20);
		
		JLabel setting_label_nickname = new JLabel("Nickname");
		setting_label_nickname.setBounds(62, 37, 100, 20);
		
		frame_setting.add(setting_label_ip);
		frame_setting.add(setting_label_nickname);
	}
	
	private void resetFrame() {
		frame.setSize(width+1, height+1); // for fix bug with draw buttons
		frame.setSize(width, height);
	}
}
