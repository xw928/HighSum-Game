package View;

import Model.*;
import javax.swing.*;

public class ViewController extends JFrame{
	private GameTableFrame gameFrame;
	private boolean cancelBet = false;
	public ViewController(){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public GameTableFrame getGameFrame() {
		return gameFrame;
	}


	public void startGameFrame(Dealer dealer, Player player){
		gameFrame = new GameTableFrame(dealer, player);
	}

	public void displayChipsOnTable(int num){
		gameFrame.getGamePanel().setChipsOnTable(num);
	}

	public void displayPlayerTotalCardValue(Player player) {
		gameFrame.getGamePanel().displayPlayerTotalCardValue(player.getTotalCardsValue());
	}

	public void displayDealerTotalCardValue(Dealer dealer){
		gameFrame.getGamePanel().displayDealerTotalCardValue(dealer.getTotalCardsValue());
	}

	public void displayHiddenCard(Boolean tf){
		gameFrame.getGamePanel().displayHiddenCard(tf);
	}

	public void displayPlayerBalance(Player player){
		gameFrame.getGamePanel().displayBalance(player.getChips());
	}


	public void displayPlayerNameAndLeftOverChips(Player player) {
		JOptionPane.showMessageDialog(null, player.getLoginName() + ", You are left with " + player.getChips() + " chips", "Chips Reminder ~", JOptionPane.PLAIN_MESSAGE);
	}

	public void displayInsufficientChips(){
		JOptionPane.showMessageDialog(null, "You do not have enough chips to continue game...","Insufficient Amount ~",JOptionPane.PLAIN_MESSAGE);

	}

	public int getDealerCallBetChips() {
		JOptionPane.showMessageDialog(null, "State Bet: 10", "Dealer Call ~", JOptionPane.PLAIN_MESSAGE);
		return 10;
	}

	public void displayEndGame() {
		JOptionPane.showMessageDialog(null, "Thank you for playing HighSum game!", "Game End ~", JOptionPane.PLAIN_MESSAGE);
	}

	public void displayPlayerWin(Player player) {
		JOptionPane.showMessageDialog(null, player.getLoginName()+" Wins!", "Game Result ~", JOptionPane.PLAIN_MESSAGE);
	}

	public void displayDealerWin() {
		JOptionPane.showMessageDialog(null, "Dealer Wins!", "Game Result ~", JOptionPane.PLAIN_MESSAGE);
	}

	public void displayInsufficientChipsDealerWin() {
		JOptionPane.showMessageDialog(null, "Dealer Wins! Due to Insufficient Chips.", "Game Result ~", JOptionPane.PLAIN_MESSAGE);
	}

	public void displayTie() {
		JOptionPane.showMessageDialog(null, "It is a tie!", "Game Result ~", JOptionPane.PLAIN_MESSAGE);
	}

	public void displayPlayerQuit() {
		JOptionPane.showMessageDialog(null, "You have quit the current game. Dealer Win!", "Game Result ~", JOptionPane.PLAIN_MESSAGE);
	}

	public void displayPlayerNameAndChips(Player player) {
		JOptionPane.showMessageDialog(null, player.getLoginName()+", You have "+player.getChips()+" chips.", "Chips Reminder ~", JOptionPane.PLAIN_MESSAGE);
	}

	public boolean cancelStateBet(){
		return cancelBet;
	}


	public char getPlayerCallOrQuit() {
		String[] options = {"Call", "Quit"};
		int option = JOptionPane.showOptionDialog(
				null, "Do you want to Call or Quit?", "Player Call ~",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, options, options[0]
		);
		return option == 0 ? 'c' : 'q';
	}


	public int getPlayerCallBetChip(Player player, int round) {
		boolean validBetAmount = false;
		int chipsToBet = 0;
		String[] option = {"Yes", "No"};
		while (!validBetAmount) {
			String askBet = JOptionPane.showInputDialog(null, "State Your Bet:", "Player Call ~", JOptionPane.PLAIN_MESSAGE);
			if (askBet == null && round == 1) {
				// Cancel button was clicked
				JOptionPane.showMessageDialog(null, "You are not allowed to cancel bet in round one.", "Invalid Action ~", JOptionPane.PLAIN_MESSAGE);
			} else if(askBet == null){
				int confirm = JOptionPane.showOptionDialog(
						null,
						"Are you sure you want to quit the game?",
						"Quit Game ~",
						JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
						null,option,option[0]
				);
				if (confirm == JOptionPane.YES_OPTION) {
					cancelBet = true;
					break;
				}
			}else{
				try {
					chipsToBet = Integer.parseInt(askBet);
					if (chipsToBet < 0) {
						JOptionPane.showMessageDialog(null, "Chips cannot be negative.", "Invalid Amount ~", JOptionPane.PLAIN_MESSAGE);
					} else if (chipsToBet > player.getChips()) {
						JOptionPane.showMessageDialog(null, "You do not have enough chips to bet " + chipsToBet + " chips, Please try again", "Insufficient Amount ~", JOptionPane.PLAIN_MESSAGE);
					} else {
						validBetAmount = true;
					}
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Invalid Input ~", JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
		return chipsToBet;
	}


	public char getPlayerFollowOrNot(Player player, int dealerBet) {
		char r = 'n';
		String[] option = {"Yes", "No"};
		int choice = JOptionPane.showOptionDialog(
				null,
				"Do you want to follow?",
				"Dealer Call ~",
				JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
				null,option,option[0]
		);

		if (choice == JOptionPane.YES_OPTION) {
			r = 'y';
			if (player.getChips() < dealerBet) {
				JOptionPane.showMessageDialog(null, "You do not have enough chips to follow, lost game...","Insufficient Amount ~",JOptionPane.PLAIN_MESSAGE);
				r = 'x'; // return insufficient amount to continue play
			}
		}
		return r;
	}

	public char getPlayerNextGame() {
		char r = 'n';
		String[] option = {"Yes", "No"};
		int choice = JOptionPane.showOptionDialog(
				null,
				"Next game?",
				"HighSum Game",
				JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
				null,option,option[0]
		);

		if (choice == JOptionPane.YES_OPTION) {
			r = 'y';
		}

		return r;
	}





}
