package chess.view;

import java.util.Iterator;

import chess.grid.Coordinate;
import chess.grid.CoordinateItem;
import chess.model.Piece;

public interface ChessViewable {
    
    /**
     * 
     * Helper method for pieceMoveCoordinates
     * @param pos
     * @return Coordinate iterable
     */
    Iterable<Coordinate> pawnMove(Coordinate pos);

    /**
     * 
     * Helper method for pieceMoveCoordinates
     * @param pos
     * @return Coordinate iterable
     */
    Iterable<Coordinate> knightMove(Coordinate pos);

    /**
     * 
     * Helper method for pieceMoveCoordinates
     * @param pos
     * @return Coordinate iterable
     */
    Iterable<Coordinate> bishopMove(Coordinate pos);

    /**
     * 
     * Helper method for pieceMoveCoordinates
     * @param pos
     * @return Coordinate iterable
     */
    Iterable<Coordinate> rookMove(Coordinate pos);

    /**
     * 
     * Helper method for pieceMoveCoordinates
     * @param pos
     * @return Coordinate iterable
     */
    Iterable<Coordinate> queenMove(Coordinate pos);

    /**
     * 
     * Helper method for pieceMoveCoordinates
     * @param pos
     * @return Coordinate iterable
     */
    Iterable<Coordinate> kingMove(Coordinate pos);

    /**
     * 
     * Will return an array of the coordinates that any type of piece can move to, not considering the rules of chess.
     * Takes current piece position as argument.
     * @param pos
     * @return Coordinate iterable
     */
    Iterable<Coordinate> pieceMoveCoordinates(Coordinate pos);

    /**
     * 
     * Returns an iterator of the squares a piece can legally move to.
     * Takes the return of pieceMoveCoordinates as argument.
     * @param coordArray
     * @return Legal coordinate iterator
     */
    Iterator<Coordinate> pieceLegalSquares(Iterable<Coordinate> allPossibleCoords);

    /**
     * 
     * Checks whether a king is mated. If the king is checked the gamestate is set to either WHITE_CHECKED or BLACK_CHECKED
     * @param kingCoord
     */
    void checkCheck(Coordinate kingCoord);
}
