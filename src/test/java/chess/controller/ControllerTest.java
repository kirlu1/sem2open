package chess.controller;

import org.junit.Test;

import chess.view.ChessModel;
import chess.view.ChessView;

public class ControllerTest {
    

    @Test
    public void buttonArrayTest() {
        ChessModel model = new ChessModel();
        ChessView view = new ChessView(model);
        ChessController controller = new ChessController(model,view);
        assert(!(null==controller.buttonArray));
    }
}
