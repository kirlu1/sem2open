package chess.grid;

public class CoordinateItem <E> {
    

    public final Coordinate coordinate;
    public E item;

    public CoordinateItem(Coordinate coord,E item) {
        this.item = item;
        this.coordinate = coord;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CoordinateItem)) {
            return false;
        }
        CoordinateItem<E> coordinateItem = (CoordinateItem<E>) o;
        return this.coordinate.equals(coordinateItem.coordinate) && this.item.equals(coordinateItem.item);
    }

    @Override
    public int hashCode() {
        return coordinate.hashCode()+item.hashCode();
    }

    public String toString() {
        String string = "{ coordinate='"+coordinate.toString()+"', item='"+item.toString()+"' }";
        return string;
    }

    public int getItemRow() {
        return coordinate.row;
    }

    public int getItemCol() {
        return coordinate.col;
    }

}
