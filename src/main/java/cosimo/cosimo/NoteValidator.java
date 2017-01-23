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
				byte[] dataResp = response.get(1).getData();
				if (dataResp[0] == 1 && eventNumber != 0) {
					eventNumber = 0;
				}
				switch (dataResp[0] - eventNumber) {
				case 0:
					break;
				case 1: {
					eventNumber++;
					this.handle(dataResp[1], dataResp[2]);
					break;
				}
				case 2: {
					eventNumber += 2;
					this.handle(dataResp[1], dataResp[2]);
					this.handle(dataResp[3], dataResp[4]);
					break;
				}
				case 3: {
					eventNumber += 3;
					this.handle(dataResp[1], dataResp[2]);
					this.handle(dataResp[3], dataResp[4]);
					this.handle(dataResp[5], dataResp[6]);
					break;
				}
				case 4: {
					eventNumber += 4;
					this.handle(dataResp[1], dataResp[2]);
					this.handle(dataResp[3], dataResp[4]);
					this.handle(dataResp[5], dataResp[6]);
					this.handle(dataResp[7], dataResp[8]);
					break;
				}
				case 5: {
					eventNumber += 5;
					this.handle(dataResp[1], dataResp[2]);
					this.handle(dataResp[3], dataResp[4]);
					this.handle(dataResp[5], dataResp[6]);
					this.handle(dataResp[7], dataResp[8]);
					this.handle(dataResp[9], dataResp[10]);
					break;
				}
				default: // TODO error
				}
				Thread.sleep(300);
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
					coin.addMoney(5);
					break;
				}
				case 2: {
					coin.addMoney(10);
					break;
				}
				case 3: {
					coin.addMoney(20);
					break;
				}
				case 4: {
					coin.addMoney(50);
					break;
				}
				case 5: {
					coin.addMoney(100);
					break;
				}
				default:
					System.err.println("bho");
				}
			}
		}).start();
	}
}
