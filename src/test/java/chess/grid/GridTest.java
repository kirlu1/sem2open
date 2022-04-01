package chess.grid;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GridTest {
    
    @Test
    public void turn180test() { //checks that turnGrid180 works properly
        Grid<CoordinateItem<Boolean>> testGrid = new Grid<CoordinateItem<Boolean>>(3,3);

        Coordinate topLeft = new Coordinate(0,0);
        Coordinate bottomRight = new Coordinate(2,2);

        CoordinateItem<Boolean> initialTopLeft = new CoordinateItem<Boolean>(topLeft, true);
        CoordinateItem<Boolean> initialBottomRight = new CoordinateItem<Boolean>(bottomRight, false);

        testGrid.set(topLeft,initialTopLeft);
        testGrid.set(bottomRight,initialBottomRight);
        assertEquals(initialTopLeft, testGrid.get(topLeft));
        assertEquals(initialBottomRight, testGrid.get(bottomRight));
        testGrid.turnGrid180();

        assertEquals(initialBottomRight, testGrid.get(topLeft));
        assertEquals(initialTopLeft, testGrid.get(bottomRight));
    }


}
