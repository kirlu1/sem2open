package chess.grid;

public class Coordinate {
    
    public final int row;
    public final int col;


    public Coordinate(int y,int x) {
        this.row = y;
        this.col = x;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Coordinate)) {
            return false;
        }
        Coordinate coordinate = (Coordinate) o;
        return row == coordinate.row && col == coordinate.col;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += 7*row;
        hash += 23*col;
        return hash;
    }

    public String toString() {
        String string = "{ row='"+row+"', col='"+col+"' }";
        return string;
    }


    // Turns a (x,y) coordinate into an index for a one-dimensional list
    public int toIndex(int cols) {
        int index = row*cols + col;
        return index;
    }

}
