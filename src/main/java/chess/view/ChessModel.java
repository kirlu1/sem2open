package chess.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import chess.model.GameState;
import chess.model.Piece;
import chess.model.PieceType;
import chess.model.PlayerColor;
import chess.controller.ChessControllable;
import chess.grid.Coordinate;
import chess.grid.CoordinateItem;
import chess.grid.Direction;

public class ChessModel implements ChessViewable, ChessControllable {

    public ChessBoard board;
    public GameState state;
    public int windowDimension = 1000;
    public boolean isMoving;
    public ArrayList<Coordinate> legalMoves;
    public Coordinate selectedSquare;

    public HashMap<PlayerColor,Coordinate> kingsPos;

    public ChessModel() {
        this.board = new ChessBoard();
        Coordinate whiteKingPos = new Coordinate(7,3);
        Coordinate blackKingPos = new Coordinate(0,4);
        this.kingsPos = new HashMap<PlayerColor,Coordinate>();

        this.kingsPos.put(PlayerColor.WHITE,whiteKingPos);
        this.kingsPos.put(PlayerColor.BLACK,blackKingPos);

        this.state = GameState.WHITE_TURN;
        this.isMoving = false;
    }

    @Override
    public ArrayList<Coordinate> whitePawnMove(Coordinate pos) {
        ArrayList<Coordinate> targetableSquares = pawnMove(pos,-1);
        if (!board.get(pos).hasMoved && (board.get(new Coordinate(5,pos.col))==null) && (board.get(new Coordinate(4,pos.col))==null)) {
            targetableSquares.add(new Coordinate(4,pos.col));
        }
        return targetableSquares;
    }

    @Override
    public ArrayList<Coordinate> blackPawnMove(Coordinate pos) {
        ArrayList<Coordinate> targetableSquares = pawnMove(pos,1);
        if (!board.get(pos).hasMoved && (board.get(new Coordinate(2,pos.col))==null) && (board.get(new Coordinate(3,pos.col))==null)) {
            targetableSquares.add(new Coordinate(3,pos.col));
        }
        return targetableSquares;
    }

    @Override 
    public ArrayList<Coordinate> pawnMove(Coordinate pos,int step) {
        ArrayList<Coordinate> targetableSquares = new ArrayList<Coordinate>();
        int startRow = pos.row;
        int startCol = pos.col;
        Coordinate frontSquare = new Coordinate(startRow+step,startCol);
        PlayerColor pawnColor = board.get(pos).color;
        if (board.get(frontSquare)==null) {
            targetableSquares.add(frontSquare);
        }
        Coordinate leftSquare = new Coordinate(startRow+step,startCol-1);
        Coordinate rightSquare = new Coordinate(startRow+step,startCol+1);
        if (!(board.get(leftSquare)==null)) {
            if (!board.get(leftSquare).color.equals(pawnColor)) {
            targetableSquares.add(leftSquare);
            }
        }
        if (!(board.get(rightSquare)==null)) {
            if (!board.get(rightSquare).color.equals(pawnColor)) {
                targetableSquares.add(rightSquare);
                }
        }
        return targetableSquares;
    }

    @Override
    public ArrayList<Coordinate> knightMove(Coordinate pos) {
        ArrayList<Coordinate> targetableSquares = new ArrayList<Coordinate>();
        int startRow = pos.row;
        int startCol = pos.col;
        int[] dir = {-2,2};
        int[] dir2 = {-1,1};
        PlayerColor movingColor = board.get(pos).color;
        for(int i : dir) {
            for(int j : dir2) {
                Coordinate newCoord = new Coordinate(startRow+i,startCol+j);
                Coordinate newCoord2 = new Coordinate(startRow+j,startCol+i);
                Coordinate[] cArray = {newCoord,newCoord2};
                for(Coordinate checkedCoord : cArray) {
                    if (board.coordinateIsOnGrid(checkedCoord)) {
                        if (!(board.get(checkedCoord)==null)) {
                            if (!board.get(checkedCoord).color.equals(movingColor)) {
                                targetableSquares.add(checkedCoord);
                            }
                        } else {targetableSquares.add(checkedCoord);}
                    }
                }
            }
        }
        return targetableSquares;
    }

    @Override
    public ArrayList<Coordinate> bishopMove(Coordinate pos) {
        ArrayList<Coordinate> targetableSquares = new ArrayList<Coordinate>();
        int startRow = pos.row;
        int startCol = pos.col;
        PlayerColor movingColor = board.get(pos).color;
        for(int i = -1; i < 2; i+=2) {
            for(int j = -1; j < 2; j+=2) {
                for (int k = 1; true; k++) {
                    Coordinate newCoord = new Coordinate(startRow+k*i,startCol+k*j);
                    if (!board.coordinateIsOnGrid(newCoord)) {
                        break;
                    }
                    if (!(board.get(newCoord)==null)) {
                        if (!board.get(newCoord).color.equals(movingColor)) {
                            targetableSquares.add(newCoord);
                        }
                        break;
                    }
                    targetableSquares.add(newCoord);
                }
            }
        }
        return targetableSquares;
    }

    @Override
    public ArrayList<Coordinate> rookMove(Coordinate pos) {
        ArrayList<Coordinate> targetableSquares = new ArrayList<Coordinate>();
        int startRow = pos.row;
        int startCol = pos.col;
        PlayerColor movingColor = board.get(pos).color;
        for (int i = -1; i < 2; i+=2) {
            for (int k = 0; k < 2; k++) {
                for (int j = 1; true; j++) {
                    Coordinate newCoord = new Coordinate(startRow+i*j*(1-k),startCol+i*j*k);
                    if (!board.coordinateIsOnGrid(newCoord)) {
                        break;
                    }
                    if (!(board.get(newCoord)==null)) {
                        if (!board.get(newCoord).color.equals(movingColor)) {
                            targetableSquares.add(newCoord);
                        }
                        break;
                    }
                    targetableSquares.add(newCoord);
                }
            }
        }
        return targetableSquares;
    }

    @Override
    public ArrayList<Coordinate> queenMove(Coordinate pos) {
        ArrayList<Coordinate> targetableSquares = rookMove(pos);
        targetableSquares.addAll(bishopMove(pos));
        return targetableSquares;
    }

    @Override
    public ArrayList<Coordinate> kingMove(Coordinate pos,PlayerColor color) {
        ArrayList<Coordinate> targetableSquares = new ArrayList<Coordinate>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                Coordinate testedSquare = new Coordinate(pos.row+i,pos.col+j);
                if ((!(i==0 && j==0)) && board.coordinateIsOnGrid(testedSquare)) {
                    if (board.get(testedSquare)==null) {
                        if (isSquareSafe(testedSquare, color.oppositeColor())) {
                            targetableSquares.add(testedSquare);
                        }
                    } else {
                        if (board.get(testedSquare).color.equals(color.oppositeColor())) {
                            if (isSquareSafe(testedSquare,color.oppositeColor())) {
                                targetableSquares.add(testedSquare);
                            }
                        }
                    }
                }
            }
        }
        return targetableSquares;
    }

    @Override
    public ArrayList<Coordinate> pieceMoveCoordinates(Coordinate pos) {
        Piece piece = board.get(pos);
        switch(piece.type) {
            case PAWN:
                if (piece.color.equals(PlayerColor.WHITE)) {return whitePawnMove(pos);}
                return blackPawnMove(pos);
            case KNIGHT: return knightMove(pos);
            case BISHOP: return bishopMove(pos);
            case ROOK: return rookMove(pos);
            case QUEEN: return queenMove(pos);
            case KING: 
                if (piece.color.equals(PlayerColor.WHITE)) {return kingMove(pos,PlayerColor.WHITE);}
                else {return kingMove(pos,PlayerColor.BLACK);}
            default: return null;
        }
    }

    @Override
    public void movePiece(Coordinate origin,Coordinate target) {
        Piece movedPiece = board.get(origin);
        movedPiece.wasMoved();
        if (movedPiece.type.equals(PieceType.KING)) {
            kingsPos.put(movedPiece.color,target);
        }
        board.moveElement(origin,target);
        if (movedPiece.type.equals(PieceType.PAWN)) {
            pawnPromotion(movedPiece.color);
        }
    }

    public boolean isSquareSafeNoKing(Coordinate square, PlayerColor attackingPlayer) {
        int coordRow = square.row;
        int coordCol = square.col;
        // any attacking pawns?
        if (attackingPlayer.equals(PlayerColor.WHITE)) {
            for (int i = -1; i < 2; i+=2) {
                Piece adjacentSquare = board.get(new Coordinate(coordRow+1,coordCol+i));
                if (!(adjacentSquare==null)) {
                    if (adjacentSquare.type.equals(PieceType.PAWN) && adjacentSquare.color.equals(PlayerColor.WHITE)) {
                        return false;
                    }
                }
            }
        } else {
            for (int i = -1; i < 2; i+=2) {
                Piece adjacentSquare = board.get(new Coordinate(coordRow-1,coordCol+i));
                if (!(adjacentSquare==null)) {
                    if (adjacentSquare.type.equals(PieceType.PAWN) && adjacentSquare.color.equals(PlayerColor.BLACK)) {
                        return false;
                    }
                }
            }
        }
        //any attacking bishops or queens?
        for (int j = -1; j < 2; j+=2) {
            for (int i = -1; i < 2; i+=2) {
                for (int k = 1; true; k++) {
                    Coordinate diagonalSquare = new Coordinate(coordRow+j*k,coordCol+i*k);
                    if (!board.coordinateIsOnGrid(diagonalSquare)) {
                        break;
                    }
                    Piece attackingPiece = board.get(diagonalSquare);
                    if (!(attackingPiece==null)) {
                        if (!attackingPiece.color.equals(attackingPlayer)) {
                            if (attackingPiece.type.equals(PieceType.KING)) {
                                continue;
                            }
                            break;
                        }
                        if (attackingPiece.type.equals(PieceType.QUEEN) || attackingPiece.type.equals(PieceType.BISHOP)) {
                            return false;
                        } else if (!(attackingPiece==null)) {break;}
                    }
                }
            }
        }
        // any attacking rooks or queens?
        int[] dir1 = {0,1,0,-1};
        int[] dir2 = {1,0,-1,0};
        for (int i = 0; i < 4; i++) {
            for (int j = 1; true; j++) {
                Coordinate alignedSquare = new Coordinate(coordRow+dir1[i]*j,coordCol+dir2[i]*j);
                if (!board.coordinateIsOnGrid(alignedSquare)) {break;}
                Piece attackingPiece = board.get(alignedSquare);
                if (!(attackingPiece==null)) {
                    if (!attackingPiece.color.equals(attackingPlayer)) {
                        if (attackingPiece.type.equals(PieceType.KING)) {
                            continue;}
                        break;
                    }
                    if (attackingPiece.type.equals(PieceType.QUEEN) || attackingPiece.type.equals(PieceType.ROOK)) {
                        return false;
                    } else if (!(attackingPiece==null)) {break;}
                }
            }
        }
        // any attacking knights?
        int[] dir3 = {1,1,-1,-1,2,2,-2,-2};
        int[] dir4 = {2,-2,2,-2,1,-1,1,-1};
        for (int i = 0; i < 8; i++) {
            Coordinate knightStepSquare = new Coordinate(coordRow+dir3[i],coordCol+dir4[i]);
            if (board.coordinateIsOnGrid(knightStepSquare)) {
                Piece attackingPiece = board.get(knightStepSquare);
                if (!(attackingPiece==null)) {
                    if (attackingPiece.type.equals(PieceType.KNIGHT) && attackingPiece.color.equals(attackingPlayer)) {
                        return false;
                    }
                }
            }
        }
        
        return true;
    }

    @Override
    public boolean isSquareSafe(Coordinate square,PlayerColor attackingPlayer) {
        if (!isSquareSafeNoKing(square,attackingPlayer)) {
            return false;
        }

        // is the square being defended by the attacking side's king?
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                Coordinate adjacentSquare = new Coordinate(square.row+i,square.col+j);
                if ((!(i==j && i==0)) && board.coordinateIsOnGrid(adjacentSquare)) {
                    Piece attackingPiece = board.get(adjacentSquare);
                    if (!(attackingPiece == null)) {
                        if (attackingPiece.type.equals(PieceType.KING) && attackingPiece.color.equals(attackingPlayer)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void pawnPromotion(PlayerColor movingPlayer) {
        int r;
        if (movingPlayer.equals(PlayerColor.WHITE)) {r=0;}
        else {r=7;}
        for (int i = 0; i < 8; i++) {
            Piece piece = board.get(new Coordinate(r,i));
            if (!(piece==null)) {
                if (piece.type.equals(PieceType.PAWN)) {
                    piece.promote();
                }
            }
        }
    }

    @Override
    public Iterator<CoordinateItem<Piece>> piecesOnBoard() {
        ArrayList<CoordinateItem<Piece>> pieceArray = new ArrayList<CoordinateItem<Piece>>();
        Iterator<CoordinateItem<Piece>> boardIter = board.iterator();
        while(boardIter.hasNext()) {
            CoordinateItem<Piece> square = boardIter.next();
            if (!(square.item==null)) {
                pieceArray.add(square);
            }
        }
        return pieceArray.iterator();
    }


    public PlayerColor getTurnColor() {
        switch (this.state) {
            case WHITE_TURN:
            case WHITE_CHECKED:
                return PlayerColor.WHITE;
            case BLACK_TURN:
            case BLACK_CHECKED:
                return PlayerColor.BLACK;
            default:
                return null;

        }
    }

    public void newTurn() {
        PlayerColor movingColor = getTurnColor();
        if (movingColor.equals(PlayerColor.WHITE)) {
            if (isChecked(PlayerColor.BLACK)) {
                if (isMated(PlayerColor.BLACK)) {
                    state = GameState.BLACK_MATED;
                } else {
                    state = GameState.BLACK_CHECKED;
                }
            } else {if (isStalemate(PlayerColor.BLACK)) {
                state = GameState.STALEMATE;
            } else {state = GameState.BLACK_TURN;}}
        } else {
            if(isChecked(PlayerColor.WHITE)) {
                if (isMated(PlayerColor.WHITE)) {
                    state = GameState.WHITE_MATED;
                } else {
                    state = GameState.WHITE_CHECKED;
                }
            } else {if (isStalemate(PlayerColor.WHITE)) {
                state = GameState.STALEMATE;
            } else {state = GameState.WHITE_TURN;}}
        }
    }

    public void noLongerMoving() {
        isMoving = false;
        legalMoves.clear();
    }

    private boolean isChecked(PlayerColor kingColor) {
        Coordinate kingCoord = kingsPos.get(kingColor);
        return !isSquareSafe(kingCoord, kingColor.oppositeColor());
    }

    public Coordinate defendingFrom(Coordinate pieceCoord) {
        Coordinate myKing = kingsPos.get(getTurnColor());
        if (!board.fullAlignmentCheck(pieceCoord,myKing)) {
            return null;}
        if (isObstructed(pieceCoord, myKing)) {
            return null;}
        Direction kingThroughPiece = board.alignmentOrientation(myKing, pieceCoord);
        PieceType threat;
        if (kingThroughPiece.isDiagonal) {threat=PieceType.BISHOP;}
        else {threat=PieceType.ROOK;}
        for (int i = 1; true; i++) {
            Coordinate blockedCoordinate = new Coordinate(pieceCoord.row+i*kingThroughPiece.vert,pieceCoord.col+i*kingThroughPiece.hori);
            if (!board.coordinateIsOnGrid(blockedCoordinate)) {
                return null;
            }
            Piece attacker = board.get(blockedCoordinate);
            if (attacker==null) {
                continue;}
            if (attacker.color.equals(getTurnColor())) {
                return null;}
            if (attacker.type.equals(PieceType.QUEEN) || attacker.type.equals(threat)) {
                return blockedCoordinate;
            } else {return null;}
        }
    }

    public ArrayList<Coordinate> farThreatCoordinates(PlayerColor threatenedKing) { // A king can be checked by multiple ROOKS/BISHOPS/QUEENS
        ArrayList<Coordinate> threats = new ArrayList<Coordinate>();
        Coordinate kingPos = kingsPos.get(threatenedKing);
        for(Direction dir : Direction.directions) {
            PieceType threat;
            if (dir.isDiagonal) {threat=PieceType.BISHOP;}
            else {threat=PieceType.ROOK;}
            for (int i = 1; true; i++) {
                Coordinate threatCoordinate = new Coordinate(kingPos.row+i*dir.vert,kingPos.col+i*dir.hori);
                if(!board.coordinateIsOnGrid(threatCoordinate)) {break;}
                Piece attacker = board.get(threatCoordinate);
                if (attacker==null) {
                    continue;}
                if (attacker.color.equals(getTurnColor())) {
                    break;}
                if (attacker.type.equals(PieceType.QUEEN) || attacker.type.equals(threat)) {
                    threats.add(threatCoordinate);
                } else {break;}
            }
            if (threats.size()>1) {return threats;}
        }
        return threats;
    }

    public Coordinate knightThreatCoordinate(PlayerColor threatenedKing) { //the king can only be threatened by a single knight at a time
        Coordinate kingPos = kingsPos.get(threatenedKing);
        for(Coordinate threatCoordinate : knightMove(kingPos)) {
            Piece attacker = board.get(threatCoordinate);
            if (attacker==null) {continue;}
            if (attacker.type.equals(PieceType.KNIGHT)) {
                return threatCoordinate;
            }
        }
        return null;
    }

    public Coordinate pawnThreatCoordinate(PlayerColor threatenedKing) { // the king can only be threatened by a single pawn at a time
        int s;
        Coordinate kingPos = kingsPos.get(threatenedKing);
        if (threatenedKing.equals(PlayerColor.WHITE)) {s = -1;}
        else {s = 1;}
        for (int i = -1; i < 2; i+=2) {
            Coordinate threatCoordinate = new Coordinate(kingPos.row+s,kingPos.col+i);
            Piece attacker = board.get(threatCoordinate);
            if (attacker==null) {continue;}
            if (attacker.color.equals(threatenedKing.oppositeColor()) && attacker.type.equals(PieceType.PAWN)) {
                return threatCoordinate;
            }
        }
        return null;
    }

    private boolean isObstructed(Coordinate coord1,Coordinate coord2) {
        ArrayList<Coordinate> betweenCoords = board.coordinatesInBetween(coord1, coord2);
        for(Coordinate coord : betweenCoords) {
            if (!(board.get(coord)==null)) {
                return true;
            }
        }
        return false;
    }

    public boolean isMated(PlayerColor kingColor) {
        Coordinate kingPos = kingsPos.get(kingColor);
        ArrayList<Coordinate> kingMoves = pieceMoveCoordinates(kingPos);
        if (kingMoves.size()>0) {return false;}

        ArrayList<Coordinate> farThreats = farThreatCoordinates(kingColor);
        Coordinate knightThreat = knightThreatCoordinate(kingColor);
        Coordinate pawnThreat = pawnThreatCoordinate(kingColor);

        if (farThreats.size()>1) {return true;}
        if (!(knightThreat==null)) {
            if (!isSquareSafe(knightThreat, kingColor)) {return true;}
            if (farThreats.size()>0) {return true;}
        }

        if (farThreats.size()==1) {
            Coordinate farThreat = farThreats.get(0);
            if (!isSquareSafe(farThreat, kingColor)) {return false;}
            ArrayList<Coordinate> blockingSquares = board.coordinatesInBetween(kingPos, farThreat);

            for(Coordinate square : blockingSquares) {
                if (!isSquareSafe(square, kingColor)) {
                    return false;
                }
            }
        }

        if (!(pawnThreat==null)) {
            if (isSquareSafe(pawnThreat,kingColor.oppositeColor())) {
                return false;
            }
            if (!isSquareSafeNoKing(pawnThreat,kingColor)) {
                return false;
            }
        }

        return true;
    }

    public boolean isStalemate(PlayerColor unmovingPlayer) {
        if (insufficientMaterial()) {return true;}

        for(CoordinateItem<Piece> square : board) {
            if (!(square.item==null)) {
                if (square.item.color.equals(unmovingPlayer)) {
                    if (pieceMoveCoordinates(square.coordinate).size()>0) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
    
    public boolean insufficientMaterial() {
        PieceType[] pieces = {PieceType.PAWN,PieceType.BISHOP,PieceType.QUEEN,PieceType.ROOK};
        for(CoordinateItem<Piece> square : board) {
            if (!(square.item==null)) {
                for(PieceType piece : pieces) {
                    if (square.item.type.equals(piece)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}