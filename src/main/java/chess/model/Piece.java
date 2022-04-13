package chess.model;

public class Piece {
    public PieceType type;
    public final PlayerColor color;
    public boolean hasMoved;


    public Piece(PieceType type, PlayerColor color) {
        this.type = type;
        this.color = color;
        this.hasMoved = false;
    }

    public void wasMoved() {
        hasMoved = true;
    }

    public Piece copy() {
        Piece newPiece = new Piece(type,color);
        if (hasMoved) {newPiece.wasMoved();}
        return newPiece;
    }

    public void promote() {
        type = PieceType.QUEEN;
    }
}
