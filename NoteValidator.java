package cosimo.cosimo;

import java.util.ArrayList;

public class NoteValidator implements Runnable {
	private byte eventNumber;
	private CCTalkConnection noteConnection;
	private final byte ADDRESS;
	private Coin coin;

	public NoteValidator(CCTalkConnection noteConnection, byte address, Coin coin) {
		this.coin = coin;
		this.noteConnection = noteConnection;
		this.ADDRESS = address;
	}

	@Override
	public void run() {
		try {
			this.noteConnection.sendMessage(this.ADDRESS, 1, 228, new byte[] { (byte) 0x01 });
			this.noteConnection.sendMessage(this.ADDRESS, 1, 231, new byte[] { (byte) 0x1f, (byte) 0x00 });
			ArrayList<CCTalkMessage> response = this.noteConnection.sendMessage(this.ADDRESS, 1, 159, null);
			byte[] data = response.get(1).getData();
			eventNumber = data[0];
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (!Thread.currentThread().isInterrupted()) {
			try {
				ArrayList<CCTalkMessage> response = noteConnection.sendMessage(this.ADDRESS, 1, 159, null);
				System.out.println(response);
				byte[] dataResp = response.get(1).getData();
				if (dataResp[0] == 1 && eventNumber != 0) {
					eventNumber = 0;
				}
				switch (dataResp[0] - eventNumber) {
				case 0:
					break;
				case 1: {
					eventNumber++;
					Thread.sleep(100);
					this.handle(dataResp[1], dataResp[2]);
					break;
				}
				default: // TODO it should never happen
				}
				Thread.sleep(400);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void handle(final byte a, final byte b) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				switch (a) {
				case 0:
					System.out.println("errore inserimento");
					break;
				case 1: {
						if(b==1){
						try {
							noteConnection.sendMessage(ADDRESS, 1, 154, new byte[]{(byte) 0x01});
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						coin.addMoney(5);
					}
					break;
				}
				default:
					System.err.println("FATAL ERROR. WHAT HAPPENED?");
				}
			}
		}).start();
	}
}
