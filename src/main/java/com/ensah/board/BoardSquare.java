package com.ensah.board;

public class BoardSquare {
    private final Position position;
    private final PositionType positionType;

    public enum PositionType {LAND, RIVER, P1TRAP, P2TRAP, P1SANCTUARY, P2SANCTUARY}

    public BoardSquare(Position position, PositionType positionType) {
        this.position = position;
        this.positionType = positionType;
    }

    public Position getPosition() {
        return position;
    }

    public PositionType getPositionType() {
        return positionType;
    }

    @Override
    public String toString() {
        return "Board{" +
                "position=" + position +
                ", positionType=" + positionType +
                '}';
    }
}
