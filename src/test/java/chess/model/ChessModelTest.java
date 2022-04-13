package chess.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import chess.grid.Coordinate;
import chess.view.ChessModel;

public class ChessModelTest {
    

    @Test
    public void defendingFromTest() {
        ChessModel model = new ChessModel();
        model.board.emptyGrid();
        
        Coordinate kingCoord = new Coordinate(0,0);
        Coordinate rookCoord = new Coordinate(0,6);
        Coordinate pawnCoord = new Coordinate(0,3);

        Piece king = new Piece(PieceType.KING,PlayerColor.WHITE);
        Piece rook = new Piece(PieceType.ROOK, PlayerColor.BLACK);
        Piece pawn = new Piece(PieceType.PAWN, PlayerColor.WHITE);

        model.board.set(kingCoord,king);
        model.board.set(rookCoord,rook);
        model.board.set(pawnCoord,pawn);

        model.kingsPos.put(PlayerColor.WHITE,kingCoord);

        Coordinate blockedRook = model.defendingFrom(pawnCoord);
        Coordinate randomSquare = model.defendingFrom(new Coordinate(4,4));

        assertEquals(rookCoord, blockedRook);
        assert(randomSquare==null);
    }
}
