package Model;
import java.util.*;

public class Deck {
	private ArrayList<Card> cards;
	public Deck() {
		cards = new ArrayList<Card>();
		String[] suits = { "Diamond", "Club","Heart","Spade",  };
		for (int i = 0; i < suits.length; i++) {
			String suit = suits[i];
			Card card = new Card(suit, "Ace", 1,1+i);
			cards.add(card);
			
			int c = 5;
			for (int n = 2; n <= 10; n++) {
				Card oCard = new Card(suit, "" + n, n,c+i);
				cards.add(oCard);
				c+=4;
			}

			Card jackCard = new Card(suit, "Jack", 10,41+i);
			cards.add(jackCard);

			Card queenCard = new Card(suit, "Queen", 10,45+i);
			cards.add(queenCard);

			Card kingCard = new Card(suit, "King", 10,49+i);
			cards.add(kingCard);
		}
	}
	
	public Card dealCard() {
		return cards.remove(0);
	}


	//add back arraylist of cards
	public void appendCard(ArrayList<Card> cards) {
		for(Card card: cards) {
			this.cards.add(card);
		}
	}

	public void shuffle() {
		Random random = new Random();
		for(int i=0;i<10000;i++) {
			int indexA = random.nextInt(cards.size());
			int indexB = random.nextInt(cards.size());
			Card cardA = cards.get(indexA);
			Card cardB = cards.get(indexB);
			cards.set(indexA, cardB);
			cards.set(indexB, cardA);	
		}
	}


}

