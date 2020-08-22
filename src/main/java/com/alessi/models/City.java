package com.alessi.models;

import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class City extends Circle {

    private Text text;
    private int xpos;
    private int ypos;
    private int idNo;

    public City(int x, int y, int id) {
        super(15, Color.YELLOWGREEN);
        xpos = x;
        ypos = y;
        setLayoutX(x);
        setLayoutY(y);
        idNo = id;
        text = new Text(String.valueOf(id));
        text.setStroke(Color.BLACK);
        setEffect(new Lighting());
    }

    public int getXpos() {
        return xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public Text getText() {
        return text;
    }

    public int calculateDistanceFrom(City city) {
        int xdiff = xpos - city.getXpos();
        int ydiff = ypos - city.getYpos();
        return (int) Math.sqrt(xdiff * xdiff + ydiff * ydiff);
    }
}
