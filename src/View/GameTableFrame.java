package View;
import javax.swing.*;
import Model.*;

public class GameTableFrame extends JFrame{
    private GameTablePanel gameTablePanel;
    private Dealer dealer;
    private Player player;
    private int count;

    
    public GameTableFrame(Dealer dealer, Player player) {
        setTitle("HighSum Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        this.dealer = dealer;
        this.player = player;
        gameTablePanel = new GameTablePanel(dealer,player);
        this.count=0;

        add(gameTablePanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public GameTablePanel getGamePanel(){
        return this.gameTablePanel;
    }

    
}
