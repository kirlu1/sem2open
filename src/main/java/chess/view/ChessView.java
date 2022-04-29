package chess.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

import chess.model.GameState;
import chess.model.PlayerColor;

public class ChessView extends JFrame {
    
    ChessModel model;
    JLabel overlay;

    public ChessView(ChessModel model) {
        this.model = model;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(model.windowDimension,model.windowDimension);
        this.setLayout(null);

        this.overlay = new JLabel();
        overlay.setBounds(0,0,model.windowDimension,model.windowDimension);
        overlay.setBackground(new Color(0,0,0,180));
        overlay.setFont(new Font("Serif", Font.BOLD, 100));
        overlay.setForeground(Color.WHITE);
        add(overlay);
    }

    public void endScreen() {
        PlayerColor winner;
        String message;
        if (model.state.equals(GameState.STALEMATE)) {
            message = "STALEMATE";
        }

        if (model.state.equals(GameState.WHITE_CHECKED)) {
            winner=PlayerColor.BLACK;
            message = winner+" HAS WON";}
        else {
            winner=PlayerColor.WHITE;
            message = winner+" HAS WON";}
        overlay.setOpaque(true);
        
        this.overlay.setText(message);
        repaint();
    }


    
}
