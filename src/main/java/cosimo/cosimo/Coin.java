package cosimo.cosimo;

public class Coin {
	private Double money;
	
	public Coin() {
		money = new Double(0);
	}

	public double getMoney() {
		synchronized (this.money) {
			return money;
		}
	}

	public void setMoney(double money) {
		synchronized (this.money) {
			this.money = money;
		}
	}
	
	public void addMoney(double money){
		synchronized (this.money) {
			this.money+=money;
		}
	}
	
	public void substractMoney(double money){
		synchronized (this.money) {
			this.money-=money;
		}
	}
}
