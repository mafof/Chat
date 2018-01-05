package Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import Client.Main;

/**
 * Class for jobs with Files
 */

public class FilesHadler {
	
	private static File file;
	
	public FilesHadler(String namePath) {
		file = new File(namePath);
	}
	
	public boolean isFiles() {
		if(file.exists())
			return true;
		else
			return false;
	}
	
	public static void write() {	
		try {
			if(!file.exists())
				file.createNewFile();
			
			if(Main.isSetting) {
				FileOutputStream fs = new FileOutputStream(file);
				String strWrite = Main.ip + ";" + Main.nickname;
				byte[] buffer = strWrite.getBytes();
				
				fs.write(buffer, 0, buffer.length);	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void read() {
		try {
			String str = "";
			FileInputStream fs = new FileInputStream(file);
			byte [] buff = new byte[fs.available()];
			fs.read(buff, 0, fs.available());
			
			for(int i=0; i < buff.length; i++)
				str += (char) buff[i];
			
			String [] strCompl = str.split(";");
			
			Main.ip = strCompl[0];
			Main.nickname = strCompl[1];
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
