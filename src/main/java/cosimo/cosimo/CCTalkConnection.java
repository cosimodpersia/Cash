package cosimo.cosimo;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.fazecast.jSerialComm.SerialPort;

public class CCTalkConnection {
	private final SerialPort port;
	private InputStream input;
	private OutputStream output;
	private byte[] buffer;
	
	public CCTalkConnection(String port) {
		this.port = SerialPort.getCommPort(port);
		this.port.openPort();
		this.input = this.port.getInputStream();
		this.output = this.port.getOutputStream();
		this.buffer = new byte[64];
	}
	
	public ArrayList<CCTalkMessage> sendMessage(int destination,int source ,int header, byte[] data) throws Exception{
		if(destination>255 && source>255 && header > 255) 
			throw new Exception("not a byte value. It should be >=0 and <=255");
		ArrayList<CCTalkMessage> response = new ArrayList<>();
		try {
			boolean flag = true;
			while(flag){
				CCTalkMessage message = new CCTalkMessage((byte) destination,(byte) source, (byte)header, data);
				output.write(message.getCommand());
				Thread.sleep(70);
				input.read(buffer);
				response = CCTalkMessage.parseMessages(buffer);
				if (response.size()>=2){
					flag=false;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	public byte[] sendMessagePlain(int destination,int source ,int header, byte[] data) throws Exception{
		if(destination>255 && source>255 && header > 255) 
			throw new Exception("not a byte value. >=0 and <=255");
		try {
			CCTalkMessage message = new CCTalkMessage((byte) destination,(byte) source, (byte)header, data);
			output.write(message.getCommand());
			Thread.sleep(70);
			input.read(buffer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer;
	}
}
