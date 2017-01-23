package cosimo.cosimo;

import java.util.ArrayList;

public class Hopper {
	private CCTalkConnection connection;
	private final byte ADDRESS;
	
	public Hopper(CCTalkConnection connection,byte hopperAddress) {
		this.connection = connection;
		this.ADDRESS=hopperAddress;
	}
	
	public ArrayList<CCTalkMessage> give(final int coin){
		ArrayList<CCTalkMessage>  response = new ArrayList<>();

		String number = Integer.toBinaryString(coin);
		for (int i = number.length(); i < 17; i++) {
			number = '0' + number;
		}
		String number1 = number.substring(0, 9);
		String number2 = number.substring(9, number.length());
		try {
			response= connection.sendMessage(ADDRESS, 1, 134, new byte[] { (byte) 0x88, (byte) 0xf4,
					(byte) 0x10, Byte.parseByte(number2), Byte.parseByte(number1) });
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}
}
