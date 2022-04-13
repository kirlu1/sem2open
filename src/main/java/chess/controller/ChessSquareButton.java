package chess.controller;

import javax.swing.JLabel;

import chess.grid.Coordinate;

import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;

public class ChessSquareButton extends JLabel implements MouseListener{

    public final Coordinate squareCoordinate;
    public final int xPos;
    public final int yPos;
    public Color tileColor;
    final ChessController controller;

    ChessSquareButton(Coordinate squareCoordinate,int yPos,int xPos,Color tileColor,int size,ChessController controller) {
        this.squareCoordinate = squareCoordinate;
        this.xPos = xPos;
        this.yPos = yPos;
        this.tileColor = tileColor;
        this.controller = controller;

        this.setBounds(xPos,yPos,size,size);
        this.setBackground(tileColor);
        this.setOpaque(true);
        this.setFont(new Font("Serif", Font.BOLD, 20));
        if (tileColor.equals(Color.BLACK)) {this.setForeground(Color.WHITE);}
        else {this.setForeground(Color.BLACK);}

        this.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        //System.out.println(getText());
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        //this.setBackground(tileColor);
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        controller.squareClicked(squareCoordinate);
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
    }
    
    public void highlight() {
        Color highlight = Color.ORANGE;
        if (tileColor.equals(Color.WHITE)) {setBackground(highlight);}
        else {setBackground(highlight.darker());}
    }
}
