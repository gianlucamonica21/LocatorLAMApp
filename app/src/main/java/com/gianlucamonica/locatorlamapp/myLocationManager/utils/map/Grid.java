package com.gianlucamonica.locatorlamapp.myLocationManager.utils.map;

public class Grid {

    Coordinate a;
    Coordinate b;
    String name;
    int id;

    public Grid(Coordinate a, Coordinate b, String name){
        this.a = a;
        this.b = b;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinate getA() {
        return a;
    }

    public void setA(Coordinate a) {
        this.a = a;
    }

    public Coordinate getB() {
        return b;
    }

    public void setB(Coordinate b) {
        this.b = b;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Grid{" +
                "a=" + a +
                ", b=" + b +
                ", name='" + name + '\'' +
                '}';
    }
}
