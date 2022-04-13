package chess.view;

import chess.grid.Coordinate;
import chess.grid.Grid;
import chess.model.Piece;
import chess.model.PieceType;
import chess.model.PlayerColor;

public class ChessBoard extends Grid<Piece> {
    
    int row;
    int col;

    public ChessBoard() {
        super(8,8);
        placePieces(PlayerColor.BLACK,1,0);
        placePieces(PlayerColor.WHITE,6,7);
        placePiece(0,3,PlayerColor.BLACK,PieceType.QUEEN);
        placePiece(0,4,PlayerColor.BLACK,PieceType.KING);
        placePiece(7,3,PlayerColor.WHITE,PieceType.KING);
        placePiece(7,4,PlayerColor.WHITE,PieceType.QUEEN);
    }

    private ChessBoard(boolean empty) {
        super(8,8);
    }

    private void placePieces(PlayerColor color,int pawnRow,int mainRow) {
        for (int i = 0; i < 8; i++) {
            Piece pawn = new Piece(PieceType.PAWN,color);
            set(new Coordinate(pawnRow,i),pawn);
        }
        placePiece(mainRow,0,color,PieceType.ROOK);
        placePiece(mainRow,7,color,PieceType.ROOK);
        placePiece(mainRow,1,color,PieceType.KNIGHT);
        placePiece(mainRow,6,color,PieceType.KNIGHT);
        placePiece(mainRow,2,color,PieceType.BISHOP);
        placePiece(mainRow,5,color,PieceType.BISHOP);
    }

    private void placePiece(int row,int col,PlayerColor color,PieceType type) {
        set(new Coordinate(row,col),new Piece(type,color));
    }

    public ChessBoard copy() { // never used
        ChessBoard boardCopy = new ChessBoard(true);
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                Coordinate squareCoord = new Coordinate(i,j);
                Piece item = get(squareCoord).copy();
                boardCopy.set(squareCoord,item);
            }
        }
        return boardCopy;
    }
}
