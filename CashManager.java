package cosimo.cosimo;

import java.io.IOException;

import javafx.scene.control.TextField;

public class CashManager{
	private final String COIN_USB_PATH;
	private final String HOPPER_USB_PATH;
	private final String NOTEVALIDATOR_USB_PATH;
	
	private final int COIN_ADDRESS;
	private final int HOPPER_ADDRESS;
	private final int NOTEVALIDATOR_ADDRESS;
	
	private final CCTalkConnection connectionCoin ;
	private final CCTalkConnection connectionHopper ;
	private final CCTalkConnection connectionNoteValidator ;
	
	private final Hopper hopper;
	private final CoinSelector coinSelector;
	private final NoteValidator noteValidator;
	private Coin coin;
	
	private Thread coinSelectorThread;
	private Thread noteValidatorThread;
	
	private Process process;
	
	public CashManager(String coinPath,String hopperPath,String banknotePath,
			int coinAddress,int hopperAddress,int noteValidatorAddress) {
		this.COIN_USB_PATH=coinPath;
		this.HOPPER_USB_PATH=hopperPath;
		this.NOTEVALIDATOR_USB_PATH=banknotePath;
		
		this.COIN_ADDRESS=coinAddress;
		this.HOPPER_ADDRESS=hopperAddress;
		this.NOTEVALIDATOR_ADDRESS=noteValidatorAddress;
		
		this.coin = new Coin();
		
		if (COIN_USB_PATH.equals(HOPPER_USB_PATH) && HOPPER_USB_PATH.equals(NOTEVALIDATOR_USB_PATH)) {
			this.connectionCoin = new CCTalkConnection(COIN_USB_PATH);
			this.connectionHopper = null;
			this.connectionNoteValidator = null;
			hopper = new Hopper(this.connectionCoin, (byte) HOPPER_ADDRESS);
			coinSelector = new CoinSelector(this.connectionCoin, (byte) COIN_ADDRESS, coin);
			noteValidator = new NoteValidator(connectionCoin, (byte) NOTEVALIDATOR_ADDRESS, coin);
		} else if (COIN_USB_PATH.equals(HOPPER_USB_PATH)) {
			this.connectionCoin = new CCTalkConnection(COIN_USB_PATH);
			this.connectionHopper = null;
			this.connectionNoteValidator = new CCTalkConnection(NOTEVALIDATOR_USB_PATH);
			hopper = new Hopper(this.connectionCoin, (byte) HOPPER_ADDRESS);
			coinSelector = new CoinSelector(this.connectionCoin, (byte) COIN_ADDRESS, coin);
			noteValidator = new NoteValidator(connectionNoteValidator, (byte) NOTEVALIDATOR_ADDRESS, coin);
		} else if (COIN_USB_PATH.equals(NOTEVALIDATOR_USB_PATH)) {
			this.connectionCoin = new CCTalkConnection(COIN_USB_PATH);
			this.connectionHopper = new CCTalkConnection(HOPPER_USB_PATH);
			this.connectionNoteValidator = null;
			hopper = new Hopper(this.connectionHopper, (byte) HOPPER_ADDRESS);
			coinSelector = new CoinSelector(this.connectionCoin, (byte) COIN_ADDRESS, coin);
			noteValidator = new NoteValidator(connectionCoin, (byte) NOTEVALIDATOR_ADDRESS, coin);
		} else if (HOPPER_USB_PATH.equals(NOTEVALIDATOR_USB_PATH)) {
			this.connectionCoin = new CCTalkConnection(COIN_USB_PATH);
			this.connectionHopper = null;
			this.connectionNoteValidator = new CCTalkConnection(NOTEVALIDATOR_USB_PATH);
			hopper = new Hopper(this.connectionNoteValidator, (byte) HOPPER_ADDRESS);
			coinSelector = new CoinSelector(this.connectionCoin, (byte) COIN_ADDRESS, coin);
			noteValidator = new NoteValidator(connectionNoteValidator, (byte) NOTEVALIDATOR_ADDRESS, coin);
		} else {
			this.connectionCoin = new CCTalkConnection(COIN_USB_PATH);
			this.connectionHopper = new CCTalkConnection(HOPPER_USB_PATH);
			this.connectionNoteValidator = new CCTalkConnection(NOTEVALIDATOR_USB_PATH);
			hopper = new Hopper(this.connectionHopper, (byte) HOPPER_ADDRESS);
			coinSelector = new CoinSelector(this.connectionCoin, (byte) COIN_ADDRESS, coin);
			noteValidator = new NoteValidator(connectionNoteValidator, (byte) NOTEVALIDATOR_ADDRESS, coin);
		}
		
	}
	
	public void start(){
//		coinSelectorThread = new Thread(coinSelector);
//		coinSelectorThread.setName("Coin Selector Thread");
//		coinSelectorThread.start();
//		noteValidatorThread = new Thread(noteValidator);
//		noteValidatorThread.setName("note Validator Thread");
//		noteValidatorThread.start();
	}
	
	public void stop(){
//		coinSelectorThread.interrupt();
//		noteValidatorThread.interrupt();
//		try {
//			coinSelectorThread.join();
//			noteValidatorThread.join();
//		uiThread.join();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public void giveMoney(int money){
		hopper.give(money);
	}
	
	public Coin getCoinInstance(){
		return coin;
	}
}
