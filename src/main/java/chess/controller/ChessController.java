package chess.controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import chess.grid.Coordinate;
import chess.grid.CoordinateItem;
import chess.model.Piece;
import chess.model.PieceType;
import chess.model.GameState;
import chess.view.ChessModel;
import chess.view.ChessView;

public class ChessController {
    
    ChessModel model;
    ChessView view;

    public ChessSquareButton [] buttonArray;


    public ChessController(ChessModel model,ChessView view) {
        this.view = view;
        this.model = model;
        int size = model.windowDimension;
        int squareSize = (int) ((size-50)/(8));

        this.buttonArray = new ChessSquareButton[64];
        int c = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Coordinate buttonCoord = new Coordinate(i,j);
                Color color = Color.BLACK;
                int s;
                if (i%2==0) {s = 0;}
                else {s = 1;}
                if ((c%2)==s) {color = Color.BLACK;}
                else {color = Color.WHITE;}

                ChessSquareButton newButton = new ChessSquareButton(buttonCoord,i*squareSize,j*squareSize,color,squareSize,this);
                this.buttonArray[c] = newButton;
                c++;
            }
        }
        for(ChessSquareButton newButton : this.buttonArray) {
            view.add(newButton);
        }
    }

    public void piecesToLabels() {
        Iterator<CoordinateItem<Piece>> pieceIterator = model.board.iterator();
        while(pieceIterator.hasNext()) {
            CoordinateItem<Piece> pieceItem = pieceIterator.next();
            pieceToLabel(pieceItem.coordinate,pieceItem.item);
        }
    }

    void pieceToLabel(Coordinate squareCoord,Piece piece) {
        int index = squareCoord.toIndex(8);
        String label = "";
        if (piece==null) {
            buttonArray[index].setText(label);
        } else {
            switch (piece.color) {
                case WHITE:
                    label+="White ";
                    break;
                case BLACK:
                    label+="Black ";
            }
            switch (piece.type) {
                case PAWN:
                    label+="Pawn";
                    break;
                case BISHOP:
                    label+="Bishop";
                    break;
                case KING:
                    label+="King";
                    break;
                case KNIGHT:
                    label+="Knight";
                    break;
                case QUEEN:
                    label+="Queen";
                    break;
                case ROOK:
                    label+="Rook";
                    break;
                default:
                    break;
            }
            buttonArray[index].setText(label);
        }
    }

    public ChessSquareButton getButton(Coordinate buttonCoord) {
        int index = buttonCoord.toIndex(8);
        return buttonArray[index];
    }

    public void squareClicked(Coordinate squareCoord) {
        if (model.state.equals(GameState.WHITE_MATED) || model.state.equals(GameState.BLACK_MATED)) {return;}

        Piece squarePiece = model.board.get(squareCoord);
        if (!model.isMoving && !(squarePiece==null)) {
            if (squarePiece.color.equals(model.getTurnColor())) {
                model.legalMoves = model.pieceMoveCoordinates(squareCoord);
                Coordinate blocked = model.defendingFrom(squareCoord);

                if (!squarePiece.type.equals(PieceType.KING)) {
                    if (model.state.equals(GameState.BLACK_CHECKED) || model.state.equals(GameState.WHITE_CHECKED)) {

                        ArrayList<Coordinate> bishopRookQueen = model.farThreatCoordinates(model.getTurnColor());
                        Coordinate knight = model.knightThreatCoordinate(model.getTurnColor());
                        Coordinate pawn = model.pawnThreatCoordinate(model.getTurnColor());
                        int c = 0;

                        c+=bishopRookQueen.size();
                        if (!(knight==null)) {c++;}

                        if (c<2 && blocked==null) { //in cases of more than one threat, the king must be moved, so there is no point in checking for legal moves of other pieces
                            if (bishopRookQueen.size()==1) {
                                Coordinate farThreat = bishopRookQueen.get(0);
                                ArrayList<Coordinate> betweenCoords = model.board.coordinatesInBetween(farThreat,model.kingsPos.get(model.getTurnColor()));
                                betweenCoords.add(farThreat);
                                model.legalMoves.retainAll(betweenCoords);
                            } else {
                            if (!(knight == null)) {
                                if (model.legalMoves.contains(knight)) {
                                    model.legalMoves.clear();
                                    model.legalMoves.add(knight);}
                                else {model.legalMoves.clear();}
                            }}
                            if (!(pawn==null)) {
                                if (model.legalMoves.contains(pawn)) {
                                    model.legalMoves.clear();
                                    model.legalMoves.add(pawn);
                                } else {model.legalMoves.clear();}
                            }
                        } else {
                            model.legalMoves.clear();
                        }
                } else {if (!(blocked==null)) {
                    model.legalMoves.retainAll(model.board.coordinatesInBetween(model.kingsPos.get(model.getTurnColor()),blocked));
            } 
            }}
            finalizeSelectClick(squareCoord);
        }
    }
        else if (model.isMoving) {
            if (model.legalMoves.contains(squareCoord)) {
                executeMove(squareCoord); // piece gets to move
            }
            else {
                if (!(model.board.get(squareCoord)==null)) { // No need to deselect selected piece before selecting another
                    if (model.board.get(squareCoord).color.equals(model.getTurnColor())) {
                        model.noLongerMoving();
                        resetColor();
                        squareClicked(squareCoord);
                    }
                } else { // normal deselection
                model.noLongerMoving();
                resetColor();
                }
            }
        }
    }

    private void executeMove(Coordinate square) {
        model.movePiece(model.selectedSquare,square);
                model.noLongerMoving();
                model.newTurn();
                resetColor();
                if (model.state.equals(GameState.WHITE_MATED) || model.state.equals(GameState.BLACK_MATED) || model.state.equals(GameState.STALEMATE)) {
                    view.endScreen();
                }
                piecesToLabels();
    }

    private void paintLegalSquares(Iterator<Coordinate> squareIter) {
        while(squareIter.hasNext()) {
            Coordinate square = squareIter.next();
            ChessSquareButton button = getButton(square);
            button.highlight();
        }
    }


    private void resetColor() {
        for(ChessSquareButton button : buttonArray) {
            button.setBackground(button.tileColor);
        }
    }

    private void finalizeSelectClick(Coordinate squareCoord) {
        if (model.legalMoves.size() > 0) { //without this condition you would have to unselect a square to select another 
            model.isMoving = true; //even if its piece actually couldnt move anywhere, which was annoying
            model.selectedSquare = squareCoord;
            getButton(model.selectedSquare).setBackground(Color.GREEN);
            paintLegalSquares(model.legalMoves.iterator());
        }
    }
}
