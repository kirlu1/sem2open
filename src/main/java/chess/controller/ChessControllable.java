package chess.controller;

import chess.grid.Coordinate;
import chess.model.PlayerColor;

public interface ChessControllable {
    
    

    /**
     * 
     * Moves piece, setting Piece.hasMoved to true
     * @param origin coordinate
     * @param target coordinate to move to
     */
    void movePiece(Coordinate origin,Coordinate target);

    /**
     * Checks whether a specified square is "contested" by the specified color.
     * Essentially whether the opposing king is allowed to reside on the square.
     * 
     * @param square to check
     * @param color of the player which would be attacking the square
     * @return true if the square is safe
     */
    boolean isSquareSafe(Coordinate square,PlayerColor attackingPlayer);

    /**
     * Promotes any pawns in the top/bottom row
     * @param 
     */
    void pawnPromotion(PlayerColor movingPlayer);
}
