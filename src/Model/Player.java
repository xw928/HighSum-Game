package Model;
import java.util.*;

public class Player extends User{

	private int chips;
	protected ArrayList<Card> cardsOnHand;
	
	public Player(String loginName, String password, int chips) {
		super(loginName, password);
		this.chips = chips;
		this.cardsOnHand = new ArrayList<Card>();
	}
	
	public int getChips() {
		return this.chips;
	}
	
	public void addChips(int amount) {
		this.chips+=amount;//no error check
	}
	
	public void deductChips(int amount) {
		this.chips-=amount;//no error check
	}

	public void addCard(Card card) {
		this.cardsOnHand.add(card);
	}
	
	public ArrayList<Card> getCardsOnHand() {
		return this.cardsOnHand;
	}

	public int getTotalCardsValue() {
		int total = 0;
		for(Card card: this.cardsOnHand) {
			total += card.getValue();
		}
		return total;
	}

	public Card getLastCard() {
		return this.cardsOnHand.get(this.cardsOnHand.size()-1);
	}
	
	public void clearCardsOnHand() {
		this.cardsOnHand.clear();
	}
	

}
