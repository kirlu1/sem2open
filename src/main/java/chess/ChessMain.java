package chess;

import chess.controller.ChessController;
import chess.model.GameState;
import chess.view.ChessModel;
import chess.view.ChessView;

public class ChessMain 
{
    public static void main( String[] args ) {
        ChessModel model = new ChessModel();
        ChessView view = new ChessView(model);
        ChessController controller = new ChessController(model,view);
        controller.piecesToLabels();
        view.setVisible(true); //Frame must be set to visible only after every square label has been added.
    }
}