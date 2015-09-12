package nf.co.arcanechicken.fblaproject;

public enum OS {
	WINDOWS(3, 2), MAC(2, 3), LINUX(1, 1);
	
	private int cashPerE;
	private int priceMultiplier;
	
	private OS(int cashPerE, int priceMultiplier){
		this.cashPerE = cashPerE;
		this.priceMultiplier = priceMultiplier;
	}

	public int getCashPerE() {
		return cashPerE;
	}

	public void setCashPerE(int cashPerE) {
		this.cashPerE = cashPerE;
	}

	public int getPriceMultiplier() {
		return priceMultiplier;
	}

	public void setPriceMultiplier(int priceMultiplier) {
		this.priceMultiplier = priceMultiplier;
	}
}
