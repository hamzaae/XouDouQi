package com.ensah.board;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y){
        this.x=x;
        this.y=y;
    }

    public Position(Position position){
        this.x=position.getI();
        this.y= position.getJ();
    }

    public int getI() {
        return x;
    }

    public int getJ() {
        return y;
    }

    public void setX(int j) {
        this.x = x;
    }

    public void setY(int i) {
        this.y= y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return x == position.x && y == position.y;
    }

    @Override
    public String toString() {
        return "(" + "x=" + x + ", y=" + y + ')';
    }
}

