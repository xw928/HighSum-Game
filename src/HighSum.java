import Controller.GameController;
import Model.*;
import View.*;

public class HighSum {
	private Dealer dealer;
	private Player player;
	private ViewController view;
    private GameController gc;

    public HighSum() {
    	//create all the required objects
    	this.dealer = new Dealer();
        this.player = new Player("IcePeak","password",100);
		this.view = new ViewController();
    	this.gc = new GameController(dealer,player,view);
    }
    
	public static void main(String[] args) {
	    new HighSum();
	}

}
