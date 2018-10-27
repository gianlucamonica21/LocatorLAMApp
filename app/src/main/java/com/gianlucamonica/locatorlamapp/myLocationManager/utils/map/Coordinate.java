package com.gianlucamonica.locatorlamapp.myLocationManager.utils.map;

public class Coordinate {

    private int x;
    private int y;


    public Coordinate(int x,int y) {
        this.x = x;
        this.y = y;
    }

    public void mult(int mult, int add){
        this.x *= mult;
        this.y *= mult;
        this.x += add;
        this.y += add;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {

        return y;
    }

    public int getX() {

        return x;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
