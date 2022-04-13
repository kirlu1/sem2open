package chess.view;

import java.util.Iterator;

import chess.grid.Coordinate;
import chess.grid.CoordinateItem;
import chess.model.GameState;
import chess.model.Piece;
import chess.model.PlayerColor;

public interface ChessViewable {


    /**
     * Returns an iterator over non-empty squares
     * @return iterator
     */
    Iterator<CoordinateItem<Piece>> piecesOnBoard();
    
    /**
     * Helper method for whitePawnMove and blackPawnMove.
     * Step is for determining whether the pawn moves up or down the board. (Is black/white)
     * 
     * @param pos
     * @param step
     * @return Coordinate iterable
     */
    Iterable<Coordinate> pawnMove(Coordinate pos,int step);

    /**
     * 
     * Helper method for pieceMoveCoordinates
     * @param pos
     * @return Coordinate iterable
     */
    Iterable<Coordinate> whitePawnMove(Coordinate pos);

    /**
     * 
     * Helper method for pieceMoveCoordinates
     * @param pos
     * @return Coordinate iterable
     */
    Iterable<Coordinate> blackPawnMove(Coordinate pos);

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
     * @param color color of moving king
     * @return Coordinate iterable
     */
    Iterable<Coordinate> kingMove(Coordinate pos,PlayerColor color);

    /**
     * 
     * Will return an array of the coordinates that any type of piece can move to,
     * considering the borders of the board and basic rules of the game.
     * Takes current piece position as argument.
     * @param pos
     * @return Coordinate iterable
     */
    Iterable<Coordinate> pieceMoveCoordinates(Coordinate pos);
}   
