package cosimo.cosimo;

import java.util.ArrayList;
import java.util.Arrays;

public class CCTalkMessage {
	private byte destination; // destination address
	private byte source; // source address
	private byte length; // data length
	private byte header; // header command
	private byte[] data; // data
	private byte checksum; // 0xFF - (sum of all fields % 0xFF) +1
	
	private byte[] cmd; // full command: DA+length+SA+header+(data)+checksum
	
	public CCTalkMessage(byte destination, byte source, byte header, byte[] data) throws Exception
	{
		
		if (data != null) {
			byte length = (byte)data.length;
			if (length != data.length)
				throw new Exception("cct command: specified length does not match data length");
			this.header = header;
			this.length = length;
			this.data = data;
			this.destination=destination;
			this.source=source;
			
			cmd = new byte[5+data.length];
			cmd[0] = destination;
			cmd[1] = length;
			cmd[2] = source;
			cmd[3] = header;
			
			for (int i=0; i<length; ++i)
				cmd[4+i] = data[i];

		}else{
			byte length = 0;
			this.header = header;
			this.length = length;
			this.data = data;
			this.destination=destination;
			this.source=source;
			cmd = new byte[5];
			cmd[0] = destination;
			cmd[1] = length;
			cmd[2] = source;
			cmd[3] = header;
			
			for (int i=0; i<length; ++i)	//TODO controllare qui...
				cmd[4+i] = data[i];
		}
		checksum = (checksum(Arrays.copyOf(cmd, cmd.length - 1)));
		cmd[cmd.length - 1] = checksum;
	}
	
	public byte checksum(byte[] str)
	{
		int sum = 0;
		for (int i=0; i<str.length; ++i)
			sum += (str[i]);
		// NOTE not 0xFF but 0x100
		return (byte)(0x100-sum%0x100);
	}
	

	public byte getLength() {
		return length;
	}

	public byte getHeader() {
		return header;
	}
	public byte getChecksum()
	{
		return checksum;
	}

	public byte[] getData() {
		return data;
	}
	
	public byte[] getCommand() {
		return cmd;
	}
	
	public byte getSA()
	{
		return source;
	}
	public byte getDA()
	{
		return destination;
	}
	
	@Override
	public String toString()
	{
		String str=
			Integer.toString(0xff&destination, 16)+" "+
			Integer.toString(0xff&length, 16)+" "+
			Integer.toString(0xff&source, 16)+" "+
			Integer.toString(0xff&header, 16)+" ";
		
		for (int i=0; i<length; ++i)
			str+=Integer.toString(0xff&data[i], 16)+" ";
		str+=Integer.toString(0xff&checksum, 16);

		return str;
	}
	
	public static ArrayList<CCTalkMessage> parseMessages(byte[] msg) throws Exception{
		ArrayList<CCTalkMessage> res = new ArrayList<CCTalkMessage>();
		byte destination,length,source,command;
		byte[] data = new byte[0];
		for(int i=0;i< msg.length;i++){
			destination = msg[i];
			if(destination==0){
				break;
			}else{
				length = msg[i+1];
				source = msg[i+2];
				command = msg[i+3];
				if(length != 0){
					data = Arrays.copyOfRange(msg, i+4, i+4+length);
					i=i+4+length; //viene incrementato di uno dal for
				}else{
					i=i+4;//viene incrementato di uno dal for
				}
			}
			res.add(new CCTalkMessage(destination, source, command, data));
			data = new byte[0];
		}
		return res;
	}
}
