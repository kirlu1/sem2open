package chess.grid;

import java.util.ArrayList;
import java.util.Iterator;

public class Grid<E> implements IGrid<E> {
     
    final int rows;
    final int cols;
    public ArrayList<CoordinateItem<E>> grid;

    public Grid(int rows,int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new ArrayList<CoordinateItem<E>>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Coordinate coord = new Coordinate(i,j);
                CoordinateItem<E> gridelem = new CoordinateItem<E>(coord,null);
                grid.add(gridelem);
            }
        }
    }

    public Grid(int rows,int cols,E value) {
        this.rows = rows;
        this.cols = cols;
        grid = new ArrayList<CoordinateItem<E>>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Coordinate coord = new Coordinate(i,j);
                CoordinateItem<E> gridelem = new CoordinateItem<E>(coord,value);
                grid.add(gridelem);
            }
        }
    }


    @Override
    public Iterator<CoordinateItem<E>> iterator() {
        return grid.iterator();
    }



    @Override
    public int getRows() {
        return rows;
    }



    @Override
    public int getCols() {
       return cols;
    }

    public void emptyGrid() {
        for(CoordinateItem<E> cItem : grid) {
            cItem.item = null;
        }
    }

    @Override
    public void set(Coordinate coordinate, E value) {
        if (!coordinateIsOnGrid(coordinate)) {
            throw new IndexOutOfBoundsException();
        }
        CoordinateItem<E> elem = new CoordinateItem<E>(coordinate,value);
        grid.set(coordinate.toIndex(cols),elem);
    }


    @Override
    public E get(Coordinate coordinate) {
        if (!coordinateIsOnGrid(coordinate)) {
            return null;
            //throw new IndexOutOfBoundsException();
        }
        return grid.get(coordinate.toIndex(cols)).item;
    }



    @Override
    public boolean coordinateIsOnGrid(Coordinate coordinate) {
        if (coordinate.row < 0 || coordinate.col < 0) {
            return false;
        }
        if (coordinate.row > rows-1 || coordinate.col > cols-1) {
            return false;
        }
        return true;
    }

    @Override
    public void turnGrid180() {
        ArrayList<CoordinateItem<E>> newGrid = new ArrayList<CoordinateItem<E>>();
        for (int i = rows-1; i > -1; i--) {
            for (int j = (i+1)*cols-1; j > i*cols-1; j--) {
                newGrid.add(grid.get(j));
            }
        }
        grid = newGrid;
    }

    @Override
    public void moveElement(Coordinate initPos, Coordinate targetPos) {
        E movedElement = get(initPos);
        set(initPos,null);
        set(targetPos,movedElement);
        
    }

    public ArrayList<Coordinate> diagonals(Coordinate intersect) {
        ArrayList<Coordinate> diagonalCoords = new ArrayList<Coordinate>();
        for(Direction dir : Direction.diagonal) {
            for (int i = 1; true; i++) {
                Coordinate newCoord = new Coordinate(intersect.row+i*dir.vert,intersect.col+i*dir.hori);
                if (!coordinateIsOnGrid(newCoord)) {
                    break;
                }
                if (coordinateIsOnGrid(newCoord)) {
                    diagonalCoords.add(newCoord);
                }
            }
        }
        return diagonalCoords;
    }

    public ArrayList<Coordinate> axes(Coordinate intersect) {
        ArrayList<Coordinate> alignedCoords = new ArrayList<Coordinate>();
        for(Direction dir : Direction.straight) {
            for (int i = 1; true; i++) {
                Coordinate newCoord = new Coordinate(intersect.row+i*dir.vert,intersect.col+i*dir.hori);
                if (!coordinateIsOnGrid(newCoord)) {
                    break;
                }
                if (coordinateIsOnGrid(newCoord)) {
                    alignedCoords.add(newCoord);
                }
            }
        }
        return alignedCoords;
    }

    public boolean alignedStraightCheck(Coordinate coord1,Coordinate coord2) {
        ArrayList<Coordinate> alignedCoords = axes(coord1);
        return alignedCoords.contains(coord2);
    }

    public boolean alignedDiagonallyCheck(Coordinate coord1,Coordinate coord2) {
        ArrayList<Coordinate> alignedCoords = diagonals(coord1);
        return alignedCoords.contains(coord2);
    }

    public boolean fullAlignmentCheck(Coordinate coord1,Coordinate coord2) {
        return (alignedStraightCheck(coord1, coord2) || alignedDiagonallyCheck(coord1, coord2));
    }

    public Direction alignmentOrientation(Coordinate origin,Coordinate pointingTo) {
        for(Direction dir : Direction.directions) {
            for (int i = 1; true; i++) {
                Coordinate newCoord = new Coordinate(origin.row+i*dir.vert,origin.col+i*dir.hori);
                if (!coordinateIsOnGrid(newCoord)) {
                    break;
                }
                if (newCoord.equals(pointingTo)) {
                    return dir;
                }
            }
        }
        return null;
    }

    public ArrayList<Coordinate> coordinatesInDirection(Coordinate origin,Direction dir) {
        ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
        for (int i = 1; true; i++) {
            Coordinate newCoord = new Coordinate(origin.row+i*dir.vert,origin.col+i*dir.hori);
            if (!coordinateIsOnGrid(newCoord)) {
                return coords;
            }
            coords.add(newCoord);
        }
    }

    public ArrayList<Coordinate> coordinatesInBetween(Coordinate start,Coordinate end) {
        ArrayList<Coordinate> betweenCoords = new ArrayList<Coordinate>();
        for(Direction dir : Direction.directions) {
            for (int i = 1; true; i++) {
                Coordinate newCoord = new Coordinate(start.row+i*dir.vert,start.col+i*dir.hori);
                if (!coordinateIsOnGrid(newCoord)) {
                    betweenCoords.clear();
                    break;
                }
                if (newCoord.equals(end)) {
                    return betweenCoords;
                }
                betweenCoords.add(newCoord);
            }
        }
        return null;
    }
}
