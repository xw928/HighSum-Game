package Controller;

import Model.Dealer;
import Model.Player;
import View.ViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController {
	private Dealer dealer;
	private Player player;
	private ViewController view;
	private int chipsOnTable;
	
	public GameController(Dealer dealer,Player player,ViewController view) {
		this.dealer = dealer;
		this.player = player;
		this.view = view;
		this.chipsOnTable = 0;
		loginPage();
	}


	public void delayRun(){
		displayShuffle();
		Timer afterAnimation = new Timer(2500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.startGameFrame(dealer,player);
				runOneRound();
			}
		});
		afterAnimation.setRepeats(false);
		afterAnimation.start();
	}

	
	public void runOneRound() {
		this.chipsOnTable = 0;
		this.view.displayChipsOnTable(this.chipsOnTable);
		this.view.displayPlayerBalance(player);
		this.view.displayPlayerNameAndChips(this.player);
		this.dealer.shuffleCards();

		boolean playerQuit = false;
		boolean dealerWin = false;

		for(int round = 1;round<=4;round++) {

			if(player.getChips() == 0){
				this.view.displayInsufficientChips();
				dealerWin = true;
				break;
			}

			if(round==1) {  //round 1 deal extra card
				this.dealer.dealCardTo(this.player);
				this.dealer.dealCardTo(this.dealer);
			}

			this.dealer.dealCardTo(this.player);
			this.dealer.dealCardTo(this.dealer);
			this.view.displayPlayerTotalCardValue(player);

			int whoCanCall = this.dealer.determineWhichCardRankHigher(dealer.getLastCard(), player.getLastCard());

			if(whoCanCall == 1) { //dealer call
				if(round == 1) {
					int chipsToBet = view.getDealerCallBetChips();
					this.player.deductChips(chipsToBet);
					this.chipsOnTable += 2 * chipsToBet;

				}else {
					//ask player want to follow?
					char r = this.view.getPlayerFollowOrNot(this.player, 10);
					if (r == 'y') {
						this.player.deductChips(10);
						this.chipsOnTable += 2 * 10;

					} else if (r == 'x'){   // insufficient amount
						dealerWin = true;
						break;

					}else {
						playerQuit = true;
						break;
					}
				}

			}else { //player call
				if(round == 1) { //round 1 player cannot quit
					int chipsToBet = view.getPlayerCallBetChip(this.player, round);
					this.player.deductChips(chipsToBet);
					this.chipsOnTable += 2 * chipsToBet;

				}else {
					char r = this.view.getPlayerCallOrQuit();
					if(r == 'c') {
						int chipsToBet = view.getPlayerCallBetChip(this.player,round);
						if(this.view.cancelStateBet()){
							playerQuit = true;
							break;
						}
						this.player.deductChips(chipsToBet);
						this.chipsOnTable += 2 * chipsToBet;
					}else {
						playerQuit = true;
						break;
					}
				}
			}
			this.view.displayPlayerBalance(player);
			this.view.displayChipsOnTable(this.chipsOnTable);
			this.view.displayPlayerNameAndLeftOverChips(this.player);
		}

		this.view.displayHiddenCard(true);
		this.view.displayDealerTotalCardValue(dealer);

		// check who win
		if(playerQuit) {
			this.view.displayPlayerQuit();

		} else if(dealerWin){
			this.view.displayInsufficientChipsDealerWin();

		}else if(this.player.getTotalCardsValue() < this.dealer.getTotalCardsValue()) {
			this.view.displayDealerWin();

		}else if(this.player.getTotalCardsValue() > this.dealer.getTotalCardsValue()) {
			this.view.displayPlayerWin(this.player);
			this.player.addChips(chipsOnTable);
			this.view.displayPlayerBalance(player);

		}else {
			this.view.displayTie();
			this.player.addChips(chipsOnTable/2);
			this.view.displayPlayerBalance(player);

		}

		if(player.getChips() > 0) {
			char r = this.view.getPlayerNextGame();
			if (r == 'y') {
				this.view.getGameFrame().dispose();
				delayRun();
			} else {
				this.view.displayEndGame();
				System.exit(0);
			}
		}else {
			this.view.displayEndGame();
			System.exit(0);
		}

		// put all the cards back to the deck
		dealer.addCardsBackToDeck(dealer.getCardsOnHand());
		dealer.addCardsBackToDeck(player.getCardsOnHand());
		dealer.clearCardsOnHand();
		player.clearCardsOnHand();

	}


	public void loginPage(){
		// create login frame
		JPanel panel = new JPanel();
		JFrame frame = new JFrame();
		frame.setTitle("Login");
		frame.setSize(350,210);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setAlwaysOnTop(true);

		panel.setLayout(null);

		JLabel userLabel = new JLabel("Username");
		userLabel.setBounds(10,20,80,25);
		panel.add(userLabel);

		JTextField userText = new JTextField(20);
		userText.setBounds(100,20,180,25);
		panel.add(userText);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10,50,80, 25);
		panel.add(passwordLabel);

		JPasswordField passwordText = new JPasswordField();
		passwordText.setBounds(100,50,180,25);
		panel.add(passwordText);

		JLabel message = new JLabel("");
		message.setBounds(10,115,300,25);
		panel.add(message);

		JButton loginButton = new JButton("Login");
		loginButton.setBounds(100,80,180,25);
		loginButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){

				String user = userText.getText();
				String password = String.valueOf(passwordText.getPassword());
				if (user.isEmpty() || password.isEmpty()) {
					message.setText("Please fill in the blank.");
					message.setForeground(Color.RED);
				} else if (user.equals(player.getLoginName()) && player.checkPassword(password)) {
					message.setText("Successful Login!");
					message.setForeground(new Color(0, 160, 0));
					message.setFont(new Font("Arial", Font.PLAIN, 18));

					JButton closeFrameButton = new JButton("OK");
					closeFrameButton.setBounds(200,115,80,25);
					closeFrameButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// When clicking the "OK" button, close login frame
							frame.dispose();
							delayRun();
						}
					});
					panel.add(closeFrameButton);
				} else {
					userText.setText("");
					passwordText.setText("");
					message.setText("Invalid User name or Password.");
					message.setForeground(Color.RED);
				}
			}

		});
		panel.add(loginButton);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}



	public void displayShuffle(){
		JFrame frame = new JFrame();
		frame.setTitle("HighSum Game");
		frame.setSize(1800,1200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel(null);
		panel.setBackground(Color.GREEN);

		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		// add gif
		ImageIcon originalIcon = new ImageIcon("images/shuffleCards.gif");
		int originalWidth = originalIcon.getIconWidth();
		int originalHeight = originalIcon.getIconHeight();
		double scale = 1.6; // Desired scale factor
		int newWidth = (int) (originalWidth * scale);
		int newHeight = (int) (originalHeight * scale);

		Image scaledImage = originalIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
		ImageIcon newIcon = new ImageIcon(scaledImage);
		JLabel GIFLabel = new JLabel(newIcon);
		GIFLabel.setBounds(400,80,1000,500);
		panel.add(GIFLabel);

		// display the shuffling card text animation
		JLabel shuffle = new JLabel("Shuffling deck");
		shuffle.setBounds(790,590,1000,40);
		shuffle.setForeground(Color.black);
		shuffle.setFont(new Font("Arial", Font.BOLD, 30));

		panel.add(shuffle);

		// Start the text animation timer
		Timer animationTimer = new Timer(500, new ActionListener() {
			int dotCount = 1;
			@Override
			public void actionPerformed(ActionEvent e) {
				StringBuilder labelTextBuilder = new StringBuilder("Shuffling deck");
				for (int i = 0; i < dotCount; i++) {
					labelTextBuilder.append('.');
				}
				String labelText = labelTextBuilder.toString();
				shuffle.setText(labelText);

				dotCount++;
				if (dotCount > 6) {
					dotCount = 1;
				}
			}
		});
		animationTimer.start();

		// show back card
		Thread cardThread = new Thread(() ->{
			for(int i = 0; i < 5; i++) {
				ImageIcon backCardIcon = new ImageIcon("images/back.png");
				JLabel backCardLabel = new JLabel(backCardIcon);
				int x = 410 + (i * 210);
				int y = 700;
				backCardLabel.setBounds(x, y, backCardIcon.getIconWidth(), backCardIcon.getIconHeight());
				panel.add(backCardLabel);
				frame.repaint();
				pause();
			}
			frame.dispose();   // close shuffle frame
		});
		cardThread.start();
	}

	private void pause() {
		try{
			Thread.sleep(500);
		}catch(Exception e){
			e.printStackTrace();
		}
	}


}
