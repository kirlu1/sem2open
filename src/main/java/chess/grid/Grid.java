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
            throw new IndexOutOfBoundsException();
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

    
        
}
