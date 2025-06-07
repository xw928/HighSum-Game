package View;
import java.awt.*;
import javax.swing.*;
import Model.*;

public class GameTablePanel extends JPanel {

	private Player player;
	private Dealer dealer;
	private ImageIcon cardBackImage;
	private JLabel chipsOnTableLabel;
	private JLabel totalValuePlayer;
	private JLabel totalValueDealer;
	private JLabel balanceChips;
	private boolean endGame;


	public GameTablePanel(Dealer dealer, Player player) {
		super();
		setBackground(Color.GREEN);
		setPreferredSize(new Dimension(1800, 1200));

		cardBackImage = new ImageIcon("images/back.png");
		this.dealer = dealer;
		this.player = player;
		chipsOnTableLabel = new JLabel();
		totalValuePlayer = new JLabel();
		totalValueDealer = new JLabel();
		balanceChips = new JLabel();
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// create Deck image and text
		int deckX = 190;
		int deckY = 270;
		cardBackImage.paintIcon(this, g, deckX, deckY);
		cardBackImage.paintIcon(this, g, deckX + 30, deckY + 30);

		String deckText = "Deck";
		Font font = g.getFont().deriveFont(Font.BOLD, 25);
		g.setFont(font);
		g.drawString(deckText,250,540);


		// create chips on table image and text
		ImageIcon chipsOnTable = new ImageIcon("images/ChipsOnTable.png");
		int originalWidth = chipsOnTable.getIconWidth();
		int originalHeight = chipsOnTable.getIconHeight();
		double scale = 0.4;
		int newWidth = (int) (originalWidth * scale);
		int newHeight = (int) (originalHeight * scale);

		Image scaledImage = chipsOnTable.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
		ImageIcon newIcon = new ImageIcon(scaledImage);
		newIcon.paintIcon(this, g, 170,660);


		String chipsOnTableText = chipsOnTableLabel.getText();
		g.setFont(g.getFont().deriveFont(Font.BOLD, 22));
		g.drawString(chipsOnTableText, 180, 910);

		String dealerText = "Dealer";
		g.setFont(font);
		g.drawString(dealerText, 560, 420);

		String playerText = "Player: " + player.getLoginName();
		g.setFont(font);
		g.drawString(playerText, 560, 1070);

		String playerTotalValue = totalValuePlayer.getText();
		g.setFont(font);
		g.drawString(playerTotalValue, 1470,1070);


		String balanceText = balanceChips.getText();
		g.setFont(font);
		g.drawString(balanceText,560,1110);



		int cardWidth = 170;
		int cardHeight = 240;
		// paint 4 back card when haven't start game
		Image cardBack = cardBackImage.getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_DEFAULT);
		ImageIcon scaledCardBackIcon = new ImageIcon(cardBack);
		scaledCardBackIcon.paintIcon(this, g, 550, 120);
		scaledCardBackIcon.paintIcon(this, g, 770, 120);
		scaledCardBackIcon.paintIcon(this, g, 550, 780);
		scaledCardBackIcon.paintIcon(this, g, 770, 780);

		int x = 550;
		int y = 120;


		int i = 0;
		for (Card c : dealer.getCardsOnHand()) {
			// display dealer cards
			if (i == 0) {
				scaledCardBackIcon.paintIcon(this, g, x, y);
				i++;

				if(endGame){
					Image cardImage = c.getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_DEFAULT);
					ImageIcon scaledCardIcon = new ImageIcon(cardImage);
					scaledCardIcon.paintIcon(this, g, x, y);

					String dealerTotalValue = totalValueDealer.getText();
					g.setFont(font);
					g.drawString(dealerTotalValue, 1470,420);
				}

			} else {
				Image cardImage = c.getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_DEFAULT);
				ImageIcon scaledCardIcon = new ImageIcon(cardImage);
				scaledCardIcon.paintIcon(this, g, x, y);
			}
			x += cardWidth + 50;
		}

		// display player cards
		x = 550;
		y = 780;

		for (Card c : player.getCardsOnHand()) {
			// display dealer cards
			Image cardImage = c.getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_DEFAULT);
			ImageIcon scaledCardIcon = new ImageIcon(cardImage);
			scaledCardIcon.paintIcon(this, g, x, y);
			x += cardWidth + 50;

		}
	}



	public void setChipsOnTable(int num){
		chipsOnTableLabel.setText("Chips On Table: " + num);
		repaint();
	}

	public void displayPlayerTotalCardValue(int num){
		totalValuePlayer.setText("Value: " + num);
		repaint();
	}

	public void displayDealerTotalCardValue(int num){
		totalValueDealer.setText("Value: " + num);
		repaint();
	}

	public void displayBalance(int num){
		balanceChips.setText("Balance Chips: " + num);
		repaint();
	}

	public void displayHiddenCard(boolean tf){
		endGame = tf;
		repaint();
	}



}
