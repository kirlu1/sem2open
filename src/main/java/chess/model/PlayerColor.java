package chess.model;

public enum PlayerColor {
    WHITE,BLACK;


    public PlayerColor oppositeColor() {
        if (this.equals(WHITE)) {return BLACK;}
        return WHITE;
    }
}
