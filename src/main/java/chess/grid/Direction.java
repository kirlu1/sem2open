package chess.grid;

public enum Direction {

    NORTH(-1,0,false),
    SOUTH(1,0,false),
    EAST(0,1,false),
    WEST(0,-1,false),
    NORTH_EAST(-1,1,true),
    NORTH_WEST(-1,-1,true),
    SOUTH_EAST(1,1,true),
    SOUTH_WEST(1,-1,true);

    public static Direction[] diagonal = {NORTH_EAST,
        NORTH_WEST,
        SOUTH_EAST,
        SOUTH_WEST};

    public static Direction[] straight = {NORTH,
        SOUTH,
        EAST,
        WEST};

    public static Direction[] directions = {NORTH,
        SOUTH,
        EAST,
        WEST,
        NORTH_EAST,
        NORTH_WEST,
        SOUTH_EAST,
        SOUTH_WEST};

    public final int vert;
    public final int hori;
    public final boolean isDiagonal;

    Direction(int vert, int hori,boolean isDiagonal) {
        this.vert = vert;
        this.hori = hori;
        this.isDiagonal = isDiagonal;
    }

    public Direction invert() {
        switch (this) {
            case NORTH: return SOUTH;
            case SOUTH: return NORTH;
            case WEST: return EAST;
            case EAST: return WEST;
            case NORTH_EAST: return SOUTH_WEST;
            case NORTH_WEST: return SOUTH_EAST;
            case SOUTH_EAST: return NORTH_WEST;
            case SOUTH_WEST: return NORTH_EAST;
            default: return null;
        }
    }
}
