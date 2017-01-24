package cosimo.cosimo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class App 
{
	private final static String PROPERTIES_FILE = "config.properties";
	private static CashManager cashManager;
	
    public static void main( String[] args ){
    	Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(PROPERTIES_FILE));
			startCash(properties);
			// Wait for user pressing 'q' (if STDIN available) 
			while (true) {
				System.out.println("madonna di cri	");
				while (System.in.available() > 0) {
					final char ch = (char) System.in.read();
					if (ch == 'q' || ch == 'Q') {
						stopCash();
						System.exit(0);
					}
				}
				Thread.sleep(2000);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final Throwable ex) {
			// Ignore
		}
    }
    
    
    public static void startCash(Properties properties){
		 cashManager = new CashManager(
				properties.getProperty("pathCoinSelector"), 
				properties.getProperty("pathHopper"), 
				properties.getProperty("pathNoteValidator"),
				Integer.parseInt(properties.getProperty("addressCoinSelector")),
				Integer.parseInt(properties.getProperty("addressHopper")),
				Integer.parseInt(properties.getProperty("addressNoteValidator")));
		cashManager.start();
    }
    
    public static void stopCash(){
    	cashManager.stop();
    }
    
    public static void sendCommands(String port){
    	Scanner keyboard = new Scanner(System.in);
    	CCTalkConnection connection = new CCTalkConnection(port);
		while(true){
			System.out.print("enter command: ");
			byte command =  (byte) keyboard.nextInt();keyboard.nextLine();
			System.out.print("enter data?[y/n]: ");
			String yn =  keyboard.nextLine();
			byte[] da ;
			if(yn.equals("y")){
				System.out.print("enter data: ");
				String temp = keyboard.nextLine();
				String[] data = temp.split(" ");
				int[] dataI = new int[data.length];
				for (int i = 0; i < dataI.length; i++) {
					dataI[i] = Integer.parseInt(data[i]);
				}
				da=new byte[data.length];
				for (int i = 0; i < data.length; i++) {
					da[i] = (byte) dataI[i];
				}
			}else{
				da= null;
			}
			try {
				System.out.println(connection.sendMessage(3, 1, command, da));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
}
