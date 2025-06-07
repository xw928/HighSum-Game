package Model;
import javax.swing.*;


public class Card extends ImageIcon{

	private String suit;
	private String name;
	private int value;
	private int rank;
	
	public Card(String suit, String name, int value,int rank) {
		super("images/" + suit + name + ".png");
		this.suit = suit;
		this.name = name;
		this.value = value;
		this.rank = rank;
	}

	public String getSuit() {
		return this.suit;
	}

	public String getName() {
		return this.name;
	}

	public int getValue() {
		return this.value;
	}
	
	public int getRank() {
		return this.rank;
	}
	
	
	public String toString() {
		return "<"+this.suit+" "+this.name+">";
	}
	
	public String display() {
		return "<"+this.suit+" "+this.name+" "+this.rank+">";
	}


}

